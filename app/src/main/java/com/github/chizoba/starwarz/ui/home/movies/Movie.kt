package com.github.chizoba.starwarz.ui.home.movies

data class Movie(
    val id: Long,
    val title: String,
    val releaseDate: String,
    val director: String,
    val producer: String,
    val openingCrawl: String
)