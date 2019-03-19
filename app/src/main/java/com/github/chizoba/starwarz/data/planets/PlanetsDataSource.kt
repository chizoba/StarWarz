package com.github.chizoba.starwarz.data.planets

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.domain.models.PlanetResponse

interface PlanetsDataSource {
    fun getPlanet(id: Long): Result<PlanetResponse>
}