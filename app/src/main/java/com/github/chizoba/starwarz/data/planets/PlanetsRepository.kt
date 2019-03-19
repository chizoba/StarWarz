package com.github.chizoba.starwarz.data.planets

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.domain.models.PlanetResponse
import com.github.chizoba.starwarz.getIdFromUrl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanetsRepository @Inject constructor(private val remotePlanetsDataSource: RemotePlanetsDataSource) {

    private val cache = mutableMapOf<Long, PlanetResponse>()

    fun getPlanet(id: Long): Result<PlanetResponse> {
        val planet = cache[id]
        if (planet != null) {
            return Result.Success(planet)
        }
        val result = remotePlanetsDataSource.getPlanet(id)
        if (result is Result.Success) {
            cache(result.data)
        }
        return result
    }

    private fun cache(data: PlanetResponse) {
        cache[getIdFromUrl(data.url)] = data
    }
}