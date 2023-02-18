package com.chillieman.chilliewallet

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.security.crypto.MasterKey
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import java.security.KeyStore
import javax.inject.Inject

@HiltAndroidApp
class ChillieApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var keyStore: KeyStore

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

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

            // TODO - Destroy Existing Database - IF IT EXISTS? - WHY THE FUCK DOES IT EXIST STILL
        }
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
