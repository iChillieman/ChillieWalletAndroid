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

    const val NAME_SMART_CHAIN = "Smart Chain"
    const val NAME_SMART_CHAIN_TESTNET = "Smart Chain Testnet"

    const val SYMBOL_SMART_CHAIN = "BNB"
    const val SYMBOL_SMART_CHAIN_TESTNET = "tBNB"

    const val CHAIN_ID_SMART_CHAIN = 56
    const val CHAIN_ID_SMART_CHAIN_TESTNET = 97

    const val URL_SMART_CHAIN = "https://bsc-dataseed.binance.org/"
    const val URL_SMART_CHAIN_TESTNET = "https://data-seed-prebsc-1-s1.binance.org:8545/"

    const val EXPLORER_SMART_CHAIN = "https://bscscan.com"
    const val EXPLORER_SMART_CHAIN_TESTNET = "https://testnet.bscscan.com"
}
