package com.chillieman.chilliewallet.repository

import android.util.Log
import com.chillieman.chilliewallet.db.dao.AuthDao
import com.chillieman.chilliewallet.db.dao.AuthDatumDao
import com.chillieman.chilliewallet.db.entity.Authentication
import com.chillieman.chilliewallet.definitions.AuthenticationDefinitions.DEFAULT_ID
import com.chillieman.chilliewallet.manager.EncryptionManager
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository
@Inject constructor(
    private val authDao: AuthDao,
    private val authDatumDao: AuthDatumDao,
    private val encryptionManager: EncryptionManager
) {
    fun isAuthSet(): Single<Boolean> {
        return authDao.select().map {
            true
        }.onErrorReturnItem(false)
    }

    fun createStartingPin(pin: String): Single<Long> {
        // If this is the first time Creating the Pin, you need to create a random password for wallets
        var pinId: Long? = null
        return encryptionManager.encryptMessage(pin)
            .flatMap {
                authDatumDao.insert(it)
            }.flatMap {
                pinId = it
                val randomBytes = ByteArray(64)
                Random().nextBytes(randomBytes)
                Log.d("Chillieman Rules", "Wallet Password: ${String(randomBytes)}")
                encryptionManager.encryptMessage(String(randomBytes))
            }.flatMap {
                authDatumDao.insert(it)
            }.flatMap {
                authDao.insert(Authentication(DEFAULT_ID, pinId!!, it, null))
            }
    }

    fun updatePassphrase(newPassphrase: String): Single<Int> {
        var newPassphraseId: Long? = null
        return encryptionManager.encryptMessage(newPassphrase)
            .flatMap {
                authDatumDao.insert(it)
            }.flatMap {
                newPassphraseId = it
                authDao.select()
            }.flatMap {
                authDao.update(
                    Authentication(
                        it.id,
                        it.pinId,
                        it.walletPasswordId,
                        newPassphraseId
                    )
                )
            }
    }

    fun updatePin(newPin: String): Single<Int> {
        var newPinId: Long? = null
        return encryptionManager.encryptMessage(newPin)
            .flatMap {
                authDatumDao.insert(it)
            }.flatMap {
                newPinId = it
                authDao.select()
            }.flatMap {
                authDao.update(
                    Authentication(
                        it.id,
                        newPinId!!,
                        it.walletPasswordId,
                        it.userPasswordId
                    )
                )
            }
    }

    fun checkAuthPin(pin: String): Single<Boolean> {
        return authDao.select().flatMap {
            val datum = authDatumDao.selectByIdSynchronously(it.pinId)
            encryptionManager.decryptMessage(datum)
        }.map {
            pin == it
        }
    }

    fun getWalletPassword(): Single<String> {
        return authDao.select().flatMap {
            val datum = authDatumDao.selectByIdSynchronously(it.walletPasswordId)
            encryptionManager.decryptMessage(datum)
        }
    }

    fun isPassphraseSet(): Single<Boolean> {
        return authDao.select().map {
            it.userPasswordId != null
        }
    }

    fun checkAuthPassword(password: String): Single<Boolean> {
        return authDao.select().flatMap {
            if (it.userPasswordId == null) {
                throw IllegalStateException("You're checking the Auth Password, but none exists!")
            }
            val datum = authDatumDao.selectByIdSynchronously(it.userPasswordId)
            encryptionManager.decryptMessage(datum)
        }.map {
            password == it
        }
    }

    fun resetAuthentication(): Single<Int> {
        return authDao.delete()
    }
}