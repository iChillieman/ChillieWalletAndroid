package com.chillieman.chilliewallet.ui.welcome.auth

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.animation.doOnEnd
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentWelcomeAuthPasswordBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.welcome.WelcomeViewModel
import com.chillieman.chilliewallet.ui.welcome.list.WelcomeAuthPasswordAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeAuthPasswordFragment :
    BaseSharedViewModelFragment<WelcomeViewModel>(WelcomeViewModel::class.java) {
    private var _binding: FragmentWelcomeAuthPasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeAuthPasswordBinding.inflate(inflater, container, false)

        binding.welcomePasswordErrorList.layoutManager = LinearLayoutManager(requireContext())
        viewModel.passwordErrorIdList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.welcomePasswordErrorList.alpha = 0f
                binding.welcomePasswordErrorList.visibility = View.VISIBLE
                ObjectAnimator.ofFloat(binding.welcomePasswordErrorList, "alpha", 0f, 1f).apply {
                    duration = 1000L
                    start()
                }
                binding.welcomePasswordErrorList.adapter = WelcomeAuthPasswordAdapter(it)
            } else {
                ObjectAnimator.ofFloat(binding.welcomePasswordErrorList, "alpha", 1f, 0f).apply {
                    duration = 1000L
                    start()
                    doOnEnd { binding.welcomePasswordErrorList.visibility = View.GONE }
                }

            }
        }

        binding.etWelcomeCreatePasswordFirst.doOnTextChanged { text, _, _, _ ->
            viewModel.updatePasswordErrorList(
                text.toString(),
                binding.etWelcomeCreatePasswordSecond.text.toString()
            )
        }

        binding.etWelcomeCreatePasswordSecond.doOnTextChanged { text, _, _, _ ->
            viewModel.updatePasswordErrorList(
                binding.etWelcomeCreatePasswordFirst.text.toString(),
                text.toString()
            )
        }

        binding.btnCreatePassword.setOnClickListener {
            val firstInput = binding.etWelcomeCreatePasswordFirst.text.toString()
            val secondInput = binding.etWelcomeCreatePasswordSecond.text.toString()
            viewModel.processPassword(firstInput, secondInput)
        }

        viewModel.isPasswordReady.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_welcome_auth_password_to_pin)
        }

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                ObjectAnimator.ofFloat(
                    binding.etWelcomeCreatePasswordFirst,
                    "translationY",
                    500f,
                    0f
                ).apply {
                    duration = 700L
                    start()
                }

                ObjectAnimator.ofFloat(
                    binding.etWelcomeCreatePasswordSecond,
                    "translationY",
                    500f,
                    0f
                ).apply {
                    duration = 900L
                    start()
                }

                ObjectAnimator.ofFloat(binding.btnCreatePassword, "translationY", 500f, 0f).apply {
                    duration = 1000L
                    start()
                }

            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
