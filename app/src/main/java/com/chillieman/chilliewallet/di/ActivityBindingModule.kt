package com.chillieman.chilliewallet.di

import com.chillieman.chilliewallet.ui.MainActivity
import com.chillieman.chilliewallet.di.annotation.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}