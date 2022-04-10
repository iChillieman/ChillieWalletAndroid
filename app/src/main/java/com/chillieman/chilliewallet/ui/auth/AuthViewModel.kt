package com.chillieman.chilliewallet.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jService
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import java.io.File
import java.net.SocketTimeoutException
import javax.inject.Inject

class AuthViewModel
@Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var walletPath: File
    private val walletPassword = "ChilliemanTest"
    private val injectedFileName =
        "UTC--2022-04-10T21-22-09.275000000Z--b2defa5c6b3a11e780e69b49f6390516f34dbfe0.json"

    private val _connectionState = MutableLiveData<ConnectionState>().apply {
        value = ConnectionState.DISCONNECTED
    }
    val connectionState: LiveData<ConnectionState>
        get() = _connectionState

    private val _address = MutableLiveData<String>()
    val walletAddress: LiveData<String>
        get() = _address

    private val _keys = MutableLiveData<ECKeyPair>()
    val walletKeys: LiveData<ECKeyPair>
        get() = _keys

    private val _walletFile = MutableLiveData<File>()
    val walletFile: LiveData<File>
        get() = _walletFile


    private val web3: Web3j = Web3j.build(HttpService(NODE_URL))


    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: Shutting down web3")
        web3.shutdown()
    }

    fun test() {
        Log.d(TAG, "test: Node URL: $NODE_URL")
        Log.d(TAG, "test: Wallet Path: ${walletPath.absolutePath}")
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

    fun createWallet() {
        if (walletFile.value != null) {
            Log.d(TAG, "createWallet: Wallet is already created!")
            return
        }

        if (injectedFileName.isNotBlank()) {
            _walletFile.value = File(walletPath, injectedFileName).also {
                Log.d(TAG, "Wallet LOADED: ${it.absolutePath}")
            }
            return
        }

        Single.fromCallable {
            WalletUtils.generateBip39Wallet(walletPassword, walletPath)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newWallet ->
                //Insert this File name into a database ^
                Log.d(TAG, "Wallet generated: ${newWallet.filename}")
                Log.d(TAG, "Phrase: ${newWallet.mnemonic}")
                _walletFile.value = File(walletPath, newWallet.filename).also { file ->
                    Log.d(TAG, "Wallet generated: ${file.absolutePath}")
                }
            }, {
                Log.e(TAG, "Could not create wallet", it)
            }).disposeOnClear()

    }

    fun getWalletInformation() {
        if (walletFile.value == null) {
            Log.d(TAG, "createWallet: Wallet is not created yet!")
            return
        }
        Single.fromCallable {
            WalletUtils.loadCredentials(walletPassword, walletFile.value)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ credentials ->
                Log.d(TAG, "Your address is " + credentials.address)
                Log.d(TAG, "Your address is " + credentials.ecKeyPair.privateKey.toString(16))
                Log.d(TAG, "Your address is " + credentials.ecKeyPair.publicKey.toString(16))
                _address.value = credentials.address
                _keys.value = credentials.ecKeyPair
                Log.d(TAG, "getWalletInformation: Got Wallet info")
            }, {
                Log.e(TAG, "Could not get wallet address", it)
            }).disposeOnClear()

    }

//    fun sendTransaction() {
//        try {
//            val credentials: Credentials = WalletUtils.loadCredentials(walletPassword, walletFile)
//            val receipt = Transfer.sendFunds(
//                web3,
//                credentials,
//                "0x31B98D14007bDEe637298086988A0bBd31184523",
//                BigDecimal(1),
//                Convert.Unit.ETHER
//            ).sendAsync().get()
//            Log.d(TAG, "Transaction complete: " + receipt.transactionHash)
//        } catch (e: Exception) {
//            Log.e(TAG, "Could not create wallet", e)
//        }
//    }

    //    // Workaround for bug with ECDA signatures.
//
    companion object {
        private const val TAG = "ChillieAuthVM"
        private const val NODE_URL = "https://bsc-dataseed.binance.org/"
    }
}