package com.github.chizoba.starwarz.domain.models

import com.github.chizoba.starwarz.getIdFromUrl
import com.github.chizoba.starwarz.ui.home.movies.Movie

data class MoviesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<MovieResponse>
)

data class MovieResponse(
    val characters: List<String>,
    val created: String,
    val director: String,
    val edited: String,
    val episode_id: Int,
    val opening_crawl: String,
    val planets: List<String>,
    val producer: String,
    val release_date: String,
    val species: List<String>,
    val starships: List<String>,
    val title: String,
    val url: String,
    val vehicles: List<String>
)

fun MovieResponse.toMovie() = Movie(
    id = getIdFromUrl(url),
    title = title,
    releaseDate = release_date,
    director = director,
    producer = producer,
    openingCrawl = opening_crawl
)
