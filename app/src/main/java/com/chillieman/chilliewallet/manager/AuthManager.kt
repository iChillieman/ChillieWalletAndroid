package com.chillieman.chilliewallet.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.model.AuthStatus
import com.chillieman.chilliewallet.repository.AuthRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager
@Inject constructor(
    private val authRepository: AuthRepository
){

    var timeWhenUnlocked = 0L
        private set

    fun updatedUnlockedTime() {
        timeWhenUnlocked = Calendar.getInstance().timeInMillis
    }

    private val _authStatus = MutableLiveData<AuthStatus>()
    val authStatus: LiveData<AuthStatus>
        get() = _authStatus

    var isStartupComplete = false

    fun setAuthenticationStatus(status: AuthStatus) {
        _authStatus.postValue(status)
    }

    fun startUpComplete(status: AuthStatus) {
        if(!isStartupComplete) {
            _authStatus.postValue(status)
            isStartupComplete = true
        }
    }

    fun isAuthCreated() = authRepository.isAuthSet()

    fun checkPin(pin:String) = authRepository.checkAuthPin(pin).map {
        if(it) {
            setAuthenticationStatus(AuthStatus.AUTHENTICATED)
        }
        it
    }

    fun createStartingPin(pin: String)  = authRepository.createStartingPin(pin).map {
        setAuthenticationStatus(AuthStatus.AUTHENTICATED)
        it
    }

}