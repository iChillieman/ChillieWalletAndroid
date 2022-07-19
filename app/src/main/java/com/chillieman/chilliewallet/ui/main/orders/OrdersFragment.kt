package com.chillieman.chilliewallet.ui.main.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentOrdersBinding
import com.chillieman.chilliewallet.ui.base.BaseHybridViewModelFragment
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.main.MainViewModel

class OrdersFragment : BaseHybridViewModelFragment<OrdersViewModel, MainViewModel>(
    OrdersViewModel::class.java,
    MainViewModel::class.java
) {

    private var _binding: FragmentOrdersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textBot.text = it
        }


        return root
    }
}
