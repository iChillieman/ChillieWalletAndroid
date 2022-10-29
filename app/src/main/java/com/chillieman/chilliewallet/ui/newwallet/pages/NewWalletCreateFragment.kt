package com.chillieman.chilliewallet.ui.newwallet.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentNewWalletCreateBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.newwallet.NewWalletViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewWalletCreateFragment :
    BaseSharedViewModelFragment<NewWalletViewModel>(NewWalletViewModel::class.java) {
    private var _binding: FragmentNewWalletCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewWalletCreateBinding.inflate(inflater, container, false)

        viewModel.createWallet()

        binding.chkNewWalletConfirm.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSaveWallet.isEnabled = isChecked
        }

        viewModel.seedPhrase.observe(viewLifecycleOwner) {
            binding.tvNewWallet1Word.text = it[0]
            binding.tvNewWallet2Word.text = it[1]
            binding.tvNewWallet3Word.text = it[2]
            binding.tvNewWallet4Word.text = it[3]
            binding.tvNewWallet5Word.text = it[4]
            binding.tvNewWallet6Word.text = it[5]
            binding.tvNewWallet7Word.text = it[6]
            binding.tvNewWallet8Word.text = it[7]
            binding.tvNewWallet9Word.text = it[8]
            binding.tvNewWallet10Word.text = it[9]
            binding.tvNewWallet11Word.text = it[10]
            binding.tvNewWallet12Word.text = it[11]
            binding.content.visibility = View.VISIBLE
        }

        binding.btnSaveWallet.setOnClickListener {
            viewModel.confirm()
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "ChillieCrypt"
    }
}
