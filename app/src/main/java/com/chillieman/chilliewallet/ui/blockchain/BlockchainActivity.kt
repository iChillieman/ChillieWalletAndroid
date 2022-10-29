package com.chillieman.chilliewallet.ui.blockchain

import android.os.Bundle
import com.chillieman.chilliewallet.databinding.ActivityBlockchainBinding
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity
import com.chillieman.chilliewallet.ui.blockchain.list.BlockchainListFragment

class BlockchainActivity : BaseViewModelActivity<BlockchainViewModel>(BlockchainViewModel::class.java) {
    private lateinit var binding: ActivityBlockchainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlockchainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, BlockchainListFragment.newInstance())
                .commitNow()
        }
    }
}