package com.chillieman.chilliewallet.di

import android.content.Context
import dagger.Module
import dagger.Provides

//@Module
//class DatabaseModule {
//    @Provides
//    fun provideDatabase(context: Context) : Database {
//        return Room.databaseBuilder(
//            context,
//            Database::class.java, Database.DATABASE_NAME
//        )
//            .fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
//            .build()
//    }
//
//    @Provides
//    fun provideWalletDao(database: Database) = database.walletDao()
//}