package com.chillieman.chilliewallet.ui.welcome.auth

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentWelcomeAuthPinBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.welcome.WelcomeViewModel
import com.chillieman.chilliewallet.ui.welcome.list.WelcomeAuthPinAdapter
import com.chillieman.chilliewallet.ui.welcome.list.WelcomeAuthPinLengthAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeAuthPinFragment :
    BaseSharedViewModelFragment<WelcomeViewModel>(WelcomeViewModel::class.java),
    WelcomeAuthPinAdapter.PinNumberListener {
    private var _binding: FragmentWelcomeAuthPinBinding? = null
    private val lengthAdapter by lazy { WelcomeAuthPinLengthAdapter() }

    private var pin = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeAuthPinBinding.inflate(inflater, container, false)

        binding.pinNumberList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.pinNumberList.adapter = WelcomeAuthPinAdapter(this)

        binding.pinLengthList.layoutManager =
            LinearLayoutManager(requireContext(), HORIZONTAL, false)
        binding.pinLengthList.adapter = lengthAdapter

        viewModel.errorStringId.observe(viewLifecycleOwner) {
            if (it > 0) {
                viewModel.consumeError()
                binding.errorMessage.text = getString(it)
                binding.errorMessage.visibility = View.VISIBLE
            } else {
                binding.errorMessage.visibility = View.GONE
            }
        }

        viewModel.pinTitle.observe(viewLifecycleOwner) { titleId ->
            pin = ""
            lengthAdapter.pinSize = 0

            ObjectAnimator.ofFloat(
                binding.welcomeWalletImportTitle,
                "rotationX",
                0.0f, 90.0f
            ).apply {
                duration = 250L
                start()
                doOnEnd {
                    binding.welcomeWalletImportTitle.text = getString(titleId)
                    finishSpin()
                }
            }
        }

        viewModel.isAuthCreated.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_welcome_auth_pin_to_wallet_intro)
            }
        }

        return binding.root
    }

    private fun finishSpin() {
        ObjectAnimator.ofFloat(binding.welcomeWalletImportTitle, "rotationX", 90.0f, 360.0f).apply {
            duration = 750L
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPinNumberPressed(number: String) {
        pin += number
        lengthAdapter.pinSize = pin.length
        if (pin.length == PIN_LENGTH) {
            viewModel.processesPin(pin)
        }
    }


    override fun onPinBackPressed() {
        if (pin.isNotEmpty()) {
            pin.removeRange(pin.length - 1, pin.length)
        }
    }

    override fun onPinClearPressed() {
        pin = ""
        lengthAdapter.pinSize = 0
    }

    companion object {
        const val PIN_LENGTH = 6
    }
}
