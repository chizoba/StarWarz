package com.github.chizoba.starwarz.data.species

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.domain.models.SpecieResponse

interface SpeciesDataSource {
    fun getSpecie(id: Long): Result<SpecieResponse>
}