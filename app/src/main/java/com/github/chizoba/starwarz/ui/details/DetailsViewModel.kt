package com.github.chizoba.starwarz.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.chizoba.starwarz.domain.models.SpecieResponse
import com.github.chizoba.starwarz.domain.usecases.characters.GetCharacterMoviesUseCase
import com.github.chizoba.starwarz.domain.usecases.characters.GetCharacterPlanetPopulationUseCase
import com.github.chizoba.starwarz.domain.usecases.characters.GetCharacterSpecieUseCase
import com.github.chizoba.starwarz.domain.usecases.characters.GetCharacterUseCase
import com.github.chizoba.starwarz.domain.usecases.movies.GetMovieUseCase
import com.github.chizoba.starwarz.getIdFromUrl
import com.github.chizoba.starwarz.ui.Destination
import com.github.chizoba.starwarz.ui.Event
import com.github.chizoba.starwarz.ui.home.characters.Character
import com.github.chizoba.starwarz.ui.home.movies.Movie
import javax.inject.Inject


class DetailsViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getCharacterSpecieUseCase: GetCharacterSpecieUseCase,
    private val getCharacterPlanetPopulationUseCase: GetCharacterPlanetPopulationUseCase,
    private val getCharacterMoviesUseCase: GetCharacterMoviesUseCase
) : ViewModel() {

    var idExtra: Long = -1
    var character = MutableLiveData<Character>()
    var loading = MutableLiveData<Event<Boolean>>()
    var loadingSpecie = MutableLiveData<Event<Boolean>>()
    var loadingPlanet = MutableLiveData<Event<Boolean>>()
    var loadingMovies = MutableLiveData<Event<Boolean>>()
    var movie = MutableLiveData<Movie>()
    var specie = MutableLiveData<SpecieResponse>()
    var population = MutableLiveData<String>()
    var movies = MutableLiveData<List<Movie>>()
    var errorMessage = MutableLiveData<Event<String>>()
    var navigation = MutableLiveData<Event<Destination>>()

    fun navigate(destination: Destination) {
        navigation.postValue(Event(destination))
    }

    fun getMovie(id: Long = idExtra) {
        getMovieUseCase(
            id,
            onSuccess = {
                movie.postValue(it)
            },
            onError = {
                errorMessage.postValue(Event(content = it.message ?: "Error"))
            },
            onLoading = {
                loading.postValue(Event(it))
            }
        )
    }

    fun getCharacter(id: Long = idExtra) {
        getCharacterUseCase(
            id,
            onSuccess = {
                character.postValue(it)
                getCharacterSpecie(it)
                getCharacterPlanetPopulation(it)
                getCharacterMovies(it)
            },
            onError = {
                errorMessage.postValue(Event(content = it.message ?: "Error"))
            },
            onLoading = {
                loading.postValue(Event(it))
            }
        )
    }

    private fun getCharacterSpecie(character: Character) {
        if (character.species.isEmpty()) {
            return
        }
        getCharacterSpecieUseCase(
            getIdFromUrl(character.species[0]),
            onSuccess = { specieResponse ->
                specie.postValue(specieResponse)
            },
            onError = {
                errorMessage.postValue(Event(content = it.message ?: "Error"))
            },
            onLoading = {
                loadingSpecie.postValue(Event(it))
            }
        )
    }

    private fun getCharacterPlanetPopulation(character: Character) {
        getCharacterPlanetPopulationUseCase(
            getIdFromUrl(character.planet),
            onSuccess = {
                population.postValue(it)
            },
            onError = {
                errorMessage.postValue(Event(content = it.message ?: "Error"))
            },
            onLoading = {
                loadingPlanet.postValue(Event(it))
            }
        )
    }

    private fun getCharacterMovies(character: Character) {
        getCharacterMoviesUseCase(
            character.films.map { getIdFromUrl(it) },
            onSuccess = {
                movies.postValue(it)
            },
            onError = {
                errorMessage.postValue(Event(content = it.message ?: "Error"))
            },
            onLoading = {
                loadingMovies.postValue(Event(it))
            }
        )
    }
}