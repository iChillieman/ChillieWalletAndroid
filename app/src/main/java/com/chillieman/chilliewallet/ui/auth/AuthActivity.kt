package com.chillieman.chilliewallet.ui.auth

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity
import kotlinx.android.synthetic.main.activity_auth.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class AuthActivity : BaseViewModelActivity<AuthViewModel>(AuthViewModel::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        Log.d(TAG, "onCreate: Hello...")

        observeLiveData()

        viewModel.test()

        btn_connect.setOnClickListener {
            viewModel.connectToEthNetwork()
        }

        btn_create_wallet.setOnClickListener {
            viewModel.createWallet()
        }

        btn_load_wallet_information.setOnClickListener {
            viewModel.getWalletInformation()
        }

    }

    private fun observeLiveData() {
        viewModel.connectionState.observe(this){
            tv_connection_status.text ="${getString(it.stringRes)} "

            if(it == ConnectionState.CONNECTED) {
                tv_connection_status.setTextColor(getColor(R.color.green))
            } else {
                tv_connection_status.setTextColor(getColor(R.color.red))
            }
        }

        viewModel.walletAddress.observe(this) {
            tv_wallet_address.text = it
        }

        viewModel.walletKeys.observe(this) {
            tv_wallet_private_key.text = String.format(it.privateKey.toString(16))
            tv_wallet_public_key.text = String.format(it.publicKey.toString(16))
        }

        viewModel.walletFile.observe(this){
            Log.d(TAG, "observeLiveData: Detected Wallet! ${it.absolutePath}")
            tv_wallet_status.text = getString(R.string.detected)
            tv_wallet_status.setTextColor(getColor(R.color.green))
        }
    }


    companion object{
        private const val TAG = "Chillie - AuthActivity"
    }
}