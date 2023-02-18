package com.chillieman.chilliewallet.db

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chillieman.chilliewallet.definitions.BlockchainDefinitions
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
            INSERT INTO ${BlockchainDefinitions.TABLE_NAME} (
                ${BlockchainDefinitions.Columns.NAME},
                ${BlockchainDefinitions.Columns.SYMBOL},
                ${BlockchainDefinitions.Columns.BLOCKCHAIN_ID},
                ${BlockchainDefinitions.Columns.EXPLORER},
                ${BlockchainDefinitions.Columns.IS_TESTNET},
                ${BlockchainDefinitions.Columns.LOGO_URL},
                ${BlockchainDefinitions.Columns.LAST_BLOCK_SYNC}
            ) VALUES (
                '${BlockchainDefinitions.Binance.NAME}',
                '${BlockchainDefinitions.Binance.SYMBOL}',
                ${BlockchainDefinitions.Binance.CHAIN_ID},
                '${BlockchainDefinitions.Binance.EXPLORER}',
                FALSE,
                '${BlockchainDefinitions.Binance.LOGO_URL}',
                NULL
            ), (
                '${BlockchainDefinitions.BinanceTestnet.NAME}',
                '${BlockchainDefinitions.BinanceTestnet.SYMBOL}',
                ${BlockchainDefinitions.BinanceTestnet.CHAIN_ID},
                '${BlockchainDefinitions.BinanceTestnet.EXPLORER}',
                TRUE,
                '${BlockchainDefinitions.BinanceTestnet.LOGO_URL}',
                NULL
            )
        """.trimIndent()
        )

        // Blockchain Nodes:
        database.execSQL(
            """
            INSERT INTO ${BlockchainDefinitions.NODE_TABLE_NAME} (
                ${BlockchainDefinitions.NodeColumns.BLOCKCHAIN_ID},
                ${BlockchainDefinitions.NodeColumns.NODE_URL},
                ${BlockchainDefinitions.NodeColumns.LAST_PING}
            ) VALUES (
                ${BlockchainDefinitions.Binance.CHAIN_ID},
                '${BlockchainDefinitions.Binance.DEFAULT_NODE_URL}',
                NULL
            ), (
                ${BlockchainDefinitions.BinanceTestnet.CHAIN_ID},
                '${BlockchainDefinitions.BinanceTestnet.DEFAULT_NODE_URL}',
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
//        //Execute INSERT statements to add new DEX's and Blockchains.
//
//    }
}