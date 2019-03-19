package com.github.chizoba.starwarz.data.characters

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.domain.models.CharactersSearchResponse

interface CharactersDataSource {
    fun searchCharacters(characterName: String, page: Long): Result<CharactersSearchResponse>
}