package com.chillieman.chilliewallet.definitions

object DexDefinitions {
    const val TABLE_NAME = "dex"

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val BLOCKCHAIN_ID = "blockchain_id"
        const val ADDRESS_ROUTER = "address_router"
        const val ADDRESS_FACTORY = "address_factory"
        const val LOGO_URL = "logo_url"
        const val TAX_RATE_BUY = "tax_rate_buy"
        const val TAX_RATE_SELL = "tax_rate_sell"
    }


    object PancakeSwap {
        const val NAME = "PancakeSwap"
        const val BLOCKCHAIN_ID = BlockChainDefinitions.Binance.CHAIN_ID
        const val ADDRESS_ROUTER = "0x10ED43C718714eb63d5aA57B78B54704E256024E"
        const val ADDRESS_FACTORY = "0xcA143Ce32Fe78f1f7019d7d551a6402fC5350c73"
        const val LOGO_URL = PANCAKE_LOGO_URL
    }

    object PancakeSwapTestnet {
        const val NAME = "PancakeSwap [Testnet]"
        const val BLOCKCHAIN_ID = BlockChainDefinitions.BinanceTestnet.CHAIN_ID
        const val ADDRESS_ROUTER = "0xD99D1c33F9fC3444f8101754aBC46c52416550D1"
        const val ADDRESS_FACTORY = "0x6725F303b657a9451d8BA641348b6761A6CC7a17"
        const val LOGO_URL = PANCAKE_LOGO_URL
    }

    const val PANCAKE_LOGO_URL =
        "https://assets-cdn.trustwallet.com/blockchains/binance/assets/CAKE-435/logo.png"
}