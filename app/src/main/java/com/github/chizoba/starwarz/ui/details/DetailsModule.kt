package com.github.chizoba.starwarz.ui.details

import androidx.lifecycle.ViewModel
import com.github.chizoba.starwarz.di.scopes.FragmentScoped
import com.github.chizoba.starwarz.di.scopes.ViewModelKey
import com.github.chizoba.starwarz.ui.details.characters.CharacterDetailsFragment
import com.github.chizoba.starwarz.ui.details.movies.MovieDetailsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: DetailsViewModel): ViewModel

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributesMovieDetailsFragment(): MovieDetailsFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributesCharacterDetailsFragment(): CharacterDetailsFragment
}