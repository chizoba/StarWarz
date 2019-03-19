package com.github.chizoba.starwarz.data.species

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.StarWarsAPI
import com.github.chizoba.starwarz.domain.models.SpecieResponse
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSpeciesDataSource @Inject constructor(
    private val apiService: StarWarsAPI
) : SpeciesDataSource {

    override fun getSpecie(id: Long): Result<SpecieResponse> {
        return try {
            val response = apiService.getSpecie(id).execute()
            resolveResponse(response, onError = {
                Result.Error(IOException("Error getting species ${response.code()} ${response.message()}"))
            })
        } catch (e: IOException) {
            return Result.Error(IOException("Error getting species", e))
        }
    }

    private fun resolveResponse(
        response: Response<SpecieResponse>,
        onError: () -> Result.Error
    ): Result<SpecieResponse> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return onError.invoke()
    }
}