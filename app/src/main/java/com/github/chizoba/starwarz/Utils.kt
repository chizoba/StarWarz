package com.github.chizoba.starwarz

fun getIdFromUrl(url: String): Long = url.replace(Regex("[^\\d]"), "").toLong()