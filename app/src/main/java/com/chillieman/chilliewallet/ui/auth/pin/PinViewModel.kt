package com.chillieman.chilliewallet.ui.auth.pin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.manager.AuthManager
import com.chillieman.chilliewallet.model.AuthResponse
import com.chillieman.chilliewallet.model.AuthStatus
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PinViewModel
@Inject constructor(
    private val authManager: AuthManager
): BaseViewModel(){

    private val _authResponse = MutableLiveData<AuthResponse>()
    val authResponse: LiveData<AuthResponse>
        get() = _authResponse

    private val _errorState = MutableLiveData<PinErrorState>()
    val errorState: LiveData<PinErrorState>
        get() = _errorState

    private val _isPinStored = MutableLiveData<Boolean>()
    val isPinStored: LiveData<Boolean>
        get() = _isPinStored

    private var creationPin: String? = null

    fun processesPin(pin: String, isNewPin: Boolean?) {
        _errorState.value = PinErrorState.NONE

        if(isNewPin == true) {
            if(_isPinStored.value == true) {
                comparePinsForCreation(pin)
            } else {
                creationPin = pin
                _isPinStored.value = true
            }
        } else {
            //Verify Pin - Its not a new one
            checkPin(pin)
        }
    }

    private fun comparePinsForCreation(secondPin: String) {
        if(creationPin == secondPin) {
            // They entered the same pin twice, lets use it to create Auth Record
            authManager.createStartingPin(secondPin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _authResponse.value = AuthResponse.CREATED
                }, {
                    Log.e(TAG, "Error Checking Authentication Status", it)
                })
                .disposeOnClear()
        } else {
            _errorState.value = PinErrorState.PINS_DO_NOT_MATCH
            creationPin = null
            _isPinStored.value = false
        }
    }

    private fun checkPin(pin:String) {
        authManager.checkPin(pin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it) {
                    _authResponse.value = AuthResponse.CORRECT
                } else {
                    _errorState.value = PinErrorState.WRONG_PIN
                }
            }, {
                Log.e(TAG, "Error Checking Authentication Status")
            })
            .disposeOnClear()
    }

    companion object {
        private const val TAG = "ChilliePinVM"
    }
}