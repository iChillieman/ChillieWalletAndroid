package com.chillieman.chilliewallet.ui.welcome.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentWelcomeWalletMenuBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.welcome.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeWalletMenuFragment :
    BaseSharedViewModelFragment<WelcomeViewModel>(WelcomeViewModel::class.java) {
    private var _binding: FragmentWelcomeWalletMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeWalletMenuBinding.inflate(inflater, container, false)

        binding.btnWelcomeWalletCreate.setOnClickListener {
            // Create the Wallet and navigate to Create Fragment
            viewModel.createWallet()
            findNavController().navigate(R.id.action_welcome_wallet_menu_to_create)
        }

        binding.btnWelcomeWalletImport.setOnClickListener {
            findNavController().navigate(R.id.action_welcome_wallet_menu_to_import)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
