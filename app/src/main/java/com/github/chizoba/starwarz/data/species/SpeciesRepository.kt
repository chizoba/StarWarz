package com.github.chizoba.starwarz.data.species

import com.github.chizoba.starwarz.data.Result
import com.github.chizoba.starwarz.domain.models.SpecieResponse
import com.github.chizoba.starwarz.getIdFromUrl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeciesRepository @Inject constructor(private val remoteSpeciesDataSource: RemoteSpeciesDataSource) {

    private val cache = mutableMapOf<Long, SpecieResponse>()

    fun getCharacterSpecie(id: Long): Result<SpecieResponse> {
//        val result = remoteSpeciesDataSource.getSpecie(id)
//        if (result is Result.Success) {
//            cache(result.data)
//        }
//        return result

        val specie = cache[id]
        if (specie != null) {
            return Result.Success(specie)
        }
        val result = remoteSpeciesDataSource.getSpecie(id)
        if (result is Result.Success) {
            cache(result.data)
        }
        return result
    }

    private fun cache(data: SpecieResponse) {
        cache[getIdFromUrl(data.url)] = data
    }
}