package com.chillieman.chilliewallet.definitions

import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.CHAIN_ID_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.CHAIN_ID_SMART_CHAIN_TESTNET

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

    const val CHILLIE_WALLET_NAME = "ChillieWallet"
    const val CHILLIE_WALLET_DECIMALS = 18

    //ChillieWallet Token Definitions
    const val CHILLIE_WALLET_BSC_CHAIN_ID = CHAIN_ID_SMART_CHAIN
    const val CHILLIE_WALLET_BSC_ADDRESS = ""

    const val CHILLIE_WALLET_BSC_TEST_CHAIN_ID = CHAIN_ID_SMART_CHAIN_TESTNET
    const val CHILLIE_WALLET_BSC_TEST_ADDRESS = ""
}
