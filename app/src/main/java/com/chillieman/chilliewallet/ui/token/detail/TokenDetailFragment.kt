package com.chillieman.chilliewallet.ui.token.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentTokenDetailBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.token.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TokenDetailFragment :
    BaseSharedViewModelFragment<TokenViewModel>(TokenViewModel::class.java) {
    private var _binding: FragmentTokenDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTokenDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = TokenDetailFragment()
    }
}