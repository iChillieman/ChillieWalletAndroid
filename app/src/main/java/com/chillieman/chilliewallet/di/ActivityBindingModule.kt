package com.chillieman.chilliewallet.di

import com.chillieman.chilliewallet.ui.main.MainActivity
import com.chillieman.chilliewallet.di.annotation.ActivityScoped
import com.chillieman.chilliewallet.ui.auth.AuthActivity
import com.chillieman.chilliewallet.ui.barcode.BarcodeActivity
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
}