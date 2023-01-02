package com.chillieman.chilliewallet.ui.welcome.text

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.animation.doOnEnd
import androidx.navigation.fragment.findNavController
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentTextSlideBinding
import com.chillieman.chilliewallet.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WelcomeIntroWalletFragment : BaseFragment() {
    private var _binding: FragmentTextSlideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextSlideBinding.inflate(inflater, container, false)

        binding.mainText.text = mainText
        binding.secondaryText.text = otherTextFirst

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                ObjectAnimator.ofFloat(binding.secondaryText, "Y", binding.mainText.y).apply {
                    duration = 2000L
                    start()
                    doOnEnd { fadeInSecondaryText() }
                }
            }
        })

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fadeInSecondaryText() {
        binding.secondaryText.visibility = View.VISIBLE
        val startingY = binding.mainText.y
        val targetY = startingY + binding.mainText.height

        ObjectAnimator.ofFloat(binding.secondaryText, "Y", startingY, targetY).apply {
            duration = animDuration
            start()
            doOnEnd { firstWait() }
        }

        ObjectAnimator.ofFloat(binding.secondaryText, "alpha", 0.0f, 1.0f).apply {
            duration = animDuration
            start()
        }

        ObjectAnimator.ofFloat(binding.mainText, "alpha", 0.0f, 1.0f).apply {
            duration = animDuration * 5
            start()
        }
    }

    private fun firstWait() {
        ObjectAnimator.ofFloat(binding.secondaryText, "Y", binding.secondaryText.y).apply {
            duration = 1000L
            start()
            doOnEnd { spinSecondaryTextToInvisible() }
        }
    }

    private fun spinSecondaryTextToInvisible() {
        ObjectAnimator.ofFloat(binding.secondaryText, "rotationX", 0.0f, 90.0f).apply {
            duration = 500L
            start()
            doOnEnd {
                binding.secondaryText.y = binding.guideTop.y
                binding.secondaryText.text = otherTextSecond
                spinSecondaryTextToVisible()
            }
        }
    }

    private fun spinSecondaryTextToVisible() {
        ObjectAnimator.ofFloat(binding.secondaryText, "rotationX", 90f, 0.0f).apply {
            duration = 500L
            start()
            doOnEnd {
                findNavController().navigate(R.id.action_welcome_wallet_intro_to_menu)
            }
        }
    }


    companion object {
        private const val animDuration = 1000L
        private const val mainText = "Great!"
        private const val otherTextFirst = "Time to set up your wallet."
        private const val otherTextSecond = "New Wallet"
    }

}