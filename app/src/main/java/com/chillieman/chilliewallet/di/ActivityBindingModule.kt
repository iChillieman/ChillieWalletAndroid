package com.chillieman.chilliewallet.di

import com.chillieman.chilliewallet.ui.main.MainActivity
import com.chillieman.chilliewallet.di.annotation.ActivityScoped
import com.chillieman.chilliewallet.ui.auth.AuthActivity
import com.chillieman.chilliewallet.ui.barcode.BarcodeActivity
import com.chillieman.chilliewallet.ui.blockchain.BlockchainActivity
import com.chillieman.chilliewallet.ui.dex.DexActivity
import com.chillieman.chilliewallet.ui.newwallet.NewWalletActivity
import com.chillieman.chilliewallet.ui.order.OrderActivity
import com.chillieman.chilliewallet.ui.playground.PlaygroundActivity
import com.chillieman.chilliewallet.ui.strategies.ChillieChainActivity
import com.chillieman.chilliewallet.ui.token.TokenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun barcodeActivity(): BarcodeActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun authActivity(): AuthActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun playgroundActivity(): PlaygroundActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun newWalletActivity(): NewWalletActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun orderActivity(): OrderActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun tokenActivity(): TokenActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun chillieChainActivity(): ChillieChainActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun dexActivity(): DexActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun blockChainActivity(): BlockchainActivity
}
