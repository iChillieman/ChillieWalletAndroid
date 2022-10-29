package com.chillieman.chilliewallet.definitions

object DexDefinitions {
    const val TABLE_NAME = "dex"

    object Columns {
        const val ID = "id"
        const val BLOCKCHAIN_ID = "blockchain_id"
        const val ADDRESS_ROUTER = "address_router"
        const val ADDRESS_FACTORY = "address_factory"
        const val NAME = "name"
        const val LOGO_URL = "logo_url"
    }

    const val DEX_PANCAKE_SWAP_NAME = "PancakeSwap"
    const val DEX_PANCAKE_LOGO_URL =
        "https://assets-cdn.trustwallet.com/blockchains/binance/assets/CAKE-435/logo.png"
    const val DEX_PANCAKE_SWAP_CHAIN_ID = BlockChainDefinitions.CHAIN_ID_SMART_CHAIN
    const val DEX_PANCAKE_SWAP_ADDRESS_ROUTER = "0x10ED43C718714eb63d5aA57B78B54704E256024E"
    const val DEX_PANCAKE_SWAP_ADDRESS_FACTORY = "0xcA143Ce32Fe78f1f7019d7d551a6402fC5350c73"

    const val DEX_PANCAKE_SWAP_TEST_NAME = "PancakeSwap [Testnet]"
    const val DEX_PANCAKE_SWAP_TEST_CHAIN_ID = BlockChainDefinitions.CHAIN_ID_SMART_CHAIN_TESTNET
    const val DEX_PANCAKE_SWAP_TEST_ADDRESS_ROUTER = "0xD99D1c33F9fC3444f8101754aBC46c52416550D1"
    const val DEX_PANCAKE_SWAP_TEST_ADDRESS_FACTORY = "0x6725F303b657a9451d8BA641348b6761A6CC7a17"
}