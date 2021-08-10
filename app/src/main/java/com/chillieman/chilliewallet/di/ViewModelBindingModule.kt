package com.chillieman.chilliewallet.di

import androidx.lifecycle.ViewModel
import com.chillieman.chilliewallet.di.annotation.ViewModelKey
import com.chillieman.chilliewallet.ui.dex.DexViewModel
import com.chillieman.chilliewallet.ui.settings.SettingsViewModel
import com.chillieman.chilliewallet.ui.wallet.WalletViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    abstract fun bindNewUserViewModel(viewModel: WalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DexViewModel::class)
    abstract fun bindUserListViewModel(viewModel: DexViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindUserInfoViewModel(viewModel: SettingsViewModel): ViewModel
}