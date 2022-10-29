package com.chillieman.chilliewallet.ui.playground

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.db.PrefillUtil.loadStartingBlockChains
import com.chillieman.chilliewallet.db.PrefillUtil.loadStartingDexs
import com.chillieman.chilliewallet.db.entity.PricePoint
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.URL_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.DexDefinitions
import com.chillieman.chilliewallet.definitions.TokenDefinitions
import com.chillieman.chilliewallet.manager.WalletManager
import com.chillieman.chilliewallet.model.ConnectionState
import com.chillieman.chilliewallet.model.contracts.IERC20
import com.chillieman.chilliewallet.model.contracts.IERC20.APPROVAL_EVENT
import com.chillieman.chilliewallet.model.contracts.IERC20.TRANSFER_EVENT
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Factory
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Pair
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Pair.SYNC_EVENT
import com.chillieman.chilliewallet.model.contracts.IUniswapV2Router02
import com.chillieman.chilliewallet.repository.BlockChainRepository
import com.chillieman.chilliewallet.repository.DexRepository
import com.chillieman.chilliewallet.repository.PricePointRepository
import com.chillieman.chilliewallet.repository.TokenRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.net.SocketTimeoutException
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class PlaygroundViewModel
@Inject constructor(
    private val chillieWalletManager: WalletManager,
    private val pricePointRepository: PricePointRepository,
    private val dexRepo: DexRepository,
    private val tokenRepo: TokenRepository,
    private val blockchainRepo: BlockChainRepository
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


    private val _keys = MutableLiveData<ECKeyPair>()
    val walletKeys: LiveData<ECKeyPair>
        get() = _keys

    private val web3: Web3j by lazy { Web3j.build(HttpService(URL_SMART_CHAIN)) }

    var specialDisposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: Shutting down web3")
        web3.shutdown()
        specialDisposable?.dispose()
    }

    fun fillAlphaDatabase() {
        dexRepo.insertAllDex(loadStartingDexs())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Dexes have been added!")
            }, {
                Log.e(TAG, "Error Entering Dexs", it)
            }).disposeOnClear()

        blockchainRepo.insertBlockChains(loadStartingBlockChains())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "BlockChains have been added!")
            }, {
                Log.e(TAG, "Error Entering BlockChains", it)
            }).disposeOnClear()

        //Now Fill the tokens when you go to create Wallet
    }


    fun connectToEthNetwork() {
        _connectionState.value = ConnectionState.CONNECTING
        web3.ethBlockNumber().flowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (!it.hasError()) {
                    _connectionState.value = ConnectionState.CONNECTED
                    _blockNumber.value = it.blockNumber
                    Log.d(TAG, "Connected!")
                } else {
                    _connectionState.value = ConnectionState.ERROR
                    Log.e(TAG, "Error Checking web3 version")
                }
            }, {
                if (it is SocketTimeoutException) {
                    _connectionState.value = ConnectionState.DISCONNECTED
                    Log.e(TAG, "Disconnected from Blockchain...", it)
                } else {
                    _connectionState.value = ConnectionState.ERROR
                    Log.e(TAG, "UNKNOWN Error Connecting to Blockchain...", it)
                }
            }).disposeOnClear()
    }


    fun loadWallet() {
        _isLoading.value = true
        chillieWalletManager.loadAlphaWallet().flatMap {
            chillieWalletManager.getCredentials(it)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ credentials ->
                Log.d(TAG, "Your address is " + credentials.address)
                Log.d(TAG, "Your private key is " + credentials.ecKeyPair.privateKey.toString(16))
                Log.d(TAG, "Your public key is " + credentials.ecKeyPair.publicKey.toString(16))
                _address.value = credentials.address
                _keys.value = credentials.ecKeyPair
                Log.d(TAG, "getWalletInformation: Got Wallet info")
                _isLoading.value = false

                // Watch CHLL
                getPriceForTokenInEth(
                    DexDefinitions.DEX_PANCAKE_SWAP_ADDRESS_ROUTER,
                    TokenDefinitions.ChillieWallet.TOKEN_ADDRESS,
                    credentials
                )

                //Watch CAKE
                getPriceForTokenInEth(
                    DexDefinitions.DEX_PANCAKE_SWAP_ADDRESS_ROUTER,
                    TokenDefinitions.PancakeSwap.TOKEN_ADDRESS,
                    credentials
                )

                getPriceForTokenInEth(
                    DexDefinitions.DEX_PANCAKE_SWAP_ADDRESS_ROUTER,
                    "0xe5ba47fd94cb645ba4119222e34fb33f59c7cd90",
                    credentials
                )

                getPriceForTokenInEth(
                    DexDefinitions.DEX_PANCAKE_SWAP_ADDRESS_ROUTER,
                    "0x20f663cea80face82acdfa3aae6862d246ce0333",
                    credentials
                )

                getPriceForTokenInEth(
                    DexDefinitions.DEX_PANCAKE_SWAP_ADDRESS_ROUTER,
                    "0x6169b3b23e57de79a6146a2170980ceb1f83b9e0",
                    credentials
                )

                getPriceForTokenInEth(
                    DexDefinitions.DEX_PANCAKE_SWAP_ADDRESS_ROUTER,
                    "0x5bcd91c734d665fe426a5d7156f2ad7d37b76e30",
                    credentials
                )

                getPriceForTokenInEth(
                    DexDefinitions.DEX_PANCAKE_SWAP_ADDRESS_ROUTER,
                    "0x08ba0619b1e7a582e0bce5bbe9843322c954c340",
                    credentials
                )


//                startWatchingForAddress(credentials.address)
//                loadToken(credentials)
            }, {
                Log.e(TAG, "Could not get wallet address", it)
            }).disposeOnClear()
    }

    fun loadToken(credentials: Credentials) {
        web3.ethBlockNumber().flowable()
            .flatMap {
                val filter = EthFilter(
                    DefaultBlockParameter.valueOf(it.blockNumber),
                    DefaultBlockParameter.valueOf("latest"),
                    TokenDefinitions.PancakeSwap.TOKEN_ADDRESS
                )

                filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT))

                val test = IERC20.load(
                    TokenDefinitions.PancakeSwap.TOKEN_ADDRESS,
                    web3,
                    credentials,
                    DefaultGasProvider()
                )

                test.transferEventFlowable(filter)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                Log.d(TAG, "TOKEN...")
                Log.d(TAG, "TOKEN To: ${it.to}")
                Log.d(TAG, "TOKEN From: ${it.from}")
                Log.d(TAG, "TOKEN Value: ${it.value}")
                Log.d(TAG, "TOKEN Block: ${it.log.blockNumber}")
            }, {
                Log.e(TAG, "Could not Get Transaction Details.", it)
            }).disposeOnClear()
    }

    fun getReservesFromPair(pair: IUniswapV2Pair) {

    }

    fun getPriceForTokenInEth(
        routerAddress: String,
        tokenAddress: String,
        credentials: Credentials
    ) {

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
        var isToken0Weth = false
        var weth: String? = null
        var factoryAddress: String? = null
        router.factory().flowable().flatMap {
            factoryAddress = it
            router.WETH().flowable()
        }.flatMap {
            weth = it
            val factory = IUniswapV2Factory.load(
                factoryAddress,
                web3,
                credentials,
                DefaultGasProvider()
            )

            factory.getPair(it, tokenAddress).flowable()
        }.flatMap {
            val pair = IUniswapV2Pair.load(
                it,
                web3,
                credentials,
                DefaultGasProvider()
            )

            val token0 = pair.token0().send() //Check if Token0 is WETH or if its Token1
            isToken0Weth = token0 == weth


            val filter = EthFilter(
                DefaultBlockParameter.valueOf("latest"),
                DefaultBlockParameter.valueOf("latest"),
                it
            )

            filter.addSingleTopic(EventEncoder.encode(SYNC_EVENT))

            pair.syncEventFlowable(filter)
//            pair.reserves.flowable()

        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val reserve0 = it.reserve0.toBigDecimal()
                val reserve1 = it.reserve1.toBigDecimal()
//            Log.d(TAG, "reserve0: $reserve0")
//            Log.d(TAG, "reserve1: $reserve1")
//            Log.d(TAG, "isToken0Eth: $isToken0Weth")

                // WETH Divided by Token Amount will give you BNB Price
                val decimal = if (isToken0Weth) {
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
                    TokenDefinitions.PancakeSwap.TOKEN_ADDRESS -> {
                        Log.d(TAG, "[PancakeSwap] Price (WEI): $price ($percentage%)")
                    }
                    TokenDefinitions.ChillieWallet.TOKEN_ADDRESS -> {
                        Log.d(TAG, "[ChillieWallet] Price (WEI): $price ($percentage%)")
                    }
                    else -> {
                        Log.d(TAG, "[$tokenAddress] Price (WEI): $price ($percentage%)")
                    }
                }


                insertPricePoint(
                    PricePoint(
                        tokenAddress,
                        price.toBigInteger(),
                        Calendar.getInstance().timeInMillis
                    )
                )

            }, {
                Log.e(TAG, "Failure when calculating Price", it)
            })
            .disposeOnClear()
    }

    private fun insertPricePoint(pricePoint: PricePoint) {
        pricePointRepository.insertPricePoint(pricePoint)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("ChilliePP", "Price Point Saved")
            }, {
                Log.e("ChilliePP", "Price Point Save ERROR", it)
            })
            .disposeOnClear()
    }

    private fun startWatchingForAddress(address: String) {
        val hm = EventEncoder.encode(TRANSFER_EVENT)
        val ahh = EventEncoder.encode(APPROVAL_EVENT)

        web3.pendingTransactionFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
//                Log.d(TAG, "PENDING To: ${it.to}")
//                Log.d(TAG, "PENDING From: ${it.from}")
                Log.d(TAG, "Hash: ${it.hash}")
//                Log.d(TAG, "input: ${it.input}")
//                Log.d(TAG, "PENDING oo: $hm")
//                Log.d(TAG, "PENDING aa: $ahh")
            }, {
                Log.e(TAG, "Could not Get Transaction Details.", it)
            }).disposeOnClear()

//        web3.transactionFlowable()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.d(TAG, "BRR: ${it.hash}")
//            }, {
//                Log.e(TAG, "Could not Get Transaction Details.", it)
//            }).disposeOnClear()

    }

    fun sendTransactionInEth(toAddress: String, ethAmount: BigDecimal) {
        chillieWalletManager.getSelectedWalletCredentials()
            .map {
                Transfer.sendFunds(
                    web3,
                    it,
                    toAddress,
                    ethAmount,
                    Convert.Unit.ETHER
                ).sendAsync().get()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isStatusOK) {
                    Log.d(TAG, "Transfer success:")
                } else {
                    Log.d(TAG, "Uh Oh... ${it.revertReason}")
                }
                Log.d(TAG, "Tx: ${it.transactionHash}")
            }, {
                Log.e(TAG, "Error On Transfer!", it)
            }).disposeOnClear()
    }

    companion object {
        private const val TAG = "ChilliePlayground"

    }
}