package com.github.chizoba.starwarz.domain.usecases.movies

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.movies.MoviesRepository
import com.github.chizoba.starwarz.domain.UseCase
import com.github.chizoba.starwarz.domain.models.toMovie
import com.github.chizoba.starwarz.getIdFromUrl
import com.github.chizoba.starwarz.ui.home.movies.Movie
import java.io.IOException
import javax.inject.Inject

class LoadMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Unit, List<Movie>>() {

    var nextPage: Long? = 1

    override fun execute(parameters: Unit): Result<List<Movie>> {
        if (nextPage == null) {
            return Result.Success(emptyList())
        }
        val result = moviesRepository.loadMovies(nextPage!!)
        return if (result is Result.Success) {
            nextPage = if (result.data.next != null) {
                getIdFromUrl(result.data.next)
            } else {
                null
            }
            Result.Success(result.data.results.map { it.toMovie() })
        } else {
            Result.Error(IOException("Error getting movies"))
        }
    }
}