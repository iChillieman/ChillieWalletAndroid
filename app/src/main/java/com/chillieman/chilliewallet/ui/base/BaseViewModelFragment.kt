package com.chillieman.chilliewallet.ui.base

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

abstract class BaseViewModelFragment<T : BaseViewModel>(
    @JvmSuppressWildcards private val viewModelClass: Class<T>
) : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory)
            .get(viewModelClass)
    }
        @UiThread
        get
}
