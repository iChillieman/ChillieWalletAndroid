package com.chillieman.chilliewallet.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.security.crypto.MasterKey
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ActivityMainBinding
import com.chillieman.chilliewallet.service.AuthService
import com.chillieman.chilliewallet.ui.base.BaseActivity
import java.security.KeyStore
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var authService: AuthService
    private val authConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as AuthService.AuthBinder
            authService = binder.getService()
            Log.d("Chillieman", "BOUND")
            Log.d("Chillieman", "Time When locked: ${authService.timeWhenUnlocked}")
//            Log.d("Chillieman", authService.testInjection.absolutePath)
        }

        override fun onServiceDisconnected(name: ComponentName?) = Unit //Do Nothing
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_wallet,
                R.id.navigation_dex,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //CHILLIEMAN - UNCOMMENT ME
//        startService(Intent(this, AuthService::class.java))
    }


    override fun onStart() {
        super.onStart()
        //CHILLIEMAN - UNCOMMENT ME
//        Intent(this, AuthService::class.java).also { intent ->
//            Log.d("Chillieman", "Binding the Service!")
//            bindService(intent, authConnection, Context.BIND_AUTO_CREATE)
//        }


//        https://developer.android.com/guide/topics/security/cryptography#encrypt-message
        // Encrypt a Message
        // DONE - Check out Encyrption


//        listOutKeyStoreEntries()




    }



    override fun onStop() {
        super.onStop()
//        unbindService(authConnection)
    }

    private val TAG = "ChillieCrypt"
}