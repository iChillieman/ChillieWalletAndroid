package com.chillieman.chilliewallet.ui.strategies.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentChillieChainListBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.strategies.ChillieChainViewModel

class ChillieChainListFragment : BaseSharedViewModelFragment<ChillieChainViewModel>(
    ChillieChainViewModel::class.java) {
    private var _binding: FragmentChillieChainListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChillieChainListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    companion object {
        fun newInstance() = ChillieChainListFragment()
    }
}