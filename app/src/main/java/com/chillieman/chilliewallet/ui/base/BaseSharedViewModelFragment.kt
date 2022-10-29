package com.chillieman.chilliewallet.ui.base

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModelProvider

abstract class BaseSharedViewModelFragment<T : BaseViewModel>(
    @JvmSuppressWildcards private val viewModelClass: Class<T>
) : BaseFragment() {

    val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(requireActivity(), defaultViewModelProviderFactory)[viewModelClass]
    }
        @UiThread
        get
}
