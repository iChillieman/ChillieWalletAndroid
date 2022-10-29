package com.chillieman.chilliewallet.ui.strategies.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentChillieChainDetailBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.strategies.ChillieChainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChillieChainDetailFragment : BaseSharedViewModelFragment<ChillieChainViewModel>(
    ChillieChainViewModel::class.java
) {
    private var _binding: FragmentChillieChainDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChillieChainDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ChillieChainDetailFragment()
    }
}