package com.github.chizoba.starwarz.domain.usecases.movies

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.movies.MoviesRepository
import com.github.chizoba.starwarz.domain.UseCase
import com.github.chizoba.starwarz.domain.models.toMovie
import com.github.chizoba.starwarz.ui.home.movies.Movie
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Long, Movie>() {

    override fun execute(parameters: Long): Result<Movie> {
        val movie = moviesRepository.getMovie(parameters)
        return if (movie is Result.Success) {
            Result.Success(movie.data.toMovie())
        } else {
            Result.Error(IllegalStateException("Movie $parameters not cached"))
        }
    }
}