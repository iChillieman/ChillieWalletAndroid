package com.chillieman.chilliewallet.ui.playground

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import com.chillieman.chilliewallet.model.ConnectionState
import com.chillieman.chilliewallet.repository.ChillieWalletRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.io.File
import java.math.BigDecimal
import java.net.SocketTimeoutException
import javax.inject.Inject

class PlaygroundViewModel
@Inject constructor(
    private val chillieWalletRepository: ChillieWalletRepository
) : BaseViewModel() {

    @Inject
    lateinit var walletPath: File
    private val walletPassword = "ChilliemanTest" // This should be unique to every android device

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

    fun createWallet() {
        if (walletFile.value != null) {
            Log.d(TAG, "createWallet: Wallet is already created / loaded!")
            return
        }

        //First see if the wallet exists:
        chillieWalletRepository.isWalletCreated().flatMap {
            if(it) {
                //It Exists!
                Log.d(TAG, "createWallet: Wallet is already created!")
                chillieWalletRepository.fetchWallet()
            } else {
                val newWallet = WalletUtils.generateBip39Wallet(walletPassword, walletPath)
                Log.d(TAG, "Wallet generated: ${newWallet.filename}")
                Log.d(TAG, "Phrase: ${newWallet.mnemonic}")

                val walletFile = File(walletPath, newWallet.filename)
                Log.d(TAG, "Full Wallet Path: ${walletFile.absolutePath}")

                chillieWalletRepository.createWallet("Chillieman", walletFile.absolutePath)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ wallet ->
                _walletFile.value = File(wallet.filePath)
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
                Log.d(TAG, "Your private key is " + credentials.ecKeyPair.privateKey.toString(16))
                Log.d(TAG, "Your public key is " + credentials.ecKeyPair.publicKey.toString(16))
                _address.value = credentials.address
                _keys.value = credentials.ecKeyPair
                Log.d(TAG, "getWalletInformation: Got Wallet info")
            }, {
                Log.e(TAG, "Could not get wallet address", it)
            }).disposeOnClear()

    }

    fun sendTransaction() {
        try {
            val credentials: Credentials = WalletUtils.loadCredentials(walletPassword, walletFile.value)
            val receipt = Transfer.sendFunds(
                web3,
                credentials,
                "0x31B98D14007bDEe637298086988A0bBd31184523",
                BigDecimal(1),
                Convert.Unit.ETHER
            ).sendAsync().get()
            Log.d(TAG, "Transaction complete: " + receipt.transactionHash)
        } catch (e: Exception) {
            Log.e(TAG, "Could not create wallet", e)
        }
    }

    companion object {
        private const val TAG = "ChilliePlayground"
        private const val NODE_URL = "https://bsc-dataseed.binance.org/"
        private const val TEST_NODE_URL = "https://data-seed-prebsc-1-s1.binance.org:8545/"
    }
}