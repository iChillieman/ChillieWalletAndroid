package com.chillieman.chilliewallet.ui.main.wallet.tokenlist

import java.math.BigInteger

data class TokenForList(
    val name: String,
    val symbol: String,
    val balance: String,
    val worth: String,
    val address: String,
    val logoUrl: String?
)
