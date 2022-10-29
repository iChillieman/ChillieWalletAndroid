package com.chillieman.chilliewallet.ui.dex.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentDexListBinding
import com.chillieman.chilliewallet.ui.base.BaseSharedViewModelFragment
import com.chillieman.chilliewallet.ui.dex.DexViewModel


class DexListFragment : BaseSharedViewModelFragment<DexViewModel>(DexViewModel::class.java) {

    private var _binding: FragmentDexListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDexListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    companion object {
        fun newInstance() = DexListFragment()
    }

}