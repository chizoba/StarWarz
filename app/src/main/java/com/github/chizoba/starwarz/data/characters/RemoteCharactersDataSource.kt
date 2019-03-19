package com.github.chizoba.starwarz.data.characters

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.StarWarsAPI
import com.github.chizoba.starwarz.domain.models.CharactersSearchResponse
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteCharactersDataSource @Inject constructor(private val apiService: StarWarsAPI) :
    CharactersDataSource {
    override fun searchCharacters(characterName: String, page: Long): Result<CharactersSearchResponse> {
        return try {
            val response = apiService.searchPeople(characterName, page).execute()
            resolveResponse(response, onError = {
                Result.Error(IOException("Error getting characters ${response.code()} ${response.message()}"))
            })
        } catch (e: IOException) {
            return Result.Error(IOException("Error getting characters", e))
        }

    }

    private fun resolveResponse(
        response: Response<CharactersSearchResponse>,
        onError: () -> Result.Error
    ): Result<CharactersSearchResponse> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return onError.invoke()
    }
}