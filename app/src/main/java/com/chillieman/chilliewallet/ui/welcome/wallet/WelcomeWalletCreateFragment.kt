package com.chillieman.chilliewallet.ui.welcome.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentWelcomeWalletCreateBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.welcome.WelcomeViewModel
import com.chillieman.chilliewallet.ui.welcome.list.WelcomeWalletCreateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeWalletCreateFragment :
    BaseSharedViewModelFragment<WelcomeViewModel>(WelcomeViewModel::class.java) {
    private var _binding: FragmentWelcomeWalletCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeWalletCreateBinding.inflate(inflater, container, false)
        binding.wordList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.wordList.observe(viewLifecycleOwner) {
            binding.wordList.adapter = WelcomeWalletCreateAdapter(it)
        }
        binding.chkNewWalletConfirm.setOnCheckedChangeListener { _, isChecked ->
            binding.btnConfirmWallet.isEnabled = isChecked
        }

        binding.btnConfirmWallet.setOnClickListener {
            findNavController().navigate(R.id.action_welcome_wallet_create_to_confirm)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
