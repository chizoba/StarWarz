package com.github.chizoba.starwarz.di.modules

import com.github.chizoba.starwarz.data.StarWarsAPI
import com.github.chizoba.starwarz.data.characters.CharactersDataSource
import com.github.chizoba.starwarz.data.characters.RemoteCharactersDataSource
import com.github.chizoba.starwarz.data.movies.MoviesDataSource
import com.github.chizoba.starwarz.data.movies.RemoteMoviesDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideRemoteMoviesDataSource(apiService: StarWarsAPI): MoviesDataSource {
        return RemoteMoviesDataSource(apiService)
    }

//    @Provides
//    fun provideTestMoviesDataSource(): TestMoviesDataSource {
//        return TestMoviesDataSource()
//    }

    @Singleton
    @Provides
    fun provideRemoteCharactersDataSource(apiService: StarWarsAPI): CharactersDataSource {
        return RemoteCharactersDataSource(apiService)
    }
}
