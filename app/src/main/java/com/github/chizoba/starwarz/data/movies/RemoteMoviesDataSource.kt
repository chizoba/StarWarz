package com.github.chizoba.starwarz.data.movies

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.StarWarsAPI
import com.github.chizoba.starwarz.domain.models.MoviesResponse
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteMoviesDataSource @Inject constructor(
    private val apiService: StarWarsAPI
) : MoviesDataSource {
    override fun loadMovies(page: Long): Result<MoviesResponse> {
        return try {
            val response = apiService.getMovies(page).execute()
            resolveResponse(response = response, onError = {
                Result.Error(IOException("Error getting movies ${response.code()} ${response.message()}"))
            })
        } catch (e: IOException) {
            Result.Error(IOException("Error getting movies", e))
        }

    }

    private fun resolveResponse(
        response: Response<MoviesResponse>,
        onError: () -> Result.Error
    ): Result<MoviesResponse> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return onError.invoke()
    }

}