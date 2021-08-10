package com.chillieman.chilliewallet.ui.base

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

abstract class BaseSharedViewModelFragment<T : BaseViewModel>(
    @JvmSuppressWildcards private val viewModelClass: Class<T>
) : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
//        ViewModelProvider(requireActivity())
        ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(viewModelClass)
    }
        @UiThread
        get
}
