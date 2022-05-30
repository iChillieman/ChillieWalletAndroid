package com.chillieman.chilliewallet.db

import androidx.room.RoomDatabase
import com.chillieman.chilliewallet.db.ChillieDatabase.Companion.VERSION
import com.chillieman.chilliewallet.db.dao.*
import com.chillieman.chilliewallet.db.entity.Authentication
import com.chillieman.chilliewallet.db.entity.BlockChain
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import com.chillieman.chilliewallet.db.entity.Token

@androidx.room.Database(
    entities = [
        Authentication::class,
        ChillieWallet::class,
        BlockChain::class,
        Token::class
    ],
    version = VERSION,
    exportSchema = false
)
abstract class ChillieDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
        const val DATABASE_NAME = "ChillieWallet"
    }

    abstract fun authDao(): AuthDao
    abstract fun blockChainDao(): BlockChainDao
    abstract fun walletDao(): ChillieWalletDao
    abstract fun tokenDao(): TokenDao

}