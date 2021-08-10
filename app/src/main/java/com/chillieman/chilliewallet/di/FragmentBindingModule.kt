package com.chillieman.chilliewallet.di

import com.chillieman.chilliewallet.di.annotation.FragmentScoped
import com.chillieman.chilliewallet.ui.dex.DexFragment
import com.chillieman.chilliewallet.ui.settings.SettingsFragment
import com.chillieman.chilliewallet.ui.wallet.WalletFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindingWalletFragment(): WalletFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindingDexFragment(): DexFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindingSettingsFragment(): SettingsFragment
}