package com.github.chizoba.starwarz.domain.usecases.characters

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.movies.MoviesRepository
import com.github.chizoba.starwarz.domain.UseCase
import com.github.chizoba.starwarz.domain.models.toMovie
import com.github.chizoba.starwarz.ui.home.movies.Movie
import java.io.IOException
import javax.inject.Inject

class GetCharacterMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<List<Long>, List<Movie>>() {

    override fun execute(parameters: List<Long>): Result<List<Movie>> {
        val result = moviesRepository.getCharacterMovies(parameters)
        return if (result is Result.Success) {
            Result.Success(result.data.map { it.toMovie() })
        } else {
            Result.Error(IOException("Error getting character $parameters movies"))
        }
    }
}