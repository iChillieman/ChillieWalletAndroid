package com.chillieman.chilliewallet.db

import com.chillieman.chilliewallet.db.entity.ChillieSetting
import com.chillieman.chilliewallet.db.entity.Token
import com.chillieman.chilliewallet.definitions.ChillieSettingDefinitions.Defaults
import com.chillieman.chilliewallet.definitions.ChillieSettingDefinitions.Keys
import com.chillieman.chilliewallet.definitions.TokenDefinitions

object PrefillUtil {

    // These tokens are added automatically to any Wallet the user creates or imports.
    fun loadStartingTokens(): List<Token> = listOf(
        //Live Tokens
        Token(
            TokenDefinitions.ChillieWallet.BNB.CHAIN_ID,
            TokenDefinitions.ChillieWallet.BNB.ADDRESS,
            TokenDefinitions.ChillieWallet.BNB.NAME,
            TokenDefinitions.ChillieWallet.BNB.DECIMALS,
            TokenDefinitions.ChillieWallet.BNB.SYMBOL,
            TokenDefinitions.ChillieWallet.LOGO_URL
        ),
        Token(
            TokenDefinitions.PancakeSwap.CHAIN_ID,
            TokenDefinitions.PancakeSwap.ADDRESS,
            TokenDefinitions.PancakeSwap.NAME,
            TokenDefinitions.PancakeSwap.DECIMALS,
            TokenDefinitions.PancakeSwap.SYMBOL,
            TokenDefinitions.PancakeSwap.LOGO_URL
        )
    )

    fun loadStartingTestTokens(): List<Token> = listOf(
        Token(
            TokenDefinitions.ChillieWallet.BNBTest.CHAIN_ID,
            TokenDefinitions.ChillieWallet.BNBTest.ADDRESS,
            TokenDefinitions.ChillieWallet.BNBTest.NAME,
            TokenDefinitions.ChillieWallet.BNBTest.DECIMALS,
            TokenDefinitions.ChillieWallet.BNBTest.SYMBOL,
            TokenDefinitions.ChillieWallet.LOGO_URL
        )
    )


    fun loadDefaultSettings(): List<ChillieSetting> = listOf(
        ChillieSetting(Keys.SELECTED_BLOCKCHAIN, null),
        ChillieSetting(Keys.SELECTED_WALLET, null),
        ChillieSetting(
            Keys.NOTIFICATION_TRANSACTION,
            Defaults.NOTIFICATION_TRANSACTION
        ),
        ChillieSetting(Keys.NOTIFICATION_BUY, Defaults.NOTIFICATION_BUY),
        ChillieSetting(Keys.NOTIFICATION_SELL, Defaults.NOTIFICATION_SELL),
        ChillieSetting(Keys.TIMEOUT, Defaults.TIMEOUT),
        ChillieSetting(Keys.TIMEOUT_LENGTH, Defaults.TIMEOUT_LENGTH)
    )

}