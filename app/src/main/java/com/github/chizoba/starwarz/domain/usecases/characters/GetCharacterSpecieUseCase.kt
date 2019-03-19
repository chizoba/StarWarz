package com.github.chizoba.starwarz.domain.usecases.characters

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.species.SpeciesRepository
import com.github.chizoba.starwarz.domain.UseCase
import com.github.chizoba.starwarz.domain.models.SpecieResponse
import javax.inject.Inject

class GetCharacterSpecieUseCase @Inject constructor(
    private val speciesRepository: SpeciesRepository
) : UseCase<Long, SpecieResponse>() {

    override fun execute(parameters: Long): Result<SpecieResponse> {
        return speciesRepository.getCharacterSpecie(parameters)
    }

}