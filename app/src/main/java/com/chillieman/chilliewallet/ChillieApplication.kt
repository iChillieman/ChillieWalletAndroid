package com.chillieman.chilliewallet

import android.util.Log
import androidx.security.crypto.MasterKey
import com.chillieman.chilliewallet.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.KeyStore
import java.security.Provider
import java.security.Security
import javax.inject.Inject

class ChillieApplication : DaggerApplication() {

    @Inject
    lateinit var keyStore: KeyStore

    override fun applicationInjector(): AndroidInjector<out ChillieApplication> {
        return DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .apply {
                inject(this@ChillieApplication)
            }
    }

    override fun onCreate() {
        super.onCreate()
//        val provider: Provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
//            ?: // Web3j will set up the provider lazily when it's first used.
//            return
//        if (provider is BouncyCastleProvider) {
//            // BC with same package name, shouldn't happen in real life.
//            return
//        }
//        // Android registers its own BC provider. As it might be outdated and might not include
//        // all needed ciphers, we substitute it with a known BC bundled in the app.
//        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
//        // of that it's possible to have another BC implementation loaded in VM.
//        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
//        Security.insertProviderAt(BouncyCastleProvider(), 1)


        //If a Master Key is not created, create one - It will be needed for all encryption.
        if (keyStore.size() == 0) {
            Log.d("ChillieCrypt", "Creating Master Key")
            MasterKey.Builder(applicationContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
        }
    }
}
