package com.github.chizoba.starwarz.domain.models

import com.github.chizoba.starwarz.getIdFromUrl
import com.github.chizoba.starwarz.ui.home.characters.Character

data class CharactersSearchResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<CharacterResponse>
)

data class CharacterResponse(
    val birth_year: String,
    val created: String,
    val edited: String,
    val eye_color: String,
    val films: List<String>,
    val gender: String,
    val hair_color: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    val name: String,
    val skin_color: String,
    val species: List<String>,
    val starships: List<String>,
    val url: String,
    val vehicles: List<String>
)

fun CharacterResponse.toCharacter() = Character(
    id = getIdFromUrl(url),
    name = name,
    birthYear = birth_year,
    height = height,
    species = species,//[0],
    planet = homeworld,
    films = films
)
