package com.chillieman.chilliewallet.ui.main.wallet

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
import java.math.BigDecimal
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

        sharedViewModel.selectedWallet.observe(viewLifecycleOwner) {
            binding.content.visibility = View.VISIBLE

            //TODO: Load Wallet Specific Stuff!
            sharedViewModel.getBalance()
            sharedViewModel.getAddress()
            viewModel.loadTokens()
        }


        sharedViewModel.balance.observe(viewLifecycleOwner) {
            val rounded = it.setScale(4, BigDecimal.ROUND_DOWN)
            val string = "$rounded BNB"
            binding.tvWalletBalance.text = string
        }

        sharedViewModel.address.observe(viewLifecycleOwner) { address ->
            binding.tvWalletAddress.text = address
            binding.card.setOnClickListener { it as TextView
                val clipboardService =
                    requireContext().getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("Chillieman says Hi", it.text.toString())
                clipboardService.setPrimaryClip(clipData)

                Toast.makeText(
                    requireContext(),
                    "Address Copied to Clipboard",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }



    companion object {
        private const val TAG = "ChillieWalletFragment"
    }
}