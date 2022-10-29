package com.chillieman.chilliewallet.ui.dex


import android.os.Bundle
import com.chillieman.chilliewallet.databinding.ActivityDexBinding
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity
import com.chillieman.chilliewallet.ui.dex.list.DexListFragment

class DexActivity : BaseViewModelActivity<DexViewModel>(DexViewModel::class.java) {
    private lateinit var binding: ActivityDexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDexBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DexListFragment.newInstance())
                .commitNow()
        }
    }

}