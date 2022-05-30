package com.chillieman.chilliewallet.definitions

object BlockChainDefinitions {
    const val TABLE_NAME = "block_chain"
    object Columns {
        const val CHAIN_ID = "id"
        const val NAME = "name"
        const val SYMBOL = "symbol"
        const val NODE_URL = "node_url"
        const val EXPLORER = "explorer"
    }
}