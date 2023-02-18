package com.chillieman.chilliewallet.repository

import android.util.Log
import com.chillieman.chilliewallet.db.dao.AuthDao
import com.chillieman.chilliewallet.db.dao.AuthDatumDao
import com.chillieman.chilliewallet.db.entity.Authentication
import com.chillieman.chilliewallet.model.enums.AuthStatus
import com.chillieman.chilliewallet.util.EncryptionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository
@Inject constructor(
    private val authDao: AuthDao,
    private val authDatumDao: AuthDatumDao,
    private val encryptionManager: EncryptionManager
) {
    private val _authState = MutableStateFlow(AuthStatus.INIT)
    val authState: StateFlow<AuthStatus>
        get() = _authState

    var timeWhenUnlocked = 0L
        private set

    fun updatedUnlockedTime() {
        timeWhenUnlocked = System.currentTimeMillis()
    }

    fun setAuthenticationStatus(status: AuthStatus) {
        Log.d("AuthRepo", "Auth Repo - Setting Auth State to $status")
        _authState.value = status
    }


    suspend fun isAuthCreated(): Boolean = authDao.count() > 0

    suspend fun createAuthData(password: String, pin: String) {
        val encryptedPassword = encryptionManager.encryptMessage(password)
        val encryptedPin = encryptionManager.encryptMessage(pin)

        val passwordId = authDatumDao.insert(encryptedPassword)
        val pinId = authDatumDao.insert(encryptedPin)
        val newAuth = Authentication(
            pinId = pinId,
            passwordId = passwordId
        )

        if (isAuthCreated()) {
            authDao.clear()
        }

        authDao.insert(newAuth)
    }


    suspend fun updatePassword(newPassword: String): Boolean {
        if (!isAuthCreated()) return false

        val newDatum = encryptionManager.encryptMessage(newPassword)
        val existingAuth = authDao.select()

        authDatumDao.delete(existingAuth.passwordId)

        val passwordId = authDatumDao.insert(newDatum)
        authDao.update(existingAuth.copy(passwordId = passwordId))

        return true
    }

    suspend fun updatePinCode(newPin: String): Boolean {
        if (!isAuthCreated()) return false

        val newDatum = encryptionManager.encryptMessage(newPin)
        val existingAuth = authDao.select()

        authDatumDao.delete(existingAuth.pinId)

        val passwordId = authDatumDao.insert(newDatum)
        authDao.update(existingAuth.copy(pinId = passwordId))

        return true
    }

    suspend fun isUserPasswordCorrect(password: String): Boolean {
        val auth = authDao.select()
        val datum = authDatumDao.selectById(auth.passwordId)
        return encryptionManager.decryptMessage(datum) == password
    }

    suspend fun isUserPinCorrect(pin: String): Boolean {
        val auth = authDao.select()
        val datum = authDatumDao.selectById(auth.pinId)
        return encryptionManager.decryptMessage(datum) == pin
    }

    suspend fun resetAuthentication() {
        authDao.clear()
        authDatumDao.clear()
    }
}
