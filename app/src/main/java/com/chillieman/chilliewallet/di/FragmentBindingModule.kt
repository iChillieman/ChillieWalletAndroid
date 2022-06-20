package com.chillieman.chilliewallet.di

import com.chillieman.chilliewallet.di.annotation.FragmentScoped
import com.chillieman.chilliewallet.ui.auth.password.PasswordFragment
import com.chillieman.chilliewallet.ui.auth.pin.PinFragment
import com.chillieman.chilliewallet.ui.main.orders.OrdersFragment
import com.chillieman.chilliewallet.ui.main.settings.SettingsFragment
import com.chillieman.chilliewallet.ui.main.wallet.WalletFragment
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
}