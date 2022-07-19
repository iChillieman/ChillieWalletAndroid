package com.chillieman.chilliewallet.ui.newwallet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import com.chillieman.chilliewallet.manager.WalletManager
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.web3j.crypto.Bip39Wallet
import javax.inject.Inject

class NewWalletViewModel
@Inject constructor(
    private val walletManager: WalletManager
) : BaseViewModel() {
    private var createdWallet: Bip39Wallet? = null
    private val _isCreateNewWallet = MutableLiveData<Boolean>()
    val isCreateNewWallet: LiveData<Boolean>
        get() = _isCreateNewWallet

    private val _seedPhrase = MutableLiveData<List<String>>()
    val seedPhrase: LiveData<List<String>>
        get() = _seedPhrase

    private val _isConfirmed = MutableLiveData<Boolean>()
    val isConfirmed: LiveData<Boolean>
        get() = _isConfirmed

    fun confirm() {
        walletManager.onConfirmWallet()
            .flatMap {
                walletManager.loadAlphaWallet()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Unconfirmed Wallet Loaded")
                _isConfirmed.value = true
            }, {
                Log.e(TAG, "Could not create wallet", it)
            }).disposeOnClear()

    }

    fun createWallet() {
        //Check if Wallet Exists First!
        walletManager.isWalletCreated().flatMap {
            if(it) {
                //If its Already Created... Load It!
                walletManager.getAlphaWalletSeedPhrase()
            } else {
                //Not created
                walletManager.createNewWallet()
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _seedPhrase.value = it
            }, {
                Log.e(TAG, "Could not create wallet", it)
            }).disposeOnClear()
    }

    fun setCreateOrImportWalletMode(isCreateNewWallet: Boolean) {
        _isCreateNewWallet.value = isCreateNewWallet
    }

    fun isEnteredSeedPhraseCorrect(inputWords: String) =
         inputWords == createdWallet?.mnemonic

    companion object {
        private const val TAG = "NewWalletViewModel"
    }
}
