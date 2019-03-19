package com.github.chizoba.starwarz.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.chizoba.starwarz.domain.usecases.characters.SearchCharactersUseCase
import com.github.chizoba.starwarz.domain.usecases.movies.LoadMoviesUseCase
import com.github.chizoba.starwarz.ui.Event
import com.github.chizoba.starwarz.ui.home.characters.Character
import com.github.chizoba.starwarz.ui.home.movies.Movie
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val loadMoviesUseCase: LoadMoviesUseCase,
    private val searchCharactersUseCase: SearchCharactersUseCase
) : ViewModel() {

    var movies = MutableLiveData<List<Movie>>()
    var errorMessage = MutableLiveData<Event<String>>()
    var swipeRefreshing = MutableLiveData<Boolean>()
    var movie = MutableLiveData<Movie>()
    var characters = MutableLiveData<List<Character>>()
    var loading = MutableLiveData<Boolean>()

    init {
        loadMovies()
    }

    fun searchCharacters(characterName: String) {
        searchCharactersUseCase(
            characterName,
            onSuccess = {
                if (it.isEmpty()) {
                    return@searchCharactersUseCase
                }
                characters.postValue(it)
            }, onError = { exception ->
                errorMessage.postValue(Event(exception.message ?: "Error"))
            }, onLoading = {
                loading.postValue(it)
            }
        )
    }

    fun resetPage() {
        searchCharactersUseCase.nextPage = 1
    }

    fun loadMovies() {
        loadMoviesUseCase(
            Unit,
            onSuccess = {
                if (it.isEmpty()) {
                    return@loadMoviesUseCase
                }
                movies.postValue(it)
            }, onError = { exception ->
                errorMessage.postValue(Event(exception.message ?: "Error"))
            }, onLoading = { isLoading ->
                swipeRefreshing.postValue(isLoading)
            })
    }
}