package com.github.chizoba.starwarz.data.movies

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.domain.models.MovieResponse
import com.github.chizoba.starwarz.domain.models.MoviesResponse
import com.github.chizoba.starwarz.getIdFromUrl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val remoteMoviesDataSource: MoviesDataSource
) {

    private val cache = mutableMapOf<Long, MovieResponse>()

    fun loadMovies(page: Long): Result<MoviesResponse> {
        val result = remoteMoviesDataSource.loadMovies(page)
        if (result is Result.Success) {
            cache(result.data.results)
        }
        return result
    }

    private fun cache(data: List<MovieResponse>) {
        data.associateTo(cache) {
            getIdFromUrl(it.url) to it
        }
    }

    fun getMovie(id: Long): Result<MovieResponse> {
        val movie = cache[id]
        return if (movie != null) {
            Result.Success(movie)
        } else {
            Result.Error(IllegalStateException("Movie $id not cached"))
        }
    }

    fun getCharacterMovies(idList: List<Long>): Result<List<MovieResponse>> {
        val characterMoviesMap = cache.filterKeys { idList.contains(it) }
        if (characterMoviesMap.isNotEmpty()) {
            return Result.Success(characterMoviesMap.values.toList())
        }
        return Result.Error(IllegalStateException("Movies $idList not cached"))
    }
}