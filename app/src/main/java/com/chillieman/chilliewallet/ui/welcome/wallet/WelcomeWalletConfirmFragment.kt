package com.chillieman.chilliewallet.ui.welcome.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentWelcomeWalletConfirmBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.welcome.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeWalletConfirmFragment :
    BaseSharedViewModelFragment<WelcomeViewModel>(WelcomeViewModel::class.java) {
    private var _binding: FragmentWelcomeWalletConfirmBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeWalletConfirmBinding.inflate(inflater, container, false)

        binding.btnConfirmWallet.setOnClickListener {
            viewModel.isSeedConfirmationValid(binding.welcomeWalletConfirmSeed.text.toString())
        }

        viewModel.errorStringId.observe(viewLifecycleOwner) {
            if (it > 0) {
                viewModel.consumeError()
                binding.errorMessage.text = getString(it)
                binding.errorMessage.visibility = View.VISIBLE
            } else {
                binding.errorMessage.visibility = View.GONE
            }
        }

        viewModel.isWalletConfirmed.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Wallet Created!", Toast.LENGTH_SHORT).show()
            mainViewModel.isWelcome = false
            findNavController().navigate(R.id.action_welcome_wallet_confirm_to_select)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
