package com.chillieman.chilliewallet.db

import com.chillieman.chilliewallet.db.entity.BlockChain
import com.chillieman.chilliewallet.db.entity.Token
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.EXPLORER_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.EXPLORER_SMART_CHAIN_TESTNET
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.NAME_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.NAME_SMART_CHAIN_TESTNET
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.SYMBOL_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.SYMBOL_SMART_CHAIN_TESTNET
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.URL_SMART_CHAIN
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.URL_SMART_CHAIN_TESTNET
import com.chillieman.chilliewallet.definitions.TokenDefinitions.CHILLIE_WALLET_BSC_ADDRESS
import com.chillieman.chilliewallet.definitions.TokenDefinitions.CHILLIE_WALLET_BSC_CHAIN_ID
import com.chillieman.chilliewallet.definitions.TokenDefinitions.CHILLIE_WALLET_BSC_TEST_ADDRESS
import com.chillieman.chilliewallet.definitions.TokenDefinitions.CHILLIE_WALLET_BSC_TEST_CHAIN_ID
import com.chillieman.chilliewallet.definitions.TokenDefinitions.CHILLIE_WALLET_DECIMALS
import com.chillieman.chilliewallet.definitions.TokenDefinitions.CHILLIE_WALLET_NAME


//TODO CHILLIEMAN - Make a Prefilled SQL DB that can be used for first time app users. Preload ChillieWallet Token
//TODO CHILLIEMAN - FINALIZE DB SCHEMA BEFORE RELEASING ANYTHING TO PUBLIC!!
object PrefillUtil {

    fun loadStartingTokens(): List<Token> = listOf(

        Token(
            CHILLIE_WALLET_BSC_CHAIN_ID,
            CHILLIE_WALLET_BSC_ADDRESS,
            CHILLIE_WALLET_NAME,
            CHILLIE_WALLET_DECIMALS
        ),
        Token(
            CHILLIE_WALLET_BSC_TEST_CHAIN_ID,
            CHILLIE_WALLET_BSC_TEST_ADDRESS,
            CHILLIE_WALLET_NAME,
            CHILLIE_WALLET_DECIMALS
        )
    )


    fun loadStartingBlockChains(): List<BlockChain> = listOf(
        BlockChain(NAME_SMART_CHAIN, SYMBOL_SMART_CHAIN, URL_SMART_CHAIN, EXPLORER_SMART_CHAIN),
        BlockChain(
            NAME_SMART_CHAIN_TESTNET,
            SYMBOL_SMART_CHAIN_TESTNET,
            URL_SMART_CHAIN_TESTNET,
            EXPLORER_SMART_CHAIN_TESTNET
        )
    )

}