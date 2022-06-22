package com.chillieman.chilliewallet.ui.main.wallet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.security.crypto.MasterKey
import com.chillieman.chilliewallet.databinding.FragmentWalletBinding
import com.chillieman.chilliewallet.db.entity.AuthDatum
import com.chillieman.chilliewallet.manager.EncryptionManager
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.ui.barcode.BarcodeActivity
import com.chillieman.chilliewallet.ui.base.BaseHybridViewModelFragment
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.base.BaseViewModelFragment
import com.chillieman.chilliewallet.ui.main.MainViewModel
import com.chillieman.chilliewallet.ui.playground.PlaygroundActivity
import java.security.KeyStore
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class WalletFragment : BaseHybridViewModelFragment<WalletViewModel, MainViewModel>(
    WalletViewModel::class.java,
    MainViewModel::class.java
) {

    @Inject
    lateinit var authRepository: AuthRepository

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

        viewModel.isCreating.observe(viewLifecycleOwner) {
            binding.btnLaunchWalletPlayground.isEnabled = !it
            binding.btnLaunchBarcodeActivity.isEnabled = !it
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLaunchBarcodeActivity.text = "Barcode Playground"
        binding.btnLaunchBarcodeActivity.setOnClickListener {
            startActivity(Intent(requireActivity(), BarcodeActivity::class.java))
//            viewModel.checkPin()
        }

        binding.btnLaunchWalletPlayground.text = "Wallet Playground"
        binding.btnLaunchWalletPlayground.setOnClickListener {
            startActivity(Intent(requireActivity(), PlaygroundActivity::class.java))
//           viewModel.checkPassword()
        }

    }


    companion object {
        private const val TAG = "ChillieCrypt"
    }
}