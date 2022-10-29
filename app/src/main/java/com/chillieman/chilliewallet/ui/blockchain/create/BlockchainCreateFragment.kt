package com.chillieman.chilliewallet.ui.blockchain.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentBlockchainCreateBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.blockchain.BlockchainViewModel

class BlockchainCreateFragment : BaseSharedViewModelFragment<BlockchainViewModel>(BlockchainViewModel::class.java) {

    private var _binding: FragmentBlockchainCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlockchainCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    companion object {
        fun newInstance() = BlockchainCreateFragment()
    }
}