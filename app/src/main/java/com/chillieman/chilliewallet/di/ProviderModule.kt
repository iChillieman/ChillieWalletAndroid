package com.chillieman.chilliewallet.di

import android.content.Context
import dagger.Module
import dagger.Provides
import java.io.File

@Module
class ProviderModule {
    @Provides
    internal fun provideFileDirectory(context: Context): File {
        return context.filesDir.absoluteFile
    }
}