package com.github.chizoba.starwarz.data

import com.github.chizoba.starwarz.domain.models.CharactersSearchResponse
import com.github.chizoba.starwarz.domain.models.MoviesResponse
import com.github.chizoba.starwarz.domain.models.PlanetResponse
import com.github.chizoba.starwarz.domain.models.SpecieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsAPI {

    @GET("films")
    fun getMovies(@Query("page") page: Long): Call<MoviesResponse>

    @GET("people")
    fun searchPeople(@Query("search") characterName: String, @Query("page") page: Long): Call<CharactersSearchResponse>

    @GET("species/{id}")
    fun getSpecie(@Path("id") id: Long): Call<SpecieResponse>

    @GET("planets/{id}")
    fun getPlanet(@Path("id") id: Long): Call<PlanetResponse>

    companion object {
        const val BASE_URL = "https://swapi.co/api/"
    }
}