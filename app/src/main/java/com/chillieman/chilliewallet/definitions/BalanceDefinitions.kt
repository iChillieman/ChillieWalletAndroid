package com.chillieman.chilliewallet.definitions

object BalanceDefinitions {
    const val TABLE_NAME = "balance"

    object Columns {
        const val ID = "id"
        const val BLOCKCHAIN_ID = "blockchain_id"
        const val WALLET_ID = "wallet_id"
        const val TOKEN_ID = "token_id"
        const val TOTAL_BALANCE = "balance"
        const val VALUE_IN_WEI = "eth_value"
        const val TIMESTAMP = "timestamp"
        const val IS_GLOBAL_WATCHER = "is_global_watcher"
    }
}
