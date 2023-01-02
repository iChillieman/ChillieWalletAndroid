package com.chillieman.chilliewallet.ui.wallet.select

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentWalletSelectionBinding
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import com.chillieman.chilliewallet.definitions.ChillieWalletDefinitions.SELECTED_WALLET_PREFERENCE
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.wallet.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletSelectionFragment :
    BaseSharedViewModelFragment<WalletViewModel>(WalletViewModel::class.java),
    WalletSelectionAdapter.OnWalletSelectedListener {
    private var _binding: FragmentWalletSelectionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllWallets()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletSelectionBinding.inflate(inflater, container, false)
        binding.walletSelectList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.walletSelectionList.observe(viewLifecycleOwner) {
            binding.walletSelectList.adapter = WalletSelectionAdapter(it, this)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onWalletSelected(wallet: ChillieWallet) {
        viewModel.onWalletSelected(wallet.id)
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putLong(SELECTED_WALLET_PREFERENCE, wallet.id)
            apply()
        }
        findNavController().navigate(R.id.action_wallet_selection_to_main)
    }

    companion object {
        private const val TAG = "WalletSelectionFragment"
    }
}
