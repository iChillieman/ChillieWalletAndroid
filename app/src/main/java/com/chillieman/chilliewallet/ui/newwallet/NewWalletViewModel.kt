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

    val selectedWallet: LiveData<ChillieWallet>
        get() = walletManager.selectedWallet

    private val _seedPhrase = MutableLiveData<List<String>>()
    val seedPhrase: LiveData<List<String>>
        get() = _seedPhrase

    fun createWallet() {
        walletManager.createNewWallet().map {
            createdWallet = it
            val listOfWords = mutableListOf<String>()
            val stringSeedPhrase = it.mnemonic

            val stringBuilder = StringBuilder()

            stringSeedPhrase.forEachIndexed { index, c ->
                if(c.isWhitespace()) {
                    listOfWords.add(stringBuilder.toString())
                    stringBuilder.clear()
                } else if (index == stringSeedPhrase.length - 1) {
                    stringBuilder.append(c)
                    listOfWords.add(stringBuilder.toString())
                    stringBuilder.clear()
                } else {
                    stringBuilder.append(c)
                }
            }

            listOfWords
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

    fun enterNewWalletIntoDatabase() {
        createdWallet?.let {
            walletManager.enterIntoDatabase(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .disposeOnClear()
        }

    }

    companion object {
        private const val TAG = "NewWalletViewModel"
    }

}