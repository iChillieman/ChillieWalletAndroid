package com.chillieman.chilliewallet.ui.blockchain.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillieman.chilliewallet.databinding.FragmentBlockchainSelectionBinding
import com.chillieman.chilliewallet.model.data.FullBlockChainData
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockchainSelectionFragment :
    BaseSharedViewModelFragment<BlockchainViewModel>(BlockchainViewModel::class.java),
    BlockchainSelectionAdapter.OnBlockchainSelectedListener {
    private var _binding: FragmentBlockchainSelectionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadBlockchainItems()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlockchainSelectionBinding.inflate(inflater, container, false)
        binding.blockchainSelectList.layoutManager = LinearLayoutManager(requireContext())

        binding.blockchainSelectList
        viewModel.blockchainList.observe(viewLifecycleOwner) {

        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBlockchainSelected(blockchainData: FullBlockChainData) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "WalletSelectionFragment"
    }
}
