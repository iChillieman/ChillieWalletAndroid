package com.chillieman.chilliewallet.definitions

object BlockchainDefinitions {
    const val TABLE_NAME = "block_chain"
    const val NODE_TABLE_NAME = "block_chain_node"

    object Columns {
        const val NAME = "name"
        const val SYMBOL = "symbol"
        const val BLOCKCHAIN_ID = "id"
        const val EXPLORER = "explorer"
        const val IS_TESTNET = "is_testnet"
        const val LOGO_URL = "logo_url"
        const val SELECTED_NODE = "selected_node"
        const val LAST_BLOCK_SYNC = "last_block_synced"
    }

    object NodeColumns {
        const val ID = "id"
        const val BLOCKCHAIN_ID = "blockchain_id"
        const val NODE_URL = "node_url"
        const val LAST_PING = "last_ping"
    }

    object Binance {
        const val NAME = "Smart Chain"
        const val SYMBOL = "BNB"
        const val CHAIN_ID = 56L
        const val DEFAULT_NODE_URL = "https://bsc-dataseed.binance.org/"
        const val EXPLORER = "https://bscscan.com"
        const val LOGO_URL = LOGO_URL_BINANCE
    }

    object BinanceTestnet {
        const val NAME = "Smart Chain"
        const val SYMBOL = "tBNB"
        const val CHAIN_ID = 97L
        const val DEFAULT_NODE_URL = "https://data-seed-prebsc-1-s1.binance.org:8545/"
        const val EXPLORER = "https://testnet.bscscan.com"
        const val LOGO_URL = LOGO_URL_BINANCE
    }

    const val LOGO_URL_BINANCE =
        "https://assets-cdn.trustwallet.com/blockchains/binance/info/logo.png"
}
