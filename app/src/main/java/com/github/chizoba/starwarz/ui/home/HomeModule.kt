package com.github.chizoba.starwarz.ui.home

import androidx.lifecycle.ViewModel
import com.github.chizoba.starwarz.di.scopes.FragmentScoped
import com.github.chizoba.starwarz.di.scopes.ViewModelKey
import com.github.chizoba.starwarz.ui.home.characters.CharactersFragment
import com.github.chizoba.starwarz.ui.home.movies.FilmsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributesFilmsFragment(): FilmsFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributesPeopleFragment(): CharactersFragment
}