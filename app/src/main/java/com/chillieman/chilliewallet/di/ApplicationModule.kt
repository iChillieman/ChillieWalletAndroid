package com.chillieman.chilliewallet.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.chillieman.chilliewallet.ChillieApplication
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(chillieApplication: ChillieApplication): Context

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}