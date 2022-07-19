package com.chillieman.chilliewallet.definitions

object PricePointDefinitions {
    const val TABLE_NAME = "historical_price"

    object Columns {
        const val ID = "id"
        const val TOKEN_ID = "token_id"
        const val PRICE_IN_ETH = "eth_per_token"
        const val TIMESTAMP = "timestamp"
    }
}
