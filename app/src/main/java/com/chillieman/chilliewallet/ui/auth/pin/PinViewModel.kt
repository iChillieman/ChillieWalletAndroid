package com.chillieman.chilliewallet.ui.auth.pin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.model.AuthResponse
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PinViewModel
@Inject constructor(
    private val authRepo: AuthRepository
): BaseViewModel(){

    private val _authResponse = MutableLiveData<AuthResponse>()
    val authResponse: LiveData<AuthResponse>
        get() = _authResponse

    private val _errorState = MutableLiveData<PinErrorState>()
    val errorState: LiveData<PinErrorState>
        get() = _errorState

    private lateinit var creationPin: String
    fun storePinForCreation(pin: String) {
        creationPin = pin
    }

//    fun comparePinsForCreation(secondPin: String) {
//        if(creationPin == secondPin) {
//            // They entered the same pin twice, lets use it to create Auth Record
//            createAuthentication(creationPin)
//        } else {
//            _errorState.value = PinErrorState.PINS_DO_NOT_MATCH
//        }
//    }


//    fun createAuthentication(pin: String) {
//        // TODO: Encrypt this
//
//
//        authRepo.createStartingPin(pin,"Test", "Test")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                _authResponse.value = AuthResponse.CREATED
//            }, {
//                Log.e(TAG, "Error Checking Authentication Status")
//            })
//            .disposeOnClear()
//    }
//
//    fun checkPin(pin:String) {
//        // Encrypt
//        authRepo.checkAuthPin(pin)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                if(it) {
//                    _authResponse.value = AuthResponse.CORRECT
//                } else {
//                    _errorState.value = PinErrorState.WRONG_PIN
//                }
//            }, {
//                Log.e(TAG, "Error Checking Authentication Status")
//            })
//            .disposeOnClear()
//    }

    companion object {
        private const val TAG = "ChilliePinVM"
    }
}