package com.chillieman.chilliewallet.ui.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.ui.base.BaseViewModelFragment

class SettingsFragment : BaseViewModelFragment<SettingsViewModel>(SettingsViewModel::class.java) {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
}