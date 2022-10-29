package com.chillieman.chilliewallet.ui.newwallet.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentNewWalletImportBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.newwallet.NewWalletViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewWalletImportFragment :
    BaseSharedViewModelFragment<NewWalletViewModel>(NewWalletViewModel::class.java) {
    private var _binding: FragmentNewWalletImportBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewWalletImportBinding.inflate(inflater, container, false)
        val root: View = binding.root



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
