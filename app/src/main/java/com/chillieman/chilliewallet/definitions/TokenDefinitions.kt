package com.chillieman.chilliewallet.definitions

object TokenDefinitions {
    const val TABLE_NAME = "token"
    object Columns {
        const val ID = "id"
        const val CHAIN_ID = "block_chain_id"
        const val ADDRESS = "address"
        const val NAME = "name"
        const val DECIMALS = "decimals"
        const val TAX_BUY = "tax_buy"
        const val TAX_SELL = "tax_sell"
    }
}