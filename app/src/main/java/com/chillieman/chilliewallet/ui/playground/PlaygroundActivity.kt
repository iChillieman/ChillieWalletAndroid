package com.chillieman.chilliewallet.ui.playground

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ActivityPlaygroundBinding
import com.chillieman.chilliewallet.model.ConnectionState
import com.chillieman.chilliewallet.service.AuthService
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity
import com.chillieman.chilliewallet.worker.ChillieWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
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



        binding.btnConnect.text = "Connect to ETH Network"
        binding.btnConnect.setOnClickListener {
//            viewModel.fillAlphaDatabase()
//            viewModel.connectToEthNetwork()
            scheduleExampleWorker()

        }

        viewModel.isDataFilled.observe(this) {
            if (it) {
                Toast.makeText(this, "Alpha DB is filled", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLoadWalletInformation.text = "Start Service"
        binding.btnLoadWalletInformation.setOnClickListener {
            startService(Intent(this, AuthService::class.java))
            scheduleExampleWorker()
//            viewModel.loadWallet()
//            viewModel.importWallet(binding.etImportWallet.text.toString())

        }

//        binding.btnSomethingElse.isEnabled = false
        binding.btnSomethingElse.text = "Cancel Work"
        binding.btnSomethingElse.setOnClickListener {
            WorkManager.getInstance(applicationContext)
                .cancelUniqueWork("chillie_work")

        }

    }

    fun scheduleExampleWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val repeatingRequest = PeriodicWorkRequestBuilder<ChillieWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "chillie_work",
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
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
            binding.tvWalletPublicKey.text = String.format(it.publicKey.toString(16))
            binding.btnLoadWalletInformation.isEnabled = false
        }

        viewModel.blockNumber.observe(this) {
            binding.tvWalletPrivateKey.text = it.toString()
        }
    }


    companion object {
        private const val TAG = "ChilliePlayActivity"
    }
}
