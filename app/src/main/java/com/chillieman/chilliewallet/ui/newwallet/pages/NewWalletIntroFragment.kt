package com.chillieman.chilliewallet.ui.newwallet.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentNewWalletIntroBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.newwallet.NewWalletViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewWalletIntroFragment :
    BaseSharedViewModelFragment<NewWalletViewModel>(NewWalletViewModel::class.java) {
    private var isFirstWallet: Boolean? = null
    private var _binding: FragmentNewWalletIntroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isFirstWallet = it.getBoolean(ARG_IS_FIRST_WALLET, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewWalletIntroBinding.inflate(inflater, container, false)

        binding.btnNewWalletCreate.setOnClickListener {
            viewModel.setCreateOrImportWalletMode(isCreateNewWallet = true)
        }

        binding.btnNewWalletImport.setOnClickListener {
            viewModel.setCreateOrImportWalletMode(isCreateNewWallet = false)
        }

        if (isFirstWallet == true) {
            binding.tvNewWalletNote.setText(R.string.new_wallet_first_note)
        } else {
            binding.tvNewWalletNote.setText(R.string.new_wallet_note)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_IS_FIRST_WALLET = "if_first_wallet"
        private const val TAG = "ChillieCrypt"

        @JvmStatic
        fun newInstance(isFirstWallet: Boolean) =
            NewWalletIntroFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_FIRST_WALLET, isFirstWallet)
                }
            }
    }
}
