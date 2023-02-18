package com.chillieman.chilliewallet.ui.playground

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chillieman.chilliewallet.definitions.BlockchainDefinitions
import com.chillieman.chilliewallet.definitions.DexDefinitions
import com.chillieman.chilliewallet.definitions.TokenDefinitions
import com.chillieman.chilliewallet.model.enums.ConnectionState
import com.chillieman.chilliewallet.model.contracts.IERC20
import com.chillieman.chilliewallet.model.contracts.IERC20.APPROVAL_EVENT
import com.chillieman.chilliewallet.model.contracts.IERC20.TRANSFER_EVENT
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Factory
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Pair
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Pair.SYNC_EVENT
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Router02
import com.chillieman.chilliewallet.repository.BlockchainRepository
import com.chillieman.chilliewallet.repository.ChillieWalletRepository
import com.chillieman.chilliewallet.repository.DexRepository
import com.chillieman.chilliewallet.repository.PricePointRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.web3j.abi.EventEncoder
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class PlaygroundViewModel
@Inject constructor(
    private val chillieWalletRepository: ChillieWalletRepository,
    private val pricePointRepository: PricePointRepository,
    private val dexRepo: DexRepository,
    private val blockchainRepo: BlockchainRepository,
    private val walletFolder: File,
) : BaseViewModel() {
    private val _connectionState = MutableLiveData<ConnectionState>().apply {
        value = ConnectionState.DISCONNECTED
    }
    val connectionState: LiveData<ConnectionState>
        get() = _connectionState

    private val _address = MutableLiveData<String>()
    val walletAddress: LiveData<String>
        get() = _address

    private val _blockNumber = MutableLiveData<BigInteger>()
    val blockNumber: LiveData<BigInteger>
        get() = _blockNumber

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isDataFilled = MutableLiveData<Boolean>().apply { value = false }
    val isDataFilled: LiveData<Boolean>
        get() = _isDataFilled

    private val _keys = MutableLiveData<ECKeyPair>()
    val walletKeys: LiveData<ECKeyPair>
        get() = _keys

    private var walletJob: Job? = null
    private var job: Job? = null
    private val tokenJobs = mutableListOf<Job>()

    private val web3: Web3j by lazy { Web3j.build(HttpService(BlockchainDefinitions.Binance.DEFAULT_NODE_URL)) }

    override fun onCleared() {
        super.onCleared()
        tokenJobs.forEach { it.cancel() }
        tokenJobs.clear()
        job?.cancel()
        job = null
        walletJob?.cancel()
        walletJob = null
        Log.d(TAG, "onCleared: Shutting down web3")
        web3.shutdown()
    }

    fun importWallet(input: String) {
        walletJob?.cancel()
        walletJob = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) { _isLoading.value = true }

            // TODO - ALPHA - REMOVE THIS CONSTRAINT
            clearExistingWalletFilesIfNeeded()

            val isSuccess =
                chillieWalletRepository.importWallet(input, "ChillieWallet")

            Log.d("Chillieman", "Chillieman - isSuccess: $isSuccess")
            if (isSuccess) {
                val wallet = chillieWalletRepository.fetchAlphaWallet()
                withContext(Dispatchers.Main) { _address.value = wallet.address }
                Log.d("Chillieman", "Chillieman - address: ${wallet.address}")

            }
            withContext(Dispatchers.Main) { _isLoading.value = false }
        }
    }

    private suspend fun clearExistingWalletFilesIfNeeded() {
        val isWalletCreated = chillieWalletRepository.isWalletCreated()
        if (isWalletCreated) {
            // DELETE THE CONTENTS OF WALLET FOLDER
            walletFolder.listFiles()?.forEach {
                try {
                    it.delete()
                } catch (e: Exception) {
                    return@forEach
                }
            }
            chillieWalletRepository.clear()
        }
    }

    fun connectToEthNetwork() {
        _connectionState.value = ConnectionState.CONNECTING
        job = CoroutineScope(Dispatchers.IO).launch {
            val blockNumber = web3.ethBlockNumber().send()
            blockNumber.blockNumber

            withContext(Dispatchers.Main) {
                if (!blockNumber.hasError()) {
                    _connectionState.value = ConnectionState.CONNECTED
                    _blockNumber.value = blockNumber.blockNumber
                    Log.d(TAG, "Connected!")
                } else {
                    _connectionState.value = ConnectionState.ERROR
                    Log.e(TAG, "Error Checking web3 version")
                }
            }
        }
    }


    fun loadWallet() {
        job?.cancel()
        job = viewModelScope.launch {
            _isLoading.value = true
            val alphaWallet =
                withContext(Dispatchers.IO) { chillieWalletRepository.fetchAlphaWallet() }
            val credentials =
                withContext(Dispatchers.IO) { chillieWalletRepository.getCredentials(alphaWallet) }

            Log.d(TAG, "Your address is " + credentials.address)
            Log.d(TAG, "Your private key is " + credentials.ecKeyPair.privateKey.toString(16))
            Log.d(TAG, "Your public key is " + credentials.ecKeyPair.publicKey.toString(16))
            _address.value = credentials.address
            _keys.value = credentials.ecKeyPair
            Log.d(TAG, "getWalletInformation: Got Wallet info")
            _isLoading.value = false

            // Watch CHLL
            getPriceForTokenInEth(
                DexDefinitions.PancakeSwap.ADDRESS_ROUTER,
                TokenDefinitions.ChillieWallet.BNB.ADDRESS,
                credentials
            )

            //Watch CAKE
            getPriceForTokenInEth(
                DexDefinitions.PancakeSwap.ADDRESS_ROUTER,
                TokenDefinitions.PancakeSwap.ADDRESS,
                credentials
            )

            getPriceForTokenInEth(
                DexDefinitions.PancakeSwap.ADDRESS_ROUTER,
                "0xe5ba47fd94cb645ba4119222e34fb33f59c7cd90",
                credentials
            )

            getPriceForTokenInEth(
                DexDefinitions.PancakeSwap.ADDRESS_ROUTER,
                "0x20f663cea80face82acdfa3aae6862d246ce0333",
                credentials
            )

            getPriceForTokenInEth(
                DexDefinitions.PancakeSwap.ADDRESS_ROUTER,
                "0x6169b3b23e57de79a6146a2170980ceb1f83b9e0",
                credentials
            )

            getPriceForTokenInEth(
                DexDefinitions.PancakeSwap.ADDRESS_ROUTER,
                "0x5bcd91c734d665fe426a5d7156f2ad7d37b76e30",
                credentials
            )

            getPriceForTokenInEth(
                DexDefinitions.PancakeSwap.ADDRESS_ROUTER,
                "0x08ba0619b1e7a582e0bce5bbe9843322c954c340",
                credentials
            )


//        startWatchingForAddress(credentials.address)
//        loadToken(credentials)
        }
    }

    private suspend fun getPriceForTokenInEth(
        routerAddress: String,
        tokenAddress: String,
        credentials: Credentials
    ) {
        withContext(Dispatchers.IO) {

            //Load Router
            val router = IUniswapV2Router02.load(
                routerAddress,
                web3,
                credentials,
                DefaultGasProvider()
            )

            //Load Factory And WETH

            //Fetch Pair from Factory

            // From the pair reserves, calculate price in WETH

            var startingPrice: BigInteger = BigInteger.valueOf(0)
            val factoryAddress = router.factory().send()
            var weth: String? = router.WETH().send()

            val factory = IUniswapV2Factory.load(
                factoryAddress,
                web3,
                credentials,
                DefaultGasProvider()
            )

            val pairAddress = factory.getPair(weth, tokenAddress).send()
            val pair = IUniswapV2Pair.load(
                pairAddress,
                web3,
                credentials,
                DefaultGasProvider()
            )

            val token0 = pair.token0().send() //Check if Token0 is WETH or if its Token1


            val filter = EthFilter(
                DefaultBlockParameter.valueOf("latest"),
                DefaultBlockParameter.valueOf("latest"),
                pairAddress
            )

            filter.addSingleTopic(EventEncoder.encode(SYNC_EVENT))

//        pair.reserves.flowable()
            pair.syncEventFlowable(filter).forEach {

                val reserve0 = it.reserve0.toBigDecimal()
                val reserve1 = it.reserve1.toBigDecimal()
//            Log.d(TAG, "reserve0: $reserve0")
//            Log.d(TAG, "reserve1: $reserve1")
//            Log.d(TAG, "isToken0Eth: $isToken0Weth")

                // WETH Divided by Token Amount will give you BNB Price
                val decimal = if (token0 == weth) {
                    reserve0.divide(reserve1, 18, RoundingMode.FLOOR)
                } else {
                    reserve1.divide(reserve0, 18, RoundingMode.FLOOR)
                }

                val price = decimal.movePointRight(18)

                //TODO: Format this in to WEI (Price for WETH should be an INT, not a decimal)
                if (startingPrice == BigInteger.valueOf(0)) {
                    startingPrice = price.toBigInteger()
                    Log.d(TAG, "[$tokenAddress] Starting Price: $startingPrice")
                }

                // (CurrentPrice / Starting Price) - 1
                val percentage = price.divide(startingPrice.toBigDecimal(), 4, RoundingMode.FLOOR)
                    .subtract(BigDecimal.ONE)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2)

                when (tokenAddress) {
                    TokenDefinitions.PancakeSwap.ADDRESS -> {
                        Log.d(TAG, "[PancakeSwap] Price (WEI): $price ($percentage%)")
                    }
                    TokenDefinitions.ChillieWallet.BNB.ADDRESS -> {
                        Log.d(TAG, "[ChillieWallet] Price (WEI): $price ($percentage%)")
                    }
                    else -> {
                        Log.d(TAG, "[$tokenAddress] Price (WEI): $price ($percentage%)")
                    }
                }


//            pricePointRepository.insertPricePoint(
//                PricePoint(
//                    tokenAddress,
//                    price.toBigInteger(),
//                    Calendar.getInstance().timeInMillis
//                )
//            )
            }
        }
    }

    private fun startWatchingForAddress(address: String) {
        val hm = EventEncoder.encode(TRANSFER_EVENT)
        val ahh = EventEncoder.encode(APPROVAL_EVENT)

        val pendingTransactions = web3.pendingTransactionFlowable().forEach {
            // Do ALL trades go through this??
            Log.d(TAG, "PENDING To: ${it.to}")
            Log.d(TAG, "PENDING From: ${it.from}")
            Log.d(TAG, "Hash: ${it.hash}")
            Log.d(TAG, "input: ${it.input}")
            Log.d(TAG, "PENDING oo: $hm")
            Log.d(TAG, "PENDING aa: $ahh")
        }


        val transactions = web3.transactionFlowable().forEach {
            Log.d(TAG, "BRRR: ${it.hash}")
        }
    }

    fun loadToken(credentials: Credentials) {
        val blockNumber = web3.ethBlockNumber().send()

        val filter = EthFilter(
            DefaultBlockParameter.valueOf(blockNumber.blockNumber),
            DefaultBlockParameter.valueOf("latest"),
            TokenDefinitions.PancakeSwap.ADDRESS
        )

        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT))

        // Can we get around the Load Function? Do we need to provide Credentials when
        // We are simply checking the price?
        val test = IERC20.load(
            TokenDefinitions.PancakeSwap.ADDRESS,
            web3,
            credentials,
            DefaultGasProvider()
        )

        val transferEvents = test.transferEventFlowable(filter).forEach {
            Log.d(TAG, "TOKEN...")
            Log.d(TAG, "TOKEN To: ${it.to}")
            Log.d(TAG, "TOKEN From: ${it.from}")
            Log.d(TAG, "TOKEN Value: ${it.value}")
            Log.d(TAG, "TOKEN Block: ${it.log.blockNumber}")
        }
    }

    fun sendTransactionInEth(toAddress: String, ethAmount: BigDecimal) {
        job?.cancel()
        job = viewModelScope.launch {
            val wallet = chillieWalletRepository.fetchAlphaWallet()
            val creds = chillieWalletRepository.getCredentials(wallet)

            val transfer = Transfer.sendFunds(
                web3,
                creds,
                toAddress,
                ethAmount,
                Convert.Unit.ETHER
            ).send()


            if (transfer.isStatusOK) {
                Log.d(TAG, "Transfer success:")
            } else {
                Log.d(TAG, "Uh Oh... ${transfer.revertReason}")
            }
            Log.d(TAG, "Tx: ${transfer.transactionHash}")
        }
    }

    companion object {
        private const val TAG = "ChilliePlayVM"

    }
}