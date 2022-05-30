package com.chillieman.chilliewallet.di

import com.chillieman.chilliewallet.di.annotation.FragmentScoped
import com.chillieman.chilliewallet.ui.main.bot.BotFragment
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
    internal abstract fun bindDexFragment(): BotFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindSettingsFragment(): SettingsFragment
}