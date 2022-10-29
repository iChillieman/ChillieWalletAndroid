package com.chillieman.chilliewallet.ui.order

import android.os.Bundle
import com.chillieman.chilliewallet.databinding.ActivityOrderBinding
import com.chillieman.chilliewallet.ui.base.BaseViewModelActivity
import com.chillieman.chilliewallet.ui.order.create.OrderCreateFragment

class OrderActivity : BaseViewModelActivity<OrderViewModel>(OrderViewModel::class.java) {
    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, OrderCreateFragment.newInstance())
                .commit()
        }
    }
}