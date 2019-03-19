package com.github.chizoba.starwarz.domain.usecases.characters

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.data.planets.PlanetsRepository
import com.github.chizoba.starwarz.domain.UseCase
import java.io.IOException
import javax.inject.Inject

class GetCharacterPlanetPopulationUseCase @Inject constructor(
    private val planetsRepository: PlanetsRepository
) : UseCase<Long, String>() {

    override fun execute(parameters: Long): Result<String> {
        val result = planetsRepository.getPlanet(parameters)
        return if (result is Result.Success) {
            Result.Success(result.data.population)
        } else {
            Result.Error(IOException("Error getting planet $parameters population"))
        }
    }
}