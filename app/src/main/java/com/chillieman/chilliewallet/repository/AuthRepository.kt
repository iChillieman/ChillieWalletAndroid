package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.AuthDao
import com.chillieman.chilliewallet.db.entity.Authentication
import com.chillieman.chilliewallet.definitions.AuthenticationDefinitions.DEFAULT_ID
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository
@Inject constructor(
    private val authDao: AuthDao
) {
    fun isAuthSet(): Single<Boolean> {
       return authDao.select().map {
           true
       }.onErrorReturnItem(false)
    }

    fun createAuth(pin: String, passphrase: String): Single<Long> {
        return authDao.insert(Authentication(DEFAULT_ID, pin, passphrase))
    }

    fun updatePassphrase(newPassphrase: String): Single<Int> {
        return authDao.select().flatMap {
            authDao.update(Authentication(it.id, it.pin, newPassphrase))
        }
    }

    fun updatePin(newPin: String): Single<Int> {
        return authDao.select().flatMap {
            authDao.update(Authentication(it.id, newPin, it.passphrase))
        }
    }

    fun checkAuthPin(pin: String): Single<Boolean> {
        return authDao.select().map {
            it.pin == pin
        }
    }

    fun isPassphraseSet(): Single<Boolean> {
        return authDao.select().map {
            it.passphrase != null
        }
    }

    fun checkPassphrase(passphrase: String): Single<Boolean> {
        return authDao.select().map {
            it.passphrase == passphrase
        }
    }

    fun resetAuthentication(): Single<Int> {
        return authDao.delete()
    }
}