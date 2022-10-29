package com.chillieman.chilliewallet.ui.newwallet

import android.os.Bundle
import com.chillieman.chilliewallet.databinding.ActivityNewWalletBinding
import com.chillieman.chilliewallet.definitions.IntentDefinitions
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity
import com.chillieman.chilliewallet.ui.newwallet.pages.NewWalletCreateFragment
import com.chillieman.chilliewallet.ui.newwallet.pages.NewWalletImportFragment
import com.chillieman.chilliewallet.ui.newwallet.pages.NewWalletIntroFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewWalletActivity :
    BaseViewModelActivity<NewWalletViewModel>(NewWalletViewModel::class.java) {
    private lateinit var binding: ActivityNewWalletBinding
    private val isFirstWallet by lazy {
        intent.getBooleanExtra(IntentDefinitions.EXTRA_IS_FIRST_WALLET, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val containerId = binding.container.id

        supportFragmentManager.beginTransaction()
            .add(containerId, NewWalletIntroFragment.newInstance(isFirstWallet))
            .commit()

        viewModel.isCreateNewWallet.observe(this) {
            val fragmentToAdd = if (it) {
                NewWalletCreateFragment()
            } else {
                NewWalletImportFragment()
            }

            supportFragmentManager.beginTransaction()
                .add(containerId, fragmentToAdd)
                .addToBackStack(null)
                .commit()
        }

        viewModel.isConfirmed.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (!isFirstWallet) {
            super.onBackPressed()
        }
    }
}
