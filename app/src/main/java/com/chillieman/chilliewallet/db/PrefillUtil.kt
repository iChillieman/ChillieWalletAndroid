package com.chillieman.chilliewallet.db

import com.chillieman.chilliewallet.db.entity.BlockChain
import com.chillieman.chilliewallet.db.entity.ChillieSetting
import com.chillieman.chilliewallet.db.entity.Dex
import com.chillieman.chilliewallet.db.entity.Token
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.CHAIN_ID_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.CHAIN_ID_SMART_CHAIN_TESTNET
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.EXPLORER_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.EXPLORER_SMART_CHAIN_TESTNET
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.LOGO_URL_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.NAME_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.NAME_SMART_CHAIN_TESTNET
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.SYMBOL_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.SYMBOL_SMART_CHAIN_TESTNET
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.URL_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.URL_SMART_CHAIN_TESTNET
import com.chillieman.chilliewallet.definitions.ChillieSettingDefinitions.Defaults
import com.chillieman.chilliewallet.definitions.ChillieSettingDefinitions.Keys
import com.chillieman.chilliewallet.definitions.DexDefinitions.DEX_PANCAKE_LOGO_URL
import com.chillieman.chilliewallet.definitions.DexDefinitions.DEX_PANCAKE_SWAP_ADDRESS_FACTORY
import com.chillieman.chilliewallet.definitions.DexDefinitions.DEX_PANCAKE_SWAP_ADDRESS_ROUTER
import com.chillieman.chilliewallet.definitions.DexDefinitions.DEX_PANCAKE_SWAP_CHAIN_ID
import com.chillieman.chilliewallet.definitions.DexDefinitions.DEX_PANCAKE_SWAP_NAME
import com.chillieman.chilliewallet.definitions.DexDefinitions.DEX_PANCAKE_SWAP_TEST_ADDRESS_FACTORY
import com.chillieman.chilliewallet.definitions.DexDefinitions.DEX_PANCAKE_SWAP_TEST_ADDRESS_ROUTER
import com.chillieman.chilliewallet.definitions.DexDefinitions.DEX_PANCAKE_SWAP_TEST_CHAIN_ID
import com.chillieman.chilliewallet.definitions.DexDefinitions.DEX_PANCAKE_SWAP_TEST_NAME
import com.chillieman.chilliewallet.definitions.TokenDefinitions


//TODO CHILLIEMAN - Make a Prefilled SQL DB that can be used for first time app users. Preload ChillieWallet Token
//TODO CHILLIEMAN - FINALIZE DB SCHEMA BEFORE RELEASING ANYTHING TO PUBLIC!!
object PrefillUtil {

    fun loadStartingDexs(): List<Dex> = listOf(
        Dex(
            DEX_PANCAKE_SWAP_CHAIN_ID,
            DEX_PANCAKE_SWAP_NAME,
            DEX_PANCAKE_SWAP_ADDRESS_ROUTER,
            DEX_PANCAKE_SWAP_ADDRESS_FACTORY,
            DEX_PANCAKE_LOGO_URL
        ),

        //TESTNET
        Dex(
            DEX_PANCAKE_SWAP_TEST_CHAIN_ID,
            DEX_PANCAKE_SWAP_TEST_NAME,
            DEX_PANCAKE_SWAP_TEST_ADDRESS_ROUTER,
            DEX_PANCAKE_SWAP_TEST_ADDRESS_FACTORY,
            DEX_PANCAKE_LOGO_URL
        )
    )

    fun loadStartingTokens(): List<Token> = listOf(
        //Live Tokens
        Token(
            TokenDefinitions.ChillieWallet.TOKEN_CHAIN_ID,
            TokenDefinitions.ChillieWallet.TOKEN_ADDRESS,
            TokenDefinitions.ChillieWallet.TOKEN_NAME,
            TokenDefinitions.ChillieWallet.TOKEN_DECIMALS,
            TokenDefinitions.ChillieWallet.TOKEN_SYMBOL,
            TokenDefinitions.ChillieWallet.LOGO_URL
        ),
        Token(
            TokenDefinitions.PancakeSwap.TOKEN_CHAIN_ID,
            TokenDefinitions.PancakeSwap.TOKEN_ADDRESS,
            TokenDefinitions.PancakeSwap.TOKEN_NAME,
            TokenDefinitions.PancakeSwap.TOKEN_DECIMALS,
            TokenDefinitions.PancakeSwap.TOKEN_SYMBOL,
            TokenDefinitions.PancakeSwap.LOGO_URL
        )
    )

    fun loadStartingTestTokens(): List<Token> = listOf(
        Token(
            TokenDefinitions.ChillieWallet.TOKEN_TEST_CHAIN_ID,
            TokenDefinitions.ChillieWallet.TOKEN_TEST_ADDRESS,
            TokenDefinitions.ChillieWallet.TOKEN_TEST_NAME,
            TokenDefinitions.ChillieWallet.TOKEN_DECIMALS,
            TokenDefinitions.ChillieWallet.TOKEN_TEST_SYMBOL,
            TokenDefinitions.ChillieWallet.LOGO_URL
        )
    )


    fun loadStartingBlockChains(): List<BlockChain> = listOf(
        BlockChain(
            CHAIN_ID_SMART_CHAIN,
            NAME_SMART_CHAIN,
            SYMBOL_SMART_CHAIN,
            URL_SMART_CHAIN,
            EXPLORER_SMART_CHAIN,
            LOGO_URL_SMART_CHAIN,
        ),

        BlockChain(
            CHAIN_ID_SMART_CHAIN_TESTNET,
            NAME_SMART_CHAIN_TESTNET,
            SYMBOL_SMART_CHAIN_TESTNET,
            URL_SMART_CHAIN_TESTNET,
            EXPLORER_SMART_CHAIN_TESTNET,
            LOGO_URL_SMART_CHAIN,
        )
    )

    fun loadDefaultSettings(): List<ChillieSetting> = listOf(
        ChillieSetting(Keys.SETTING_SELECTED_BLOCKCHAIN, null),
        ChillieSetting(Keys.SETTING_SELECTED_WALLET, null),
        ChillieSetting(
            Keys.SETTING_NOTIFICATION_TRANSACTION,
            Defaults.SETTING_NOTIFICATION_TRANSACTION
        ),
        ChillieSetting(Keys.SETTING_NOTIFICATION_BUY, Defaults.SETTING_NOTIFICATION_BUY),
        ChillieSetting(Keys.SETTING_NOTIFICATION_SELL, Defaults.SETTING_NOTIFICATION_SELL),
        ChillieSetting(Keys.SETTING_TIMEOUT, Defaults.SETTING_TIMEOUT),
        ChillieSetting(Keys.SETTING_TIMEOUT_LENGTH, Defaults.SETTING_TIMEOUT_LENGTH)
    )

}