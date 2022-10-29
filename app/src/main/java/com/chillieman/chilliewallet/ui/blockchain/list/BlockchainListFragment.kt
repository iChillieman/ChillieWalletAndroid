package com.chillieman.chilliewallet.ui.blockchain.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentBlockchainListBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.blockchain.BlockchainViewModel

class BlockchainListFragment : BaseSharedViewModelFragment<BlockchainViewModel>(BlockchainViewModel::class.java) {

    private var _binding: FragmentBlockchainListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlockchainListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    companion object {
        fun newInstance() = BlockchainListFragment()
    }
}