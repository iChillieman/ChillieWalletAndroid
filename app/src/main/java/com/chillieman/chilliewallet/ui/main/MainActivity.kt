package com.chillieman.chilliewallet.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ActivityMainBinding
import com.chillieman.chilliewallet.model.AuthStatus
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseViewModelActivity<MainViewModel>(MainViewModel::class.java) {
    private lateinit var binding: ActivityMainBinding
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        viewModel.authStatus.observe(this) {
            if (it == AuthStatus.UNAUTHENTICATED) {
                // TODO - NAVIGATE TO AUTH PIN FRAGMENT
                binding.navView.visibility = View.GONE
            } else if (it == AuthStatus.AUTHENTICATED) {
                binding.navView.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressMain.visibility = View.VISIBLE
            } else {
                binding.progressMain.visibility = View.GONE
            }
        }

//        startService(Intent(this, AuthService::class.java))
    }

    override fun onPause() {
        super.onPause()
        alertDialog?.dismiss()
    }

    override fun onDestroy() {
        Log.d(TAG, "OnDestroy")
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (viewModel.isWelcome) {
            alertDialog = AlertDialog.Builder(this)
                .setTitle(R.string.exit)
                .setMessage("If you leave now, some progress may be lost. Are you sure?")
                .setPositiveButton(R.string.exit) { _, _ ->
                    finish()
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .setCancelable(false)
                .show()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val TAG = "ChillieMainActivity"
    }
}
