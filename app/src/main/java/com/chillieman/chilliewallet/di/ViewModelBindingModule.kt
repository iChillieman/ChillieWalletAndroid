package com.chillieman.chilliewallet.di

import androidx.lifecycle.ViewModel
import com.chillieman.chilliewallet.di.annotation.ViewModelKey
import com.chillieman.chilliewallet.ui.auth.AuthViewModel
import com.chillieman.chilliewallet.ui.main.dex.DexViewModel
import com.chillieman.chilliewallet.ui.main.settings.SettingsViewModel
import com.chillieman.chilliewallet.ui.main.wallet.WalletViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    abstract fun bindWalletViewModel(viewModel: WalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DexViewModel::class)
    abstract fun bindDexViewModel(viewModel: DexViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}