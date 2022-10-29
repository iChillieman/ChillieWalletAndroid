package com.chillieman.chilliewallet.di

import android.content.Context
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.URL_SMART_CHAIN_TESTNET
import dagger.Module
import dagger.Provides
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
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
    internal fun provideWeb3(): Web3j = Web3j.build(HttpService(URL_SMART_CHAIN_TESTNET))

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
    internal fun provideMasterSecretKey(keyStore: KeyStore): SecretKey {
        val alias = keyStore.aliases().toList().first()

        val entry: KeyStore.Entry = keyStore.getEntry(alias, null)
        if (entry !is KeyStore.SecretKeyEntry) {
            throw IllegalStateException("Not an instance of a SecretKey")
        } else {
            return entry.secretKey
        }
    }
}
