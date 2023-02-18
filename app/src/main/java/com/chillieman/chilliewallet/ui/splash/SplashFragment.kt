package com.chillieman.chilliewallet.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentSplashBinding
import com.chillieman.chilliewallet.definitions.ChillieWalletDefinitions.SELECTED_WALLET_PREFERENCE
import com.chillieman.chilliewallet.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseViewModelFragment<SplashViewModel>(SplashViewModel::class.java) {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkStatus(getSelectedWalletPreference())
    }

    private fun getSelectedWalletPreference(): Long {
        val pref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return -1L
        return pref.getLong(SELECTED_WALLET_PREFERENCE, -1L)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                SplashStatus.NEEDS_AUTH -> {
                    mainViewModel.isWelcome = true
                    findNavController().navigate(R.id.action_splash_to_welcome_auth)
                }
                SplashStatus.NEEDS_WALLET -> {
                    mainViewModel.isWelcome = true
                    findNavController().navigate(R.id.action_splash_to_welcome_wallet)
                }
                SplashStatus.NEEDS_WALLET_SELECTION -> {
                    mainViewModel.isWelcome = false
                    findNavController().navigate(R.id.action_splash_to_wallet_select)
                }
                SplashStatus.DEFAULT -> {
                    mainViewModel.isWelcome = false
                    findNavController().navigate(R.id.action_splash_to_main)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}