package com.chillieman.chilliewallet.ui.base

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModelActivity<T : BaseViewModel>(
    @JvmSuppressWildcards private val viewModelClass: Class<T>
) : BaseActivity() {

    val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, defaultViewModelProviderFactory)[viewModelClass]
    }
        @UiThread
        get
}
