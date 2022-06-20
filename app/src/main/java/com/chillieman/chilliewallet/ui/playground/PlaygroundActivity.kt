package com.chillieman.chilliewallet.ui.playground

import android.os.Bundle
import android.util.Log
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ActivityPlaygroundBinding
import com.chillieman.chilliewallet.model.ConnectionState
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity

class PlaygroundActivity : BaseViewModelActivity<PlaygroundViewModel>(PlaygroundViewModel::class.java) {
    private lateinit var binding: ActivityPlaygroundBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaygroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate: Hello...")

        observeLiveData()

        binding.btnConnect.setOnClickListener {
            viewModel.connectToEthNetwork()
        }

        binding.btnCreateWallet.setOnClickListener {
            viewModel.createWallet()
        }

        binding.btnLoadWalletInformation.setOnClickListener {
            viewModel.getWalletInformation()
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

        viewModel.walletAddress.observe(this) {
            binding.tvWalletAddress.text = it
        }

        viewModel.walletKeys.observe(this) {
            binding.tvWalletPrivateKey.text = "NEVER SHARE A PRIVATE KEY!!!! NEVER!!!"
            binding.tvWalletPublicKey.text = String.format(it.publicKey.toString(16))
            binding.btnLoadWalletInformation.isEnabled = false
        }

        viewModel.walletFile.observe(this) {
            Log.d(TAG, "observeLiveData: Detected Wallet! ${it.absolutePath}")
            binding.tvWalletStatus.text = getString(R.string.detected)
            binding.tvWalletStatus.setTextColor(getColor(R.color.green))
            binding.btnLoadWalletInformation.isEnabled = true
            binding.btnCreateWallet.isEnabled = false
        }
    }


    companion object {
        private const val TAG = "Chillie - AuthActivity"
    }
}