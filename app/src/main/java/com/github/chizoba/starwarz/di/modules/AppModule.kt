package com.github.chizoba.starwarz.di.modules

import android.content.Context
import com.github.chizoba.starwarz.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }
}