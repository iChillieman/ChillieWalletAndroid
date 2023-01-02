package com.chillieman.chilliewallet.db

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chillieman.chilliewallet.db.ChillieDatabase.Companion.VERSION
import com.chillieman.chilliewallet.db.dao.*
import com.chillieman.chilliewallet.db.entity.*
import com.chillieman.chilliewallet.definitions.converter.BigIntegerConverters

@androidx.room.Database(
    entities = [
        Authentication::class,
        AuthDatum::class,
        ChillieWallet::class,
        BlockChain::class,
        BlockChainNode::class,
        Token::class,
        ChillieChain::class,
        ChillieChainStep::class,
        ChillieOrder::class,
        ChillieOrderStep::class,
        PricePoint::class,
        Txn::class,
        Balance::class,
        ChillieSetting::class,
        Dex::class,
        ChilliePair::class,
        TokenWatch::class
    ],
    version = VERSION,
    exportSchema = false
)
@TypeConverters(
    BigIntegerConverters::class
)
abstract class ChillieDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 2
        const val DATABASE_NAME = "chillie_wallet.db"
    }

    abstract fun authDao(): AuthDao
    abstract fun authDatumDao(): AuthDatumDao
    abstract fun blockChainDao(): BlockChainDao
    abstract fun blockChainNodeDao(): BlockChainNodeDao
    abstract fun walletDao(): ChillieWalletDao
    abstract fun tokenDao(): TokenDao
    abstract fun balanceDao(): BalanceDao
    abstract fun chillieChainDao(): ChillieChainDao
    abstract fun chillieChainStepDao(): ChillieChainStepDao
    abstract fun chillieOrderDao(): ChillieOrderDao
    abstract fun chillieOrderStepDao(): ChillieOrderStepDao
    abstract fun pricePointDao(): PricePointDao
    abstract fun txnDao(): TxnDao
    abstract fun settingsDao(): SettingsDao
    abstract fun dexDao(): DexDao
    abstract fun pairDao(): ChilliePairDao
    abstract fun tokenWatchDao(): TokenWatchDao
}
