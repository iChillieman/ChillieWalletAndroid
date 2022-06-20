package com.chillieman.chilliewallet.di

import com.chillieman.chilliewallet.di.annotation.ServiceScoped
import com.chillieman.chilliewallet.service.AuthService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBindingModule {
    @ServiceScoped
    @ContributesAndroidInjector
    internal abstract fun authService(): AuthService
}