package com.chillieman.chilliewallet.di

import android.content.Context
import android.util.Log
import com.chillieman.chilliewallet.ui.main.wallet.WalletFragment
import dagger.Module
import dagger.Provides
import java.io.File
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey

@Module
class ProviderModule {
    @Provides
    internal fun provideFileDirectory(context: Context): File {
        return context.filesDir.absoluteFile
    }

    @Provides
    internal fun provideKeyStore(): KeyStore {
        return KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
    }

    @Provides
    internal fun provideCipher(): Cipher {
        return Cipher.getInstance("AES/GCM/NoPadding")
    }

    @Provides
    internal fun provideMasterSecretKey(keyStore: KeyStore) : SecretKey {
        val alias = keyStore.aliases().toList().first()

        val entry: KeyStore.Entry = keyStore.getEntry(alias, null)
        if (entry !is KeyStore.SecretKeyEntry) {
            throw IllegalStateException("Not an instance of a SecretKey")
        } else {
            return entry.secretKey
        }
    }
}