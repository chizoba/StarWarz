package com.github.chizoba.starwarz.data.planets

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.StarWarsAPI
import com.github.chizoba.starwarz.domain.models.PlanetResponse
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemotePlanetsDataSource @Inject constructor(
    private val apiService: StarWarsAPI
) : PlanetsDataSource {
    override fun getPlanet(id: Long): Result<PlanetResponse> {
        return try {
            val response = apiService.getPlanet(id).execute()
            resolveResponse(response, onError = {
                Result.Error(IOException("Error getting planet ${response.code()} ${response.message()}"))
            })
        } catch (e: IOException) {
            return Result.Error(IOException("Error getting planet", e))
        }
    }

    private fun resolveResponse(
        response: Response<PlanetResponse>,
        onError: () -> Result.Error
    ): Result<PlanetResponse> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return onError.invoke()
    }
}