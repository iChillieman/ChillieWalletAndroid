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

    const val NODE_URL = "https://bsc-dataseed.binance.org/"
    const val TEST_NODE_URL = "https://data-seed-prebsc-1-s1.binance.org:8545/"
}