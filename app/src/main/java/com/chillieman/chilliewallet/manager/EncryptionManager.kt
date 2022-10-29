package com.chillieman.chilliewallet.manager

import com.chillieman.chilliewallet.db.entity.AuthDatum
import io.reactivex.Single
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptionManager
@Inject constructor(
    private val cipher: Cipher,
    private val secretKey: SecretKey
) {
    fun encryptMessage(message: String): Single<AuthDatum> {
        return Single.fromCallable {
            val plaintext: ByteArray = message.toByteArray()
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val cipherText = cipher.doFinal(plaintext)
            AuthDatum(cipherText, cipher.iv)
        }
    }

    fun decryptMessage(datum: AuthDatum): Single<String> {
        return Single.fromCallable {
            val spec = GCMParameterSpec(128, datum.iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
            val decryptedMessage = cipher.doFinal(datum.payload)
            String(decryptedMessage)
        }
    }
}
