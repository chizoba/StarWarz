package com.github.chizoba.starwarz.domain.usecases.characters

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.characters.CharactersRepository
import com.github.chizoba.starwarz.domain.UseCase
import com.github.chizoba.starwarz.domain.models.toCharacter
import com.github.chizoba.starwarz.getIdFromUrl
import com.github.chizoba.starwarz.ui.home.characters.Character
import java.io.IOException
import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) : UseCase<String, List<Character>>() {

    var nextPage: Long? = 1

    override fun execute(parameters: String): Result<List<Character>> {
        if (nextPage == null) {
            return Result.Success(emptyList())
        }
        val result = charactersRepository.searchCharacters(parameters, nextPage!!)
        return if (result is Result.Success) {
            nextPage = if (result.data.next != null) {
                getIdFromUrl(result.data.next)
            } else {
                null
            }
            val list = result.data.results.map { it.toCharacter() }
            Result.Success(list)
        } else {
            Result.Error(IOException("Error getting characters"))
        }
    }
}