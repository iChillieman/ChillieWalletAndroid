package com.chillieman.chilliewallet.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ActivityMainBinding
import com.chillieman.chilliewallet.definitions.IntentDefinitions
import com.chillieman.chilliewallet.model.AuthStatus
import com.chillieman.chilliewallet.service.AuthService
import com.chillieman.chilliewallet.ui.auth.AuthActivity
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity
import com.chillieman.chilliewallet.ui.newwallet.NewWalletActivity

class MainActivity : BaseViewModelActivity<MainViewModel>(MainViewModel::class.java) {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        viewModel.startAuth()

        viewModel.authStatus.observe(this) {
            when (it) {
                AuthStatus.NEED_TO_CREATE -> {
                    binding.container.visibility = View.GONE
                    startActivity(
                        Intent(this, AuthActivity::class.java)
                            .putExtra(IntentDefinitions.EXTRA_NEED_TO_CREATE_AUTH, true)
                    )
                }
                AuthStatus.UNAUTHENTICATED -> {
                    binding.container.visibility = View.GONE
                    Log.d(TAG, "Unauthenticated")
                    startActivity(Intent(this, AuthActivity::class.java))

                }
                AuthStatus.AUTHENTICATED -> {
                    Log.d(TAG, "Authenticated")
                    if(viewModel.checkWallet()) {
                        binding.container.visibility = View.VISIBLE
                    }
                }
                else -> Unit
            }
        }

        viewModel.isWalletCreated.observe(this) {
            if(!it) {
                startActivity(
                    Intent(this, NewWalletActivity::class.java)
                        .putExtra(IntentDefinitions.EXTRA_IS_FIRST_WALLET, true)
                )
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressMain.visibility = View.VISIBLE
            } else {
                binding.progressMain.visibility = View.GONE
                binding.navView.visibility = View.VISIBLE
            }
        }

        viewModel.selectedWallet.observe(this) {
            binding.container.visibility = View.VISIBLE
        }

        startService(Intent(this, AuthService::class.java))
    }



    override fun onDestroy() {
        Log.d(TAG, "OnDestroy")
        super.onDestroy()
    }

    private val TAG = "ChillieMain"
}