package com.github.chizoba.starwarz.data.movies

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.domain.models.MoviesResponse

interface MoviesDataSource {
    fun loadMovies(page: Long): Result<MoviesResponse>
}