package com.chillieman.chilliewallet.definitions

import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.CHAIN_ID_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.CHAIN_ID_SMART_CHAIN_TESTNET

object TokenDefinitions {
    const val TABLE_NAME = "token"

    object Columns {
        const val ID = "id"
        const val BLOCKCHAIN_ID = "blockchain_id"
        const val ADDRESS = "address"
        const val NAME = "name"
        const val DECIMALS = "decimals"
        const val SYMBOL = "symbol"
        const val LOGO_URL = "logo_url"
        const val TAX_BUY = "tax_buy"
        const val TAX_SELL = "tax_sell"
    }

    const val WATCH_TABLE_NAME = "token_wallet"

    object WatchColumns {
        const val ID = "id"
        const val WALLET_ID = "wallet_id"
        const val DEX_ID = "dex_id"
        const val TOKEN_ADDRESS = "token_address"
        const val IS_WATCHING = "is_watching"
    }

    object BinanceWETH {
        const val TOKEN_DECIMALS = 18

        const val TOKEN_NAME = "WBNB"
        const val TOKEN_CHAIN_ID = CHAIN_ID_SMART_CHAIN
        const val TOKEN_ADDRESS = "0xbb4CdB9CBd36B01bD1cBaEBF2De08d9173bc095c"

        const val TOKEN_TEST_NAME = "WBNB [T]"
        const val TOKEN_TEST_CHAIN_ID = CHAIN_ID_SMART_CHAIN_TESTNET
        const val TOKEN_TEST_ADDRESS = "0xae13d989daC2f0dEbFf460aC112a837C89BAa7cd"
    }


    object ChillieWallet {
        const val TOKEN_DECIMALS = 18

        const val TOKEN_NAME = "ChillieWallet"
        const val TOKEN_SYMBOL = "CHLL"
        const val TOKEN_CHAIN_ID = CHAIN_ID_SMART_CHAIN
        const val TOKEN_ADDRESS = "0x86b09825416612809e00947D0fEE05EC5853f62B"

        const val TOKEN_TEST_NAME = "ChillieWallet [T]"
        const val TOKEN_TEST_SYMBOL = "CHLL [T]"
        const val TOKEN_TEST_CHAIN_ID = CHAIN_ID_SMART_CHAIN_TESTNET
        const val TOKEN_TEST_ADDRESS = "" // TODO CHILLIE - FILL ME IN
        const val LOGO_URL = TOKEN_NAME
    }

    object PancakeSwap {
        const val TOKEN_DECIMALS = 18
        const val TOKEN_NAME = "PancakeSwap"
        const val TOKEN_SYMBOL = "CAKE"
        const val TOKEN_CHAIN_ID = CHAIN_ID_SMART_CHAIN
        const val TOKEN_ADDRESS = "0x0E09FaBB73Bd3Ade0a17ECC321fD13a19e81cE82"
        const val LOGO_URL = "https://assets-cdn.trustwallet.com/blockchains/binance/assets/CAKE-435/logo.png"
    }


    object PixlToken {
        const val TOKEN_CHAIN_ID = CHAIN_ID_SMART_CHAIN
        const val TOKEN_ADDRESS = "0x72d2946094E6E57c2faDe4964777A9af2B7A51F9"
        const val TOKEN_NAME = "Pixl Coin"
        const val LOGO_URL = TOKEN_NAME
    }
}
