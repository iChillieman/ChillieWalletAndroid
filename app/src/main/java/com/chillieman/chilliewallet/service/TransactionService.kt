package com.chillieman.chilliewallet.service

class TransactionService : BaseService() {
//    private val binder = TransactionBinder()
//
//    @Inject
//    lateinit var blockchainRepo: BlockchainRepository
//
//    @Inject
//    lateinit var pricePointRepository: PricePointRepository
//
//    @Inject
//    lateinit var tokenRepository: TokenRepository
//
//    @Inject
//    lateinit var chillieOrderRepository: ChillieOrderRepository
//
//    @Inject
//    lateinit var dexRepository: DexRepository
//
//    @Inject
//    lateinit var walletManager: WalletManager
//
//    //Map of BLOCKCHAIN_ID to Web3 Instances?
//    private val web3Connections = HashMap<Long, Web3j>()
//
//
//    // Create a Transaction Manager that can do your transfers and stuff!
//
//    // Watch Token Price!!
//
//
//    // Have a bind function where the UI can give you the tokens to watch
//
//    override fun onCreate() {
//        super.onCreate()
//
//        // Establish Web3 Connection(s), per Chain:
//
//        blockchainRepo.fetchAlBlockchains()
//            .map {
//                it.forEach { blockchain ->
//                    web3Connections[blockchain.id] = Web3j.build(HttpService(blockchain.nodeUrl))
//                }
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                // Could Call a function that verifies fields before triggering further action
//                Log.d(TAG, "CREATED - Web3 Instances have been created!")
//            }, {
//                Log.e(TAG, "Error creating Web3 Instances", it)
//            }).disposeOnDestroy()
//
//
//        // Figure out what Tokens to watch
//        // (Watch all of them that are added by user? Hmm....)
//
////        tokenRepository.getAllTokensToWatch()
////            .flattenAsFlowable { tokens ->
////                tokens.forEach {
////                    it.
////                }
////            }
////            .subscribeOn(Schedulers.io())
////            .observeOn(AndroidSchedulers.mainThread())
////            .subscribe({
////                // Could Call a function that verifies fields before triggering further action
////                Log.d(TAG, "CREATED - Web3 Instances have been created!")
////            }, {
////                Log.e(TAG, "Error creating Web3 Instances", it)
////            }).disposeOnDestroy()
//
//
//        // Track the price of Tokens
//
//        // Enter Price into Database
//
//        // If a certain Price is met (ChillieOrderStep) - Send a notification
//
//        // TODO: If a certain price is met, TAKE ACTION.
//
//        Single.fromCallable {
//            Log.d(TAG, "YAWN")
//            Thread.sleep(5000)
//            Log.d(TAG, "YAWN")
//            Thread.sleep(5000)
//            Log.d(TAG, "YAWWWWWWWWWWWWWWWWWWWWWN")
//            Thread.sleep(20000)
//        }
//            .subscribeOn(Schedulers.single())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.d(TAG, "CREATED - That was a nice nap")
//            }, {
//                Log.e(TAG, "Error taking a nap", it)
//            }).disposeOnDestroy()
//
//        Log.d(TAG, "CREATED - Time To sleep")
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Log.d(TAG, "onStartCommand!")
//        return Service.START_STICKY
//    }
//
//    override fun onBind(intent: Intent?): IBinder = binder
//
//    override fun onTaskRemoved(rootIntent: Intent?) {
//        super.onTaskRemoved(rootIntent)
//        //The user just stopped the application - Send them a notification that Orders wont work when the application is not running!
//        Log.d(TAG, "Task has been removed!")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d(TAG, "Service has been destroyed!")
//    }
//
//    fun getPriceForTokenInEth(
//        routerAddress: String,
//        tokenAddress: String,
//        credentials: Credentials
//    ) {
//
//        //Load Router
//        val router = IUniswapV2Router02.load(
//            routerAddress,
//            web3,
//            credentials,
//            DefaultGasProvider()
//        )
//
//        //Load Factory And WETH
//
//        //Fetch Pair from Factory
//
//        // From the pair reserves, calculate price in WETH
//
//        var startingPrice: BigInteger = BigInteger.valueOf(0)
//        var isToken0Weth = false
//        var weth: String? = null
//        var factoryAddress: String? = null
//        router.factory().flowable().flatMap {
//            factoryAddress = it
//            router.WETH().flowable()
//        }.flatMap {
//            weth = it
//            val factory = IUniswapV2Factory.load(
//                factoryAddress,
//                web3,
//                credentials,
//                DefaultGasProvider()
//            )
//
//            factory.getPair(it, tokenAddress).flowable()
//        }.flatMap {
//            val pair = IUniswapV2Pair.load(
//                it,
//                web3,
//                credentials,
//                DefaultGasProvider()
//            )
//
//            val token0 = pair.token0().send() //Check if Token0 is WETH or if its Token1
//            isToken0Weth = token0 == weth
//
//
//            val filter = EthFilter(
//                DefaultBlockParameter.valueOf("latest"),
//                DefaultBlockParameter.valueOf("latest"),
//                it
//            )
//
//            filter.addSingleTopic(EventEncoder.encode(IUniswapV2Pair.SYNC_EVENT))
//
//            pair.syncEventFlowable(filter)
////            pair.reserves.flowable()
//
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                val reserve0 = it.reserve0.toBigDecimal()
//                val reserve1 = it.reserve1.toBigDecimal()
////            Log.d(TAG, "reserve0: $reserve0")
////            Log.d(TAG, "reserve1: $reserve1")
////            Log.d(TAG, "isToken0Eth: $isToken0Weth")
//
//                // WETH Divided by Token Amount will give you BNB Price
//                val decimal = if (isToken0Weth) {
//                    reserve0.divide(reserve1, 18, RoundingMode.FLOOR)
//                } else {
//                    reserve1.divide(reserve0, 18, RoundingMode.FLOOR)
//                }
//
//                val price = decimal.movePointRight(18)
//
//                //TODO: Format this in to WEI (Price for WETH should be an INT, not a decimal)
//                if (startingPrice == BigInteger.valueOf(0)) {
//                    startingPrice = price.toBigInteger()
//                    Log.d(PlaygroundViewModel.TAG, "[$tokenAddress] Starting Price: $startingPrice")
//                }
//
//                // (CurrentPrice / Starting Price) - 1
//                val percentage = price.divide(startingPrice.toBigDecimal(), 4, RoundingMode.FLOOR)
//                    .subtract(BigDecimal.ONE)
//                    .multiply(BigDecimal.valueOf(100))
//                    .setScale(2)
//
//                when (tokenAddress) {
//                    TokenDefinitions.PancakeSwap.TOKEN_ADDRESS -> {
//                        Log.d(PlaygroundViewModel.TAG, "[PancakeSwap] Price (WEI): $price ($percentage%)")
//                    }
//                    TokenDefinitions.ChillieWallet.TOKEN_ADDRESS -> {
//                        Log.d(PlaygroundViewModel.TAG, "[ChillieWallet] Price (WEI): $price ($percentage%)")
//                    }
//                    else -> {
//                        Log.d(PlaygroundViewModel.TAG, "[$tokenAddress] Price (WEI): $price ($percentage%)")
//                    }
//                }
//
//
//                insertPricePoint(
//                    PricePoint(
//                        tokenAddress,
//                        price.toBigInteger(),
//                        Calendar.getInstance().timeInMillis
//                    )
//                )
//
//            }, {
//                Log.e(PlaygroundViewModel.TAG, "Failure when calculating Price", it)
//            })
//            .disposeOnClear()
//    }
//
//    private fun insertPricePoint(pricePoint: PricePoint) {
//        pricePointRepository.insertPricePoint(pricePoint)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.d("ChilliePP", "Price Point Saved")
//            }, {
//                Log.e("ChilliePP", "Price Point Save ERROR", it)
//            })
//            .disposeOnClear()
//    }
//
//    /**
//     * Class used for the client Binder.  Because we know this service always
//     * runs in the same process as its clients, we don't need to deal with IPC.
//     */
//    inner class TransactionBinder : Binder() {
//        // Return this instance of LocalService so clients can call public methods
//        fun getService() = this@TransactionService
//    }
//
//    companion object {
//        private const val TAG = "ChillieTxnService"
//    }
}
