package com.github.chizoba.starwarz.ui

import android.view.View

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.visibility(isLoading: Boolean) {
    this.visibility = if (isLoading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}