package com.chillieman.chilliewallet.ui.main.wallet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentWalletBinding
import com.chillieman.chilliewallet.ui.barcode.BarcodeActivity
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.main.MainViewModel

class WalletFragment : BaseSharedViewModelFragment<MainViewModel>(MainViewModel::class.java) {

    private var _binding: FragmentWalletBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.text.observe(viewLifecycleOwner) {
            binding.btnLaunchBarcodeActivity.text = it
        }

        viewModel.changeText("Launch QR Playground!")

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLaunchBarcodeActivity.setOnClickListener {
            startActivity(Intent(requireActivity(), BarcodeActivity::class.java))
        }
    }
}