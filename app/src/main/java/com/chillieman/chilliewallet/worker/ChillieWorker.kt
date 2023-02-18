package com.chillieman.chilliewallet.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.chillieman.chilliewallet.db.entity.Balance
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import com.chillieman.chilliewallet.db.entity.PricePoint
import com.chillieman.chilliewallet.db.entity.Token
import com.chillieman.chilliewallet.model.contracts.IERC20
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Factory
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Pair
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Router02
import com.chillieman.chilliewallet.repository.BlockchainRepository
import com.chillieman.chilliewallet.repository.ChillieWalletRepository
import com.chillieman.chilliewallet.repository.DexRepository
import com.chillieman.chilliewallet.repository.PricePointRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

@HiltWorker
class ChillieWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val chillieWalletRepository: ChillieWalletRepository,
    private val blockchainRepository: BlockchainRepository,
    private val dexRepository: DexRepository,
    private val pricePointRepository: PricePointRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val startingTime = System.currentTimeMillis()
            Log.d("ChillieWorker", "Chillieman - Worker is starting work at: $startingTime")

            var isSuccess = false

            // Store A set of Block Chain IDs
            val blockchainIdSet = mutableSetOf<Long>()

            // We need a Web3 Instance for each Blockchain (BlockchainID -> Web3 Instance)
            val blockchainToWeb3Mapping = mutableMapOf<Long, Web3j>()

            // We need a Balance Mapping (WalletID -> Set of TokenIDs to check price)
            val walletToTokenIdMapping = mutableMapOf<ChillieWallet, Set<Long>>()

            // This will contain all Tokens to fetch price off each DEX
            val tokenSet = mutableSetOf<Token>()

            // This will contain all Contract Instances used to check balances
            val tokenIdToContractMapping = mutableMapOf<Long, IERC20>()

            var credentials: Credentials? = null

            try {
                // First, Fetch all wallets.
                chillieWalletRepository.fetchAllWallets().forEach { wallet ->
                    Log.d("ChillieWorker", "Chillieman - Wallet ID: ${wallet.id}")

                    if (credentials == null) {
                        credentials = chillieWalletRepository.getCredentials(wallet)
                    }

                    // Token IDs to check balance later
                    val tokenSetForBalance = mutableSetOf<Long>()
                    chillieWalletRepository.getAllTokensByWalletId(wallet.id).forEach { token ->
                        // NOTE: These are all Sets, so duplicates will NOT be added & Will return false
                        val isNewBlockchain = blockchainIdSet.add(token.blockchainId)
                        val isNewToken = tokenSet.add(token)
                        tokenSetForBalance.add(token.id)

                        if (isNewBlockchain) {
                            // Set up Web3 Instance if this is a brand new Blockchain
                            val blockchainRPC =
                                blockchainRepository.fetchDefaultBlockchainNode(token.blockchainId)
                            // Can we make this Async?
                            val web3jInstance = Web3j.build(HttpService(blockchainRPC.nodeUrl))

                            val ethBalance = web3jInstance.ethGetBalance(
                                wallet.address,
                                DefaultBlockParameterName.LATEST
                            ).send().balance


                            // Enter ETH Balance into database.
                            val balanceItem = Balance(
                                token.blockchainId,
                                wallet.id,
                                0L, // Token ID of zero indicates its ETH balance
                                ethBalance,
                                ethBalance,
                                System.currentTimeMillis(),
                                true
                            )
                            chillieWalletRepository.insertBalanceRecord(balanceItem)

                            blockchainToWeb3Mapping[token.blockchainId] = web3jInstance
                        }

                        if (isNewToken) {
                            val web3j = blockchainToWeb3Mapping[token.blockchainId]
                                ?: throw IllegalStateException("Credentials not loaded to check prices =[")

                            // New Token? Create IERC20 Instance!
                            val tokenContract = IERC20.load(
                                token.address,
                                web3j,
                                credentials,
                                DefaultGasProvider()
                            )

                            tokenIdToContractMapping[token.id] = tokenContract
                        }
                    }

                    // Store Set of tokens to check balance later - Once all router contracts are set up.
                    walletToTokenIdMapping[wallet] = tokenSetForBalance
                }

                val staticCreds = credentials
                    ?: throw IllegalStateException("Credentials not loaded to check prices =[")

                Log.d(
                    "ChillieWorker",
                    "Chillieman - Worker is Checking Token Price - Ellapsed Time: ${System.currentTimeMillis() - startingTime}"
                )

                blockchainIdSet.forEach { blockchainId ->
                    val web3jInstance = blockchainToWeb3Mapping[blockchainId]
                        ?: throw IllegalStateException("Web3InstanceMissing!")
                    val dexListForBlockchain = dexRepository.getAllDexByBlockchainId(blockchainId)

                    dexListForBlockchain.forEach { dex ->
                        //Load Router
                        val router = IUniswapV2Router02.load(
                            dex.addressRouter,
                            web3jInstance,
                            staticCreds,
                            DefaultGasProvider()
                        )

                        //Load Factory And WETH
                        val weth: String = router.WETH().send()

                        val factory = IUniswapV2Factory.load(
                            dex.addressFactory,
                            web3jInstance,
                            staticCreds,
                            DefaultGasProvider()
                        )

                        tokenSet.filter { it.blockchainId == blockchainId }.forEach {
                            val pairAddress = factory.getPair(weth, it.address).send()
                            logPriceForToken(
                                dex.id,
                                router,
                                pairAddress,
                                weth,
                                it,
                                web3jInstance,
                                staticCreds
                            )
                        }
                    }
                }

                Log.d(
                    "ChillieWorker",
                    "Chillieman - Worker is Checking Token Balance - Ellapsed Time: ${System.currentTimeMillis() - startingTime}"
                )

                walletToTokenIdMapping.forEach { (wallet, tokenIdSet) ->
                    // Check the ID Set to check all Tokens that are being watched.
                    tokenIdSet.forEach { tokenId ->
                        val token = tokenSet.firstOrNull { it.id == tokenId }
                            ?: throw IllegalStateException("Token Does not exist in Global Set")
                        val tokenContract = tokenIdToContractMapping[token.id]
                            ?: throw IllegalStateException("ERC20 Contract doesnt exist for Token")

                        val tokenBalance = getTokenBalance(tokenContract, wallet.address)
                        val worth = if (tokenBalance == BigInteger.ZERO) {
                            BigInteger.ZERO
                        } else {
                            // TODO - CREATE FUNCTION TO FETCH LATEST PRICE OF DEFAULT DEX
                            tokenBalance
                        }

                        val balanceEntry = Balance(
                            token.blockchainId,
                            wallet.id,
                            tokenId,
                            tokenBalance,
                            worth,
                            System.currentTimeMillis(),
                            true
                        )
                        chillieWalletRepository.insertBalanceRecord(balanceEntry)
                    }
                }

                isSuccess = true
            } catch (e: Exception) {
                Log.e("ChillieWorker", "Error!", e)
            }

            val endTime = System.currentTimeMillis()
            val elapsedTime = endTime - startingTime
            Log.d("ChillieWorker", "Chillieman - Worker is done with work at: $endTime")
            Log.d("ChillieWorker", "Chillieman - Worker took ${elapsedTime}ms")

            if (isSuccess) {
                Log.d("ChillieWorker", "Chillieman - Worker Success!!")
                Result.success()
            } else {
                Log.d("ChillieWorker", "Chillieman - Worker FAILURE!!")
                Result.failure()
            }
        }
    }

    private suspend fun logPriceForToken(
        dexId: Long,
        routerContract: IUniswapV2Router02,
        pairAddress: String,
        weth: String,
        token: Token,
        web3j: Web3j,
        credentials: Credentials
    ) {
        withContext(Dispatchers.IO) {
            val pair = IUniswapV2Pair.load(
                pairAddress,
                web3j,
                credentials,
                DefaultGasProvider()
            )

            val token0 = pair.token0().send()

            val reserves = pair.reserves.send()
            val tokenReserve = if (token0 == weth) {
                reserves.component2()
            } else {
                reserves.component1()
            }

            val ethReserve = if (token0 == weth) {
                reserves.component1()
            } else {
                reserves.component2()
            }
//            val reserve0 = reserves.component1().toBigDecimal()
//            val reserve1 = reserves.component2().toBigDecimal()

            val oneEther = Convert.toWei(BigDecimal.ONE, Convert.Unit.ETHER).toBigInteger()
            val oneToken = BigDecimal.ONE.movePointRight(token.decimals).toBigInteger()

            val priceInEth = routerContract.getAmountOut(oneEther, ethReserve, tokenReserve).send()
            val priceInTokens =
                routerContract.getAmountOut(oneToken, tokenReserve, ethReserve).send()

            // WETH Divided by Token Amount will give you Price Per ETH
//            val priceInEth = if (token0 == weth) {
//                reserve0.divide(reserve1, 18, RoundingMode.FLOOR)
//                    .movePointRight(18)
//                    .toBigInteger()
//            } else {
//                reserve1.divide(reserve0, 18, RoundingMode.FLOOR)
//                    .movePointRight(18)
//                    .toBigInteger()
//            }
//
//            // TODO - CHILLIEMAN - SHOULD THIS BE DONE WITH THE SAME PRECISION AS ERC DECIMALS?
//            //      - Cant see why it SHOULD be required
//            val priceInTokens = if (token0 == weth) {
//                reserve1.divide(reserve0, 18, RoundingMode.FLOOR)
//                    .movePointRight(18)
//                    .toBigInteger()
//            } else {
//                reserve0.divide(reserve1, 18, RoundingMode.FLOOR)
//                    .movePointRight(18)
//                    .toBigInteger()
//            }

            Log.d("ChillieWorker", "Chillieman - [${token.name}] Price (WEI): $priceInEth")
            Log.d("ChillieWorker", "Chillieman - [${token.name}] Price (Token): $priceInTokens")

            val pricePoint = PricePoint(
                dexId = dexId,
                tokenId = token.id,
                priceInWei = priceInEth,
                priceInTokens = priceInTokens,
                timestamp = System.currentTimeMillis(),
                isGlobalPriceWatcher = true
            )

            pricePointRepository.insertPricePoint(pricePoint)
        }
    }


    private suspend fun getTokenBalance(tokenContract: IERC20, walletAddress: String): BigInteger {
        var balance: BigInteger = BigInteger.ZERO
        tokenContract.balanceOf(walletAddress).flowable()
            .asFlow()
            .flowOn(Dispatchers.IO)
            .collect {
                balance = it
            }
        return balance
    }
}
