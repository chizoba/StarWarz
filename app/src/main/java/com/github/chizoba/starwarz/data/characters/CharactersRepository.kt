package com.github.chizoba.starwarz.data.characters

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.domain.models.CharacterResponse
import com.github.chizoba.starwarz.domain.models.CharactersSearchResponse
import com.github.chizoba.starwarz.getIdFromUrl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject constructor(private val remoteCharactersDataSource: CharactersDataSource) {

    private val cache = mutableMapOf<Long, CharacterResponse>()

    fun searchCharacters(characterName: String, page: Long): Result<CharactersSearchResponse> {
        val result = remoteCharactersDataSource.searchCharacters(characterName, page)
        if (result is Result.Success) {
            cache(result.data.results)
        }
        return result
    }

    private fun cache(data: List<CharacterResponse>) {
        data.associateTo(cache) { getIdFromUrl(it.url) to it }
    }

    fun getCharacter(id: Long): Result<CharacterResponse> {
        val character = cache[id]
        return if (character != null) {
            Result.Success(character)
        } else {
            Result.Error(IllegalStateException("Character $id not cached"))
        }
    }
}