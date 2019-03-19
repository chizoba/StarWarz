package com.github.chizoba.starwarz.di.modules

import com.github.chizoba.starwarz.di.scopes.ActivityScoped
import com.github.chizoba.starwarz.ui.details.DetailsActivity
import com.github.chizoba.starwarz.ui.details.DetailsModule
import com.github.chizoba.starwarz.ui.home.HomeActivity
import com.github.chizoba.starwarz.ui.home.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun contributeHomeActivityInjector(): HomeActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [DetailsModule::class])
    internal abstract fun contributeDetailsActivityInjector(): DetailsActivity
}