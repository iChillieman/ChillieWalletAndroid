package com.chillieman.chilliewallet.di

import android.app.Application
import android.content.Context
import com.chillieman.chilliewallet.definitions.BlockchainDefinitions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import java.io.File
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    fun bindContext(application: Application): Context = application.applicationContext

    @Provides
    internal fun provideWalletDirectory(context: Context): File {
        val file = File(context.filesDir.absoluteFile, "wallet")

        if (!file.exists()) {
            file.mkdir()
        }

        return context.filesDir.absoluteFile
    }

    // TODO - CHILLIEMAN - STOP INJECTING Web3j instances!
    @Provides
    internal fun provideWeb3(): Web3j =
        Web3j.build(HttpService(BlockchainDefinitions.Binance.DEFAULT_NODE_URL))

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
