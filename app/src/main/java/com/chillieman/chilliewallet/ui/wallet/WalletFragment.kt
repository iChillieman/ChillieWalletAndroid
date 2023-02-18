package com.chillieman.chilliewallet.ui.wallet

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillieman.chilliewallet.databinding.FragmentWalletBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.token.list.TokenForList
import com.chillieman.chilliewallet.ui.token.list.TokenListAdapter
import com.chillieman.chilliewallet.ui.token.list.TokenSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : BaseSharedViewModelFragment<WalletViewModel>(WalletViewModel::class.java),
    TokenSelectedListener {
    private var _binding: FragmentWalletBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val tokenAdapter by lazy {
        TokenListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)

        binding.rvWalletTokenList.apply {
            adapter = tokenAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.refreshWallet.setOnRefreshListener {
            viewModel.refreshSelectedWallet()
        }

        viewModel.tokensForList.observe(viewLifecycleOwner) {
            tokenAdapter.loadItems(it)
            binding.refreshWallet.isRefreshing = false
        }

        viewModel.balance.observe(viewLifecycleOwner) {
            val scale = 4
//            val rounded = it.setScale(scale, RoundingMode.HALF_DOWN)
            val string = "$it BNB"
//            val string = if (it == BigDecimal.ZERO) {
//                "0 BNB"
//            } else {
//                "$it BNB"
//            }
            binding.tvWalletBalance.text = string
        }

        viewModel.address.observe(viewLifecycleOwner) { address ->
            binding.tvWalletAddress.text = address
            binding.card.setOnClickListener {
                val clipboardService =
                    requireContext().getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("Chillieman says Hi", address)
                clipboardService.setPrimaryClip(clipData)

                Toast.makeText(
                    requireContext(),
                    "Address Copied to Clipboard",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTokenSelected(tokenForList: TokenForList) {
        //TODO CHILLIE - Handle Token selection logic

    }


    companion object {
        private const val TAG = "ChillieWalletFragment"
    }
}
