package com.chillieman.chilliewallet.ui.auth.password

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.model.AuthResponse
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.ui.auth.AuthViewModel
import com.chillieman.chilliewallet.ui.auth.pin.PinErrorState
import com.chillieman.chilliewallet.ui.auth.pin.PinViewModel
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import com.chillieman.chilliewallet.ui.main.wallet.WalletViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PasswordViewModel
@Inject constructor(
    private val authRepo: AuthRepository
) : BaseViewModel() {

    private val _authResponse = MutableLiveData<AuthResponse>()
    val authResponse: LiveData<AuthResponse>
        get() = _authResponse

    private val _errorState = MutableLiveData<PasswordErrorState>()
    val errorState: LiveData<PasswordErrorState>
        get() = _errorState

    fun comparePasswordsForCreation(firstPassword: String, secondPassword: String) {
        if (firstPassword == secondPassword) {
            // They entered the same pin twice, lets use it to create Auth Record
            createPassword(firstPassword)
        }
    }

    fun createPassword(password: String) {
        // TODO: Encrypt this

//        authRepo.updatePassphrase(password)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                _authResponse.value = AuthResponse.CREATED
//            }, {
//                Log.e(TAG, "Error Creating Password")
//            })
//            .disposeOnClear()


    }

    fun checkPassphrase(passphrase: String) {
        // TODO: Encrypt this

//        authRepo.checkPassphrase(passphrase)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                if (it) {
//                    _authResponse.value = AuthResponse.CORRECT
//                } else {
//                    _errorState.value = PasswordErrorState.WRONG_PASSWORD
//                }
//            }, {
//                Log.e(TAG, "Error Checking Password")
//            })
//            .disposeOnClear()
    }

    companion object {
        private const val TAG = "ChilliePinVM"
    }

}
