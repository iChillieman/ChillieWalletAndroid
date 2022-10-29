package com.chillieman.chilliewallet.ui.main.wallet

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentWalletBinding
import com.chillieman.chilliewallet.ui.base.BaseHybridViewModelFragment
import com.chillieman.chilliewallet.ui.main.MainViewModel
import com.chillieman.chilliewallet.ui.main.wallet.tokenlist.TokenForList
import com.chillieman.chilliewallet.ui.main.wallet.tokenlist.TokenListAdapter
import com.chillieman.chilliewallet.ui.main.wallet.tokenlist.TokenSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal

@AndroidEntryPoint
class WalletFragment : BaseHybridViewModelFragment<WalletViewModel, MainViewModel>(
    WalletViewModel::class.java,
    MainViewModel::class.java
), TokenSelectedListener {
    private var _binding: FragmentWalletBinding? = null
    private val tokenAdapter by lazy {
        TokenListAdapter(requireContext(), this)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)

        sharedViewModel.selectedWallet.observe(viewLifecycleOwner) {
            binding.content.visibility = View.VISIBLE

            //TODO: Load Wallet Specific Stuff!
            sharedViewModel.getBalance()
            sharedViewModel.getAddress()
            viewModel.loadTokens(it)
        }

        binding.rvWalletTokenList.apply {
            adapter = tokenAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.refreshWallet.setOnRefreshListener {
            binding.tvWalletBalance.setText(R.string.loading)
            sharedViewModel.getBalance()
            viewModel.loadTokens(sharedViewModel.selectedWallet.value)
        }

        viewModel.tokensForList.observe(viewLifecycleOwner) {
            tokenAdapter.loadItems(it)
        }

        sharedViewModel.balance.observe(viewLifecycleOwner) {
            val rounded = it.setScale(4, BigDecimal.ROUND_DOWN)
            val string = "$rounded BNB"
            binding.tvWalletBalance.text = string

            binding.refreshWallet.isRefreshing = false
        }

        sharedViewModel.address.observe(viewLifecycleOwner) { address ->
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
