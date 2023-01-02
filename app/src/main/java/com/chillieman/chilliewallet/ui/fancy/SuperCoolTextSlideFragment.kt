package com.chillieman.chilliewallet.ui.fancy

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.animation.doOnEnd
import com.chillieman.chilliewallet.databinding.FragmentTextSlideBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuperCoolTextSlideFragment :
    BaseSharedViewModelFragment<SuperCoolTextSlideViewModel>(SuperCoolTextSlideViewModel::class.java) {

    private var _binding: FragmentTextSlideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextSlideBinding.inflate(inflater, container, false)

        viewModel.mainText.observe(viewLifecycleOwner) {
            binding.mainText.text = it
        }

        viewModel.secondaryText.observe(viewLifecycleOwner) {
            binding.secondaryText.text = it
        }


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
        // If secondary Text is null, Then just slide Primary to top.
        val secondaryText = viewModel.secondaryText.value
        if (secondaryText.isNullOrEmpty()) {
            //Do the next action
            sayDone()
            return
        }

        binding.secondaryText.visibility = View.VISIBLE
        val startingY = binding.mainText.y
        val targetY = startingY + binding.mainText.height
        val animDuration = 1000L
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
                viewModel.setSecondaryText("We need a password.")
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
            duration = 2000L
            start()
            doOnEnd { startMovingSecondaryToTop() }
        }
    }

    private fun startMovingSecondaryToTop() {
        val currentY = binding.secondaryText.y
        val targetY = binding.guideTop.y
        val animDuration = 1000L
        ObjectAnimator.ofFloat(binding.secondaryText, "Y", currentY, targetY).apply {
            duration = animDuration * 2
            start()
        }

        ObjectAnimator.ofFloat(binding.secondaryText, "rotationX", 0.0f, -90f).apply {
            duration = animDuration
            start()
            doOnEnd {
                viewModel.setSecondaryText("New Password")
                showFinalSecondaryText()
            }
        }

        ObjectAnimator.ofFloat(binding.mainText, "alpha", 1.0f, 0.0f).apply {
            duration = animDuration
            start()
            doOnEnd { binding.mainText.visibility = View.GONE }

        }
    }

    private fun showFinalSecondaryText() {
        val animDuration = 1000L

        ObjectAnimator.ofFloat(binding.secondaryText, "rotationX", -90f, 0f).apply {
            duration = animDuration
            start()
            doOnEnd {
                sayDone()
            }
        }
    }

    private fun sayDone() {
        Log.d("Chilliemanz", "DONE")
        viewModel.setIsFinished(true)
    }
}