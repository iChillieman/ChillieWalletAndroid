package com.chillieman.chilliewallet.di

import com.chillieman.chilliewallet.di.annotation.FragmentScoped
import com.chillieman.chilliewallet.ui.auth.password.PasswordFragment
import com.chillieman.chilliewallet.ui.auth.pin.PinFragment
import com.chillieman.chilliewallet.ui.blockchain.create.BlockchainCreateFragment
import com.chillieman.chilliewallet.ui.blockchain.list.BlockchainListFragment
import com.chillieman.chilliewallet.ui.dex.create.DexCreateFragment
import com.chillieman.chilliewallet.ui.dex.detail.DexDetailFragment
import com.chillieman.chilliewallet.ui.dex.list.DexListFragment
import com.chillieman.chilliewallet.ui.main.orders.OrdersFragment
import com.chillieman.chilliewallet.ui.main.settings.SettingsFragment
import com.chillieman.chilliewallet.ui.main.wallet.WalletFragment
import com.chillieman.chilliewallet.ui.newwallet.pages.NewWalletCreateFragment
import com.chillieman.chilliewallet.ui.newwallet.pages.NewWalletImportFragment
import com.chillieman.chilliewallet.ui.newwallet.pages.NewWalletIntroFragment
import com.chillieman.chilliewallet.ui.order.create.OrderCreateFragment
import com.chillieman.chilliewallet.ui.order.detail.OrderDetailFragment
import com.chillieman.chilliewallet.ui.strategies.create.ChillieChainCreateFragment
import com.chillieman.chilliewallet.ui.strategies.detail.ChillieChainDetailFragment
import com.chillieman.chilliewallet.ui.strategies.list.ChillieChainListFragment
import com.chillieman.chilliewallet.ui.token.create.TokenCreateFragment
import com.chillieman.chilliewallet.ui.token.detail.TokenDetailFragment
import com.chillieman.chilliewallet.ui.token.list.TokenListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindWalletFragment(): WalletFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindOrdersFragment(): OrdersFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindSettingsFragment(): SettingsFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindPinFragment(): PinFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindPasswordFragment(): PasswordFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindNewWalletCreateFragment(): NewWalletCreateFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindNewWalletImportFragment(): NewWalletImportFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindNewWalletIntroFragment(): NewWalletIntroFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindOrderCreateFragment(): OrderCreateFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindOrderDetailFragment(): OrderDetailFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindChillieChainListFragment(): ChillieChainListFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindChillieChainCreateFragment(): ChillieChainCreateFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindChillieChainDetailFragment(): ChillieChainDetailFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindTokenListFragment(): TokenListFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindTokenDetailFragment(): TokenDetailFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindTokenCreateFragment(): TokenCreateFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindDexListFragment(): DexListFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindCreateFragment(): DexCreateFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindDexDetailFragment(): DexDetailFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindBlockchainListFragment(): BlockchainListFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindBlockchainCreateFragment(): BlockchainCreateFragment
}
