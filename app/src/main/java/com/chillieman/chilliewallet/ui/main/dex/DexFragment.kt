package com.chillieman.chilliewallet.ui.main.dex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.ui.base.BaseViewModelFragment

class DexFragment : BaseViewModelFragment<DexViewModel>(DexViewModel::class.java) {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dex, container, false)
        val textView: TextView = root.findViewById(R.id.text_dex)
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
}