package com.chillieman.chilliewallet.ui.base

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModelProvider

abstract class BaseHybridViewModelFragment<T : BaseViewModel, S : BaseViewModel>(
    @JvmSuppressWildcards private val viewModelClass: Class<T>,
    @JvmSuppressWildcards private val sharedViewModelClass: Class<S>
) : BaseFragment() {
    val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(requireActivity(), defaultViewModelProviderFactory)[viewModelClass]
    }
        @UiThread
        get

    val sharedViewModel: S by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(requireActivity(), defaultViewModelProviderFactory)[sharedViewModelClass]
    }
        @UiThread
        get
}
