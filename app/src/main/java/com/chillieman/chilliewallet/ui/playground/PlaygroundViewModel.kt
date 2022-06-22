package com.chillieman.chilliewallet.ui.playground

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.manager.WalletManager
import com.chillieman.chilliewallet.model.ConnectionState
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.web3j.crypto.ECKeyPair
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.net.SocketTimeoutException
import javax.inject.Inject

class PlaygroundViewModel
@Inject constructor(
    private val chillieWalletManager: WalletManager
) : BaseViewModel() {
    private val _connectionState = MutableLiveData<ConnectionState>().apply {
        value = ConnectionState.DISCONNECTED
    }
    val connectionState: LiveData<ConnectionState>
        get() = _connectionState

    private val _address = MutableLiveData<String>()
    val walletAddress: LiveData<String>
        get() = _address

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    private val _keys = MutableLiveData<ECKeyPair>()
    val walletKeys: LiveData<ECKeyPair>
        get() = _keys

    private val web3: Web3j by lazy { Web3j.build(HttpService(NODE_URL)) }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: Shutting down web3")
        web3.shutdown()
    }

    fun connectToEthNetwork() {
        _connectionState.value = ConnectionState.CONNECTING
        web3.web3ClientVersion().flowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (!it.hasError()) {
                    _connectionState.value = ConnectionState.CONNECTED
                    Log.d(TAG, "Connected!")
                } else {
                    _connectionState.value = ConnectionState.ERROR
                    Log.e(TAG, it.error.message)
                }
            }, {
                if (it is SocketTimeoutException) {
                    _connectionState.value = ConnectionState.DISCONNECTED
                    Log.e(TAG, "Disconnected from Blockchain...", it)
                } else {
                    _connectionState.value = ConnectionState.ERROR
                    Log.e(TAG, "Error Connecting to Blockchain...", it)
                }
            }).disposeOnClear()
    }

    fun getWalletCredentials() {
        if (_isLoading.value == true) return
        _isLoading.value = true
        chillieWalletManager.getSelectedWalletCredentials()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ credentials ->
                Log.d(TAG, "Your address is " + credentials.address)
                Log.d(TAG, "Your private key is " + credentials.ecKeyPair.privateKey.toString(16))
                Log.d(TAG, "Your public key is " + credentials.ecKeyPair.publicKey.toString(16))
                _address.value = credentials.address
                _keys.value = credentials.ecKeyPair
                Log.d(TAG, "getWalletInformation: Got Wallet info")
                _isLoading.value = false
            }, {
                Log.e(TAG, "Could not get wallet address", it)
            }).disposeOnClear()
    }


    fun sendTransactionInEth(toAddress: String, ethAmount: BigDecimal) {
        chillieWalletManager.getSelectedWalletCredentials()
            .map {
                Transfer.sendFunds(
                    web3,
                    it,
                    toAddress,
                    ethAmount,
                    Convert.Unit.ETHER
                ).sendAsync().get()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.isStatusOK) {
                    Log.d(TAG, "Transfer success:")
                } else {
                    Log.d(TAG, "Uh Oh... ${it.revertReason}")
                }
                Log.d(TAG, "Tx: ${it.transactionHash}")
            }, {
                Log.e(TAG, "Error On Transfer!", it)
            }).disposeOnClear()
    }

    companion object {
        private const val TAG = "ChilliePlayground"
        private const val NODE_URL = "https://bsc-dataseed.binance.org/"
        private const val TEST_NODE_URL = "https://data-seed-prebsc-1-s1.binance.org:8545/"
    }
}