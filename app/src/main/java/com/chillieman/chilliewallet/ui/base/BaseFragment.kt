package com.chillieman.chilliewallet.ui.base

import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chillieman.chilliewallet.ui.main.MainViewModel

abstract class BaseFragment : Fragment() {
    val mainViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        )[MainViewModel::class.java]
    }
        @UiThread
        get
}
