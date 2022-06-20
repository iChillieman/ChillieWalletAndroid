package com.chillieman.chilliewallet.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.model.AuthStatus
import com.chillieman.chilliewallet.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager
@Inject constructor(
    private val authRepository: AuthRepository
){
    private val _authStatus = MutableLiveData<AuthStatus>().apply {
        value = AuthStatus.AUTHENTICATED
    }

    val authStatus: LiveData<AuthStatus>
        get() = _authStatus

    fun setAuthenticationStatus(status: AuthStatus) {
        _authStatus.value = status
    }

    fun isAuthCreated() = authRepository.isAuthSet()
}