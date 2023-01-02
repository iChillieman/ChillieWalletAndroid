package com.chillieman.chilliewallet.definitions

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
        const val IS_RECOMMENDATION = "is_recommendation"
    }

    const val WATCH_TABLE_NAME = "token_wallet"

    object WatchColumns {
        const val ID = "id"
        const val WALLET_ID = "wallet_id"
        const val DEX_ID = "dex_id"
        const val TOKEN_ID = "token_id"
    }

    object WETH {
        object BNB {
            const val NAME = "WBNB"
            const val DECIMALS = STANDARD_DECIMALS
            const val CHAIN_ID = BlockChainDefinitions.Binance.CHAIN_ID
            const val ADDRESS = "0xbb4CdB9CBd36B01bD1cBaEBF2De08d9173bc095c"
        }

        object TestBNB {
            const val NAME = "WBNB [T]"
            const val DECIMALS = STANDARD_DECIMALS
            const val CHAIN_ID = BlockChainDefinitions.BinanceTestnet.CHAIN_ID
            const val ADDRESS = "0xae13d989daC2f0dEbFf460aC112a837C89BAa7cd"
        }
    }


    object ChillieWallet {
        const val LOGO_URL = "ChillieWallet"

        object BNB {
            const val NAME = "ChillieWallet"
            const val SYMBOL = "CHLL"
            const val DECIMALS = STANDARD_DECIMALS
            const val CHAIN_ID = BlockChainDefinitions.Binance.CHAIN_ID
            const val ADDRESS = "0x86b09825416612809e00947D0fEE05EC5853f62B"
        }

        object BNBTest {
            const val NAME = "ChillieWallet [T]"
            const val SYMBOL = "tCHLL"
            const val DECIMALS = STANDARD_DECIMALS
            const val CHAIN_ID = BlockChainDefinitions.BinanceTestnet.CHAIN_ID
            const val ADDRESS = "" // TODO CHILLIE - FILL ME IN
        }
    }


    object PancakeSwap {
        const val DECIMALS = 18
        const val NAME = "PancakeSwap"
        const val SYMBOL = "CAKE"
        const val CHAIN_ID = BlockChainDefinitions.Binance.CHAIN_ID
        const val ADDRESS = "0x0E09FaBB73Bd3Ade0a17ECC321fD13a19e81cE82"
        const val LOGO_URL =
            "https://assets-cdn.trustwallet.com/blockchains/binance/assets/CAKE-435/logo.png"
    }

    const val STANDARD_DECIMALS = 18
}
