package com.chillieman.chilliewallet.ui.strategies


import android.os.Bundle
import com.chillieman.chilliewallet.databinding.ActivityChillieChainBinding
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity
import com.chillieman.chilliewallet.ui.strategies.create.ChillieChainCreateFragment

class ChillieChainActivity : BaseViewModelActivity<ChillieChainViewModel>(ChillieChainViewModel::class.java) {
    private lateinit var binding: ActivityChillieChainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChillieChainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, ChillieChainCreateFragment.newInstance())
                .commitNow()
        }
    }
}