package com.chillieman.chilliewallet.util

import com.chillieman.chilliewallet.db.entity.AuthDatum
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
    fun encryptMessage(message: String): AuthDatum {
        val plaintext: ByteArray = message.toByteArray()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val cipherText = cipher.doFinal(plaintext)
        return AuthDatum(cipherText, cipher.iv)
    }

    fun decryptMessage(datum: AuthDatum): String {
        val spec = GCMParameterSpec(128, datum.iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        val decryptedMessage = cipher.doFinal(datum.payload)
        return String(decryptedMessage)
    }
}
