package com.github.chizoba.starwarz

interface ResultCallback<T> {
    fun onSuccess(data: T)
    fun onError(e: Exception)
    fun onLoading(isLoading: Boolean)
}