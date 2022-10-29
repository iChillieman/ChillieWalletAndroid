package com.chillieman.chilliewallet.ui.token.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentTokenCreateBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.token.TokenViewModel

class TokenCreateFragment : BaseSharedViewModelFragment<TokenViewModel>(TokenViewModel::class.java) {
    private var _binding: FragmentTokenCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTokenCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    companion object {
        fun newInstance() = TokenCreateFragment()
    }
}