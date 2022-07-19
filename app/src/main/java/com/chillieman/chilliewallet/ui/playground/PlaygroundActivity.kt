package com.chillieman.chilliewallet.ui.playground

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ActivityPlaygroundBinding
import com.chillieman.chilliewallet.model.ConnectionState
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity

class PlaygroundActivity :
    BaseViewModelActivity<PlaygroundViewModel>(PlaygroundViewModel::class.java) {
    private lateinit var binding: ActivityPlaygroundBinding

    //    private val authConnection = object : ServiceConnection {
//        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            // We've bound to LocalService, cast the IBinder and get LocalService instance
//            val binder = service as AuthService.AuthBinder
//            authService = binder.getService()
//        }
//
//        override fun onServiceDisconnected(name: ComponentName?) = Unit //Do Nothing
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaygroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate: Hello...")

        observeLiveData()

        binding.btnConnect.setOnClickListener {
            viewModel.connectToEthNetwork()
        }

        binding.btnLoadWalletInformation.setOnClickListener {
            viewModel.getWalletCredentials()
        }

        binding.btnSomethingElse.setOnClickListener {
//            viewModel.getWalletInformation()
            Toast.makeText(this, "Nothing to do!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun observeLiveData() {
        viewModel.connectionState.observe(this) {
            "${getString(it.stringRes)} ".also { text -> binding.tvConnectionStatus.text = text }

            if (it == ConnectionState.CONNECTED) {
                binding.tvConnectionStatus.setTextColor(getColor(R.color.green))
            } else {
                binding.tvConnectionStatus.setTextColor(getColor(R.color.red))
            }
        }

        viewModel.isLoading.observe(this) {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.walletAddress.observe(this) {
            binding.tvWalletAddress.text = it
        }

        viewModel.walletKeys.observe(this) {
            binding.tvWalletPrivateKey.text = "NEVER SHARE A PRIVATE KEY!!!! NEVER!!!"
            binding.tvWalletPublicKey.text = String.format(it.publicKey.toString(16))
            binding.btnLoadWalletInformation.isEnabled = false
        }
    }


    companion object {
        private const val TAG = "Chillie - AuthActivity"
    }
}
