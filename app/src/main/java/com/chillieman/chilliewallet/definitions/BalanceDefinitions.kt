package com.chillieman.chilliewallet.definitions

object BalanceDefinitions {
    const val TABLE_NAME = "balance"

    object Columns {
        const val ID = "id"
        const val WALLET_ID = "wallet_id"
        const val TOKEN_ID = "token_id"
        const val TOTAL_BALANCE = "balance"
        const val TOKENS_IN_ORDER = "unavailable"
    }
}
