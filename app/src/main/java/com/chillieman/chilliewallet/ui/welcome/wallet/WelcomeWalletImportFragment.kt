package com.chillieman.chilliewallet.ui.welcome.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentWelcomeWalletImportBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.welcome.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeWalletImportFragment :
    BaseSharedViewModelFragment<WelcomeViewModel>(WelcomeViewModel::class.java) {
    private var _binding: FragmentWelcomeWalletImportBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeWalletImportBinding.inflate(inflater, container, false)

        binding.btnImportWallet.setOnClickListener {
            val words = binding.welcomeWalletImportSeed.text.toString().trim()

            val wordList = mutableListOf<String>()
            words.split(' ').forEach {
                wordList.add(it.trim())
            }

            if (wordList.size != 12) {
                Toast.makeText(requireContext(), R.string.error_seed_word_size, Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            viewModel.importWallet(words)
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
            Toast.makeText(requireContext(), "Wallet imported!", Toast.LENGTH_SHORT).show()
            mainViewModel.isWelcome = false
            findNavController().navigate(R.id.action_welcome_wallet_import_to_selection)
        }




        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
