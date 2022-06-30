package com.chillieman.chilliewallet.ui.main.wallet


import com.chillieman.chilliewallet.manager.WalletManager
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import org.web3j.protocol.Web3j
import javax.inject.Inject

class WalletViewModel
@Inject constructor(
) : BaseViewModel() {

    //TODO: Handle all Token logic here. Balances - Worth in ETH
    // - Any pending Orders on the tokens
    // (Tap to be brought to the Order Tab - With Order already selected

    fun loadTokens() {

    }


    companion object {
        private const val TAG = "ChillieLog"
    }
}