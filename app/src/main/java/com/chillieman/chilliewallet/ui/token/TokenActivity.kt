package com.chillieman.chilliewallet.ui.token

import android.os.Bundle
import com.chillieman.chilliewallet.databinding.ActivityTokenBinding
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity
import com.chillieman.chilliewallet.ui.token.create.TokenCreateFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TokenActivity : BaseViewModelActivity<TokenViewModel>(TokenViewModel::class.java) {
    private lateinit var binding: ActivityTokenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, TokenCreateFragment.newInstance())
                .commit()
        }
    }
}