package com.chillieman.chilliewallet.di

import android.content.Context
import androidx.room.Room
import com.chillieman.chilliewallet.db.ChillieDatabase
import com.chillieman.chilliewallet.db.ChillieMigrations
import com.chillieman.chilliewallet.db.dao.AuthDao
import com.chillieman.chilliewallet.db.dao.AuthDatumDao
import com.chillieman.chilliewallet.db.dao.BalanceDao
import com.chillieman.chilliewallet.db.dao.BlockchainDao
import com.chillieman.chilliewallet.db.dao.BlockchainNodeDao
import com.chillieman.chilliewallet.db.dao.ChillieChainDao
import com.chillieman.chilliewallet.db.dao.ChillieChainStepDao
import com.chillieman.chilliewallet.db.dao.ChillieOrderDao
import com.chillieman.chilliewallet.db.dao.ChillieOrderStepDao
import com.chillieman.chilliewallet.db.dao.ChilliePairDao
import com.chillieman.chilliewallet.db.dao.ChillieWalletDao
import com.chillieman.chilliewallet.db.dao.DexDao
import com.chillieman.chilliewallet.db.dao.PricePointDao
import com.chillieman.chilliewallet.db.dao.SettingsDao
import com.chillieman.chilliewallet.db.dao.TokenDao
import com.chillieman.chilliewallet.db.dao.TokenWatchDao
import com.chillieman.chilliewallet.db.dao.TxnDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabase(context: Context): ChillieDatabase {
        return Room.databaseBuilder(
            context,
            ChillieDatabase::class.java, ChillieDatabase.DATABASE_NAME
        )
            .addCallback(ChillieMigrations.DATABASE_ON_CREATE)
//            .addMigrations(ChillieMigrations.MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideAuthDao(chillieDatabase: ChillieDatabase): AuthDao = chillieDatabase.authDao()

    @Provides
    fun provideBlockchainDao(chillieDatabase: ChillieDatabase): BlockchainDao =
        chillieDatabase.blockchainDao()

    @Provides
    fun provideBlockchainNodeDao(chillieDatabase: ChillieDatabase): BlockchainNodeDao =
        chillieDatabase.blockchainNodeDao()

    @Provides
    fun provideWalletDao(chillieDatabase: ChillieDatabase): ChillieWalletDao =
        chillieDatabase.walletDao()

    @Provides
    fun provideTokenDao(chillieDatabase: ChillieDatabase): TokenDao = chillieDatabase.tokenDao()

    @Provides
    fun provideAuthDatumDao(chillieDatabase: ChillieDatabase): AuthDatumDao =
        chillieDatabase.authDatumDao()

    @Provides
    fun provideChillieOrderDao(chillieDatabase: ChillieDatabase): ChillieOrderDao =
        chillieDatabase.chillieOrderDao()

    @Provides
    fun provideChillieOrderStepDao(chillieDatabase: ChillieDatabase): ChillieOrderStepDao =
        chillieDatabase.chillieOrderStepDao()

    @Provides
    fun provideChillieChainDao(chillieDatabase: ChillieDatabase): ChillieChainDao =
        chillieDatabase.chillieChainDao()

    @Provides
    fun provideChillieChainStepDao(chillieDatabase: ChillieDatabase): ChillieChainStepDao =
        chillieDatabase.chillieChainStepDao()

    @Provides
    fun provideTxnDao(chillieDatabase: ChillieDatabase): TxnDao = chillieDatabase.txnDao()

    @Provides
    fun providePricePointDao(chillieDatabase: ChillieDatabase): PricePointDao =
        chillieDatabase.pricePointDao()

    @Provides
    fun provideBalanceDao(chillieDatabase: ChillieDatabase): BalanceDao =
        chillieDatabase.balanceDao()

    @Provides
    fun provideDexDao(chillieDatabase: ChillieDatabase): DexDao = chillieDatabase.dexDao()

    @Provides
    fun provideSettingsDao(chillieDatabase: ChillieDatabase): SettingsDao =
        chillieDatabase.settingsDao()

    @Provides
    fun providePairDao(chillieDatabase: ChillieDatabase): ChilliePairDao = chillieDatabase.pairDao()

    @Provides
    fun provideTokenWatchDao(chillieDatabase: ChillieDatabase): TokenWatchDao =
        chillieDatabase.tokenWatchDao()

}
