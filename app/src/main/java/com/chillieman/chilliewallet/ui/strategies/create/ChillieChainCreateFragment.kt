package com.chillieman.chilliewallet.ui.strategies.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentChillieChainCreateBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.strategies.ChillieChainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChillieChainCreateFragment : BaseSharedViewModelFragment<ChillieChainViewModel>(
    ChillieChainViewModel::class.java
) {
    private var _binding: FragmentChillieChainCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChillieChainCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ChillieChainCreateFragment()
    }
}