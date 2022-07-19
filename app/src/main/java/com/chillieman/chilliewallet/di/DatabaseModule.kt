package com.chillieman.chilliewallet.di

import android.content.Context
import androidx.room.Room
import com.chillieman.chilliewallet.db.ChillieDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideDatabase(context: Context): ChillieDatabase {
        return Room.databaseBuilder(
            context,
            ChillieDatabase::class.java, ChillieDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAuthDao(chillieDatabase: ChillieDatabase) = chillieDatabase.authDao()

    @Provides
    fun provideBlockChainDao(chillieDatabase: ChillieDatabase) = chillieDatabase.blockChainDao()

    @Provides
    fun provideWalletDao(chillieDatabase: ChillieDatabase) = chillieDatabase.walletDao()

    @Provides
    fun provideTokenDao(chillieDatabase: ChillieDatabase) = chillieDatabase.tokenDao()

    @Provides
    fun provideAuthDatumDao(chillieDatabase: ChillieDatabase) = chillieDatabase.authDatumDao()

    @Provides
    fun provideChillieOrderDao(chillieDatabase: ChillieDatabase) = chillieDatabase.chillieOrderDao()

    @Provides
    fun provideChillieOrderStepDao(chillieDatabase: ChillieDatabase) =
        chillieDatabase.chillieOrderStepDao()

    @Provides
    fun provideChillieChainDao(chillieDatabase: ChillieDatabase) = chillieDatabase.chillieChainDao()

    @Provides
    fun provideChillieChainStepDao(chillieDatabase: ChillieDatabase) =
        chillieDatabase.chillieChainStepDao()

    @Provides
    fun provideTxnDao(chillieDatabase: ChillieDatabase) = chillieDatabase.txnDao()

    @Provides
    fun providePricePointDao(chillieDatabase: ChillieDatabase) = chillieDatabase.pricePointDao()

    @Provides
    fun provideBalanceDao(chillieDatabase: ChillieDatabase) = chillieDatabase.balanceDao()
}
