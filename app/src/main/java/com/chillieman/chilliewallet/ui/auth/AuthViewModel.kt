package com.chillieman.chilliewallet.ui.auth


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    private val authRepo: AuthRepository
) : BaseViewModel() {
    private val _isAuthSet = MutableLiveData<Boolean>()
    val isAuthSet: LiveData<Boolean>
        get() = _isAuthSet

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState>
        get() = _authState

    private val _errorState = MutableLiveData<AuthErrorState>()
    val errorState: LiveData<AuthErrorState>
        get() = _errorState

    fun isAuthenticationSet() {
        authRepo.isAuthSet()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isAuthSet.value = it
            }, {
                Log.e(TAG, "Error Checking Authentication Status")
            })
            .disposeOnClear()
    }

    fun setAuthentication(pin: String, passphrase: String) {
        authRepo.createAuth(pin, passphrase)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _authState.value = AuthState.AUTHENTICATED
            }, {
                Log.e(TAG, "Error Checking Authentication Status")
            })
            .disposeOnClear()
    }

    fun checkPin(pin:String) {
        authRepo.checkAuthPin(pin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it) {
                    _authState.value = AuthState.AUTHENTICATED
                } else {
                    _errorState.value = AuthErrorState.WRONG_PIN
                }
            }, {
                Log.e(TAG, "Error Checking Authentication Status")
            })
            .disposeOnClear()
    }

    fun checkPassphrase(passphrase: String) {
        authRepo.checkPassphrase(passphrase)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it) {
                    _authState.value = AuthState.AUTHENTICATED
                } else {
                    _errorState.value = AuthErrorState.WRONG_PASSPHRASE
                }
            }, {
                Log.e(TAG, "Error Checking Authentication Status")
            })
            .disposeOnClear()
    }


    companion object {
        private const val TAG = "ChillieAuthVM"
        private const val NODE_URL = "https://bsc-dataseed.binance.org/"
    }
}