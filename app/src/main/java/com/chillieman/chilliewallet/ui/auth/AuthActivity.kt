package com.chillieman.chilliewallet.ui.auth

import android.os.Bundle
import android.util.Log
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ActivityAuthBinding
import com.chillieman.chilliewallet.definitions.IntentDefinitions
import com.chillieman.chilliewallet.model.AuthResponse
import com.chillieman.chilliewallet.model.ConnectionState
import com.chillieman.chilliewallet.ui.auth.pin.PinFragment
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity

class AuthActivity : BaseViewModelActivity<AuthViewModel>(AuthViewModel::class.java),
    PinFragment.Listener {
    private lateinit var binding: ActivityAuthBinding
    private var pinFragment: PinFragment? = null
    private val isFirstTime by lazy {
        intent.getBooleanExtra(IntentDefinitions.EXTRA_NEED_TO_CREATE_AUTH, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PinFragment.newInstance(isFirstTime, this).also {
            pinFragment = it
            supportFragmentManager.beginTransaction()
                .add(binding.content.id, it)
                .commit()
        }


    }

    override fun onPinResponse(response: AuthResponse) {
//        if (isFirstTime) {
//            //TODO: Direct user to the Password Creation screen!
//
//        }
        finish()
    }

    override fun onBackPressed() {
        pinFragment?.onBackPressed()
    }

    companion object {
        private const val TAG = "Chillie - AuthActivity"
    }


}