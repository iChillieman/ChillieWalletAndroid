package com.chillieman.chilliewallet.ui.main.wallet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WalletViewModel
@Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Launch the BarcodeActivity"
    }
    val text: LiveData<String> = _text


    private val _isCreating = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isCreating: LiveData<Boolean> = _isCreating

    fun createPin() {
        _isCreating.value = true
        authRepository.createStartingPin("1234")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Success in Creating pin!")
                _isCreating.value = false
            }, {
                Log.e(TAG, "Could not create pin", it)
                _isCreating.value = false
            }).disposeOnClear()
    }

    fun checkPin() {
        authRepository.checkAuthPin("1235")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Success in Checking pin!")
                Log.d(TAG, "PIN Result: $it")
            }, {
                Log.e(TAG, "Could not create pin", it)
            }).disposeOnClear()
    }

    fun createPassword() {
        _isCreating.value = true
        authRepository.updatePassphrase("ChilliemanIsTheBest123!")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Success in Creating password!")
                _isCreating.value = false
            }, {
                Log.e(TAG, "Could not create password", it)
                _isCreating.value = false
            }).disposeOnClear()
    }

    fun checkPassword() {
        authRepository.checkAuthPassword("ChilliemanI23!")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Success in Checking password!")
                Log.d(TAG, "PASSWORD Result: $it")
            }, {
                Log.e(TAG, "Could not create pin", it)
            }).disposeOnClear()
    }

    companion object {
        private const val TAG = "ChillieLog"
    }
}