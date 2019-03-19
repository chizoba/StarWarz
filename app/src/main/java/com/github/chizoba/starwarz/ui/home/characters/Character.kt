package com.github.chizoba.starwarz.ui.home.characters

data class Character(
    val id: Long,
    val name: String,
    val birthYear: String,
    val height: String,
    val species: List<String>,
    val planet: String, // homeworld
    val films: List<String>
)