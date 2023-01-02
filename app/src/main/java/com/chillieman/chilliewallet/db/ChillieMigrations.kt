package com.chillieman.chilliewallet.db

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions
import com.chillieman.chilliewallet.definitions.DexDefinitions

object ChillieMigrations {
    val DATABASE_ON_CREATE = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("Chillie", "Chillieman - Prepopulating new Database!")
            populateStartingData(db)
        }
    }

    fun populateStartingData(database: SupportSQLiteDatabase) {
        // Block Chains:
        database.execSQL(
            """
            INSERT INTO ${BlockChainDefinitions.TABLE_NAME} (
                ${BlockChainDefinitions.Columns.NAME},
                ${BlockChainDefinitions.Columns.SYMBOL},
                ${BlockChainDefinitions.Columns.CHAIN_ID},
                ${BlockChainDefinitions.Columns.EXPLORER},
                ${BlockChainDefinitions.Columns.IS_TESTNET},
                ${BlockChainDefinitions.Columns.LOGO_URL},
                ${BlockChainDefinitions.Columns.LAST_BLOCK_SYNC}
            ) VALUES (
                '${BlockChainDefinitions.Binance.NAME}',
                '${BlockChainDefinitions.Binance.SYMBOL}',
                ${BlockChainDefinitions.Binance.CHAIN_ID},
                '${BlockChainDefinitions.Binance.EXPLORER}',
                FALSE,
                '${BlockChainDefinitions.Binance.LOGO_URL}',
                NULL
            ), (
                '${BlockChainDefinitions.BinanceTestnet.NAME}',
                '${BlockChainDefinitions.BinanceTestnet.SYMBOL}',
                ${BlockChainDefinitions.BinanceTestnet.CHAIN_ID},
                '${BlockChainDefinitions.BinanceTestnet.EXPLORER}',
                TRUE,
                '${BlockChainDefinitions.BinanceTestnet.LOGO_URL}',
                NULL
            )
        """.trimIndent()
        )

        // BlockChain Nodes:
        database.execSQL(
            """
            INSERT INTO ${BlockChainDefinitions.NODE_TABLE_NAME} (
                ${BlockChainDefinitions.NodeColumns.BLOCKCHAIN_ID},
                ${BlockChainDefinitions.NodeColumns.NODE_URL},
                ${BlockChainDefinitions.NodeColumns.LAST_PING}
            ) VALUES (
                ${BlockChainDefinitions.Binance.CHAIN_ID},
                '${BlockChainDefinitions.Binance.DEFAULT_NODE_URL}',
                NULL
            ), (
                ${BlockChainDefinitions.BinanceTestnet.CHAIN_ID},
                '${BlockChainDefinitions.BinanceTestnet.DEFAULT_NODE_URL}',
                NULL
            )
        """.trimIndent()
        )

        // Dex:
        database.execSQL(
            """
            INSERT INTO ${DexDefinitions.TABLE_NAME} (
                ${DexDefinitions.Columns.NAME},
                ${DexDefinitions.Columns.BLOCKCHAIN_ID},
                ${DexDefinitions.Columns.ADDRESS_ROUTER},
                ${DexDefinitions.Columns.ADDRESS_FACTORY},
                ${DexDefinitions.Columns.LOGO_URL}
            ) VALUES (
                '${DexDefinitions.PancakeSwap.NAME}',
                '${DexDefinitions.PancakeSwap.BLOCKCHAIN_ID}',
                '${DexDefinitions.PancakeSwap.ADDRESS_ROUTER}',
                '${DexDefinitions.PancakeSwap.ADDRESS_FACTORY}',
                '${DexDefinitions.PancakeSwap.LOGO_URL}'
            ), (
                '${DexDefinitions.PancakeSwapTestnet.NAME}',
                '${DexDefinitions.PancakeSwapTestnet.BLOCKCHAIN_ID}',
                '${DexDefinitions.PancakeSwapTestnet.ADDRESS_ROUTER}',
                '${DexDefinitions.PancakeSwapTestnet.ADDRESS_FACTORY}',
                '${DexDefinitions.PancakeSwapTestnet.LOGO_URL}'
            )
        """.trimIndent()
        )

        // FROM HERE, CALL SPECIFIC FUNCTIONS THAT ADDED DATA VIA MIGRATIONS
    }

//    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            Log.d("Chillie", "Chillieman - Migrating to Version 2 of Database!")
//            // Add an ID variable to a table
//            database.execSQL(
//                "ALTER TABLE historical_price ADD COLUMN ${PricePointDefinitions.Columns.DEX_ID} INTEGER NOT NULL DEFAULT 0"
//            )
//
//            populateVersion2Upgrade(database)
//        }
//    }
//
//    fun populateVersion2Upgrade(database: SupportSQLiteDatabase) {
//        //Execute INSERT statements to add new DEX's and BlockChains.
//
//    }
}