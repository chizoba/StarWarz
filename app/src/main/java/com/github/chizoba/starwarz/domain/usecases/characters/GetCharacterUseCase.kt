package com.github.chizoba.starwarz.domain.usecases.characters

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.characters.CharactersRepository
import com.github.chizoba.starwarz.domain.UseCase
import com.github.chizoba.starwarz.domain.models.toCharacter
import com.github.chizoba.starwarz.ui.home.characters.Character
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) : UseCase<Long, Character>() {
    override fun execute(parameters: Long): Result<Character> {
        val result = charactersRepository.getCharacter(parameters)
        return if (result is Result.Success) {
            Result.Success(result.data.toCharacter())
        } else {
            Result.Error(IllegalStateException("Character $parameters not cached"))
        }
    }
}