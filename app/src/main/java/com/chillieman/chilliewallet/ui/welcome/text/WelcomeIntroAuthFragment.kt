package com.chillieman.chilliewallet.ui.welcome.text

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.animation.doOnEnd
import androidx.navigation.fragment.findNavController
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentTextSlideBinding
import com.chillieman.chilliewallet.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WelcomeIntroAuthFragment : BaseFragment() {
    private var _binding: FragmentTextSlideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextSlideBinding.inflate(inflater, container, false)

        binding.mainText.text = mainTextFirst
        binding.secondaryText.text = otherMessageFirst

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
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
    }

    private fun firstWait() {
        ObjectAnimator.ofFloat(binding.mainText, "alpha", 1.0f, 0.0f).apply {
            duration = 5000
            start()
            doOnEnd { binding.mainText.visibility = View.GONE }
        }

        ObjectAnimator.ofFloat(binding.secondaryText, "Y", binding.secondaryText.y).apply {
            duration = 2000L
            start()
            doOnEnd { spinSecondaryTextToInvisible() }
        }
    }

    private fun spinSecondaryTextToInvisible() {
        ObjectAnimator.ofFloat(binding.secondaryText, "rotationX", 0.0f, 90.0f).apply {
            duration = 500L
            start()
            doOnEnd {
                binding.secondaryText.text = otherMessageSecond
                spinSecondaryTextToVisible()
            }
        }
    }

    private fun spinSecondaryTextToVisible() {
        ObjectAnimator.ofFloat(binding.secondaryText, "rotationX", 90f, 360.0f).apply {
            duration = 500L
            start()
            doOnEnd { secondWait() }
        }
    }

    private fun secondWait() {
        ObjectAnimator.ofFloat(binding.secondaryText, "Y", binding.secondaryText.y).apply {
            duration = 500L
            start()
            doOnEnd { startMovingSecondaryToTop() }
        }
    }

    private fun startMovingSecondaryToTop() {
        val currentY = binding.secondaryText.y
        val targetY = binding.guideTop.y
        ObjectAnimator.ofFloat(binding.secondaryText, "Y", currentY, targetY).apply {
            duration = animDuration * 2
            start()
        }

        ObjectAnimator.ofFloat(binding.secondaryText, "alpha", 1.0f, -0f).apply {
            duration = animDuration
            start()
            doOnEnd {
                binding.secondaryText.text = otherMessageFinal
                showFinalSecondaryText()
            }
        }
    }

    private fun showFinalSecondaryText() {
        ObjectAnimator.ofFloat(binding.secondaryText, "alpha", 0f, 1.0f).apply {
            duration = animDuration
            start()
            doOnEnd {
                findNavController().navigate(R.id.action_welcome_auth_to_password)
            }
        }
    }

    companion object {
        private const val mainTextFirst = "Welcome!"
        private const val otherMessageFirst = "Let's get you set up."
        private const val otherMessageSecond = "We need a password."
        private const val otherMessageFinal = "New Password"
        private const val animDuration = 1000L
    }
}