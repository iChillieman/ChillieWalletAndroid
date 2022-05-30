package com.chillieman.chilliewallet.ui.auth

import android.os.Bundle
import android.util.Log
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ActivityAuthBinding
import com.chillieman.chilliewallet.model.ConnectionState
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity

class AuthActivity : BaseViewModelActivity<AuthViewModel>(AuthViewModel::class.java) {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Can be started for reasons:
        //  - User needs to prove that they can access system
        //  - User needs to set a PIN and Password
        Log.d(TAG, "onCreate: Hello...")


        //TODO: First See if An Authentication Records Exists.

        //If Auth Exists, Request PIN Number
        //If Passphrase is missing, require it from the user.
        //  - Passphrase will be required to Withdrawal Funds from the wallet.
        //  - Passphrase will be required to Create Workers/Tasks
        //If passphrase is NOT NULL, Then Goto Main (Or where ever you were)


    }

    companion object {
        private const val TAG = "Chillie - AuthActivity"
    }
}