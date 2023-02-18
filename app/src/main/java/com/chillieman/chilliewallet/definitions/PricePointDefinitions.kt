package com.chillieman.chilliewallet.definitions

object PricePointDefinitions {
    const val TABLE_NAME = "historical_price"

    object Columns {
        const val ID = "id"
        const val DEX_ID = "dex_id"
        const val TOKEN_ID = "token_id"
        const val PRICE_IN_ETH = "eth_per_token"
        const val PRICE_IN_TOKEN = "token_per_eth"
        const val TIMESTAMP = "timestamp"
        const val IS_GLOBAL_WATCHER = "is_global_watcher"
    }
}
