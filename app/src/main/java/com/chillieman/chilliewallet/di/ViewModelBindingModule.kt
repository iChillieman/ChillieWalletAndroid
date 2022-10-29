package com.chillieman.chilliewallet.di

import androidx.lifecycle.ViewModel
import com.chillieman.chilliewallet.di.annotation.ViewModelKey
import com.chillieman.chilliewallet.ui.auth.AuthViewModel
import com.chillieman.chilliewallet.ui.auth.password.PasswordViewModel
import com.chillieman.chilliewallet.ui.auth.pin.PinViewModel
import com.chillieman.chilliewallet.ui.blockchain.BlockchainViewModel
import com.chillieman.chilliewallet.ui.dex.DexViewModel
import com.chillieman.chilliewallet.ui.main.MainViewModel
import com.chillieman.chilliewallet.ui.main.orders.OrdersViewModel
import com.chillieman.chilliewallet.ui.main.settings.SettingsViewModel
import com.chillieman.chilliewallet.ui.main.wallet.WalletViewModel
import com.chillieman.chilliewallet.ui.newwallet.NewWalletViewModel
import com.chillieman.chilliewallet.ui.order.OrderViewModel
import com.chillieman.chilliewallet.ui.playground.PlaygroundViewModel
import com.chillieman.chilliewallet.ui.strategies.ChillieChainViewModel
import com.chillieman.chilliewallet.ui.token.TokenViewModel
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
    @ViewModelKey(OrdersViewModel::class)
    abstract fun bindOrdersViewModel(viewModel: OrdersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaygroundViewModel::class)
    abstract fun bindPlaygroundViewModel(viewModel: PlaygroundViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PinViewModel::class)
    abstract fun bindPinViewModel(viewModel: PinViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordViewModel::class)
    abstract fun bindPasswordViewModel(viewModel: PasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewWalletViewModel::class)
    abstract fun bindNewWalletViewModel(viewModel: NewWalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    abstract fun bindOrderViewModel(viewModel: OrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TokenViewModel::class)
    abstract fun bindTokenViewModel(viewModel: TokenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChillieChainViewModel::class)
    abstract fun bindChillieChainViewModel(viewModel: ChillieChainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DexViewModel::class)
    abstract fun bindDexViewModel(viewModel: DexViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BlockchainViewModel::class)
    abstract fun bindBlockchainViewModel(viewModel: BlockchainViewModel): ViewModel
}
