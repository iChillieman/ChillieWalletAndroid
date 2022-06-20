package com.chillieman.chilliewallet.ui.auth

import android.os.Bundle
import android.util.Log
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ActivityAuthBinding
import com.chillieman.chilliewallet.definitions.IntentDefinitions
import com.chillieman.chilliewallet.model.ConnectionState
import com.chillieman.chilliewallet.ui.auth.pin.PinFragment
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

        //See if this is the first time, and the user needs to create a PINCODE
        val isFirstTime = intent.getBooleanExtra(IntentDefinitions.EXTRA_NEED_TO_CREATE_AUTH, false)

        val fragment = PinFragment.newInstance(
            isFirstTime,
            "Chillieman says hi!"
        )

        supportFragmentManager.beginTransaction()
            .add(binding.content.id, fragment)
            .commit()

    }

    override fun onBackPressed() = Unit // Back Button does nothing on this screen

    companion object {
        private const val TAG = "Chillie - AuthActivity"
    }
}