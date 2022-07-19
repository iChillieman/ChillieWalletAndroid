package com.chillieman.chilliewallet.db

import androidx.room.RoomDatabase
import com.chillieman.chilliewallet.db.ChillieDatabase.Companion.VERSION
import com.chillieman.chilliewallet.db.dao.*
import com.chillieman.chilliewallet.db.entity.*

@androidx.room.Database(
    entities = [
        Authentication::class,
        AuthDatum::class,
        ChillieWallet::class,
        BlockChain::class,
        Token::class,
        ChillieChain::class,
        ChillieChainStep::class,
        ChillieOrder::class,
        ChillieOrderStep::class,
        PricePoint::class,
        Txn::class,
        Balance::class
    ],
    version = VERSION,
    exportSchema = false
)
abstract class ChillieDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 2
        const val DATABASE_NAME = "ChillieWallet.db"
    }

    abstract fun authDao(): AuthDao
    abstract fun authDatumDao(): AuthDatumDao
    abstract fun blockChainDao(): BlockChainDao
    abstract fun walletDao(): ChillieWalletDao
    abstract fun tokenDao(): TokenDao
    abstract fun balanceDao(): BalanceDao
    abstract fun chillieChainDao(): ChillieChainDao
    abstract fun chillieChainStepDao(): ChillieChainStepDao
    abstract fun chillieOrderDao(): ChillieOrderDao
    abstract fun chillieOrderStepDao(): ChillieOrderStepDao
    abstract fun pricePointDao(): PricePointDao
    abstract fun txnDao(): TxnDao
}
