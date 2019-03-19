package com.github.chizoba.starwarz.domain

import com.github.chizoba.starwarz.ResultCallback
import com.github.chizoba.starwarz.data.Result

abstract class UseCase<in P, R> {

    private val taskScheduler = DefaultScheduler

    operator fun invoke(
        parameters: P,
        onSuccess: (R) -> Unit,
        onError: (Exception) -> Unit,
        onLoading: (Boolean) -> Unit
    ) {
        onLoading.invoke(true)
        taskScheduler.execute {
            execute(parameters).let {
                onLoading.invoke(false)
                when (it) {
                    is Result.Success -> onSuccess.invoke(it.data)
                    is Result.Error -> onError.invoke(it.exception)
                }
            }
        }
    }

    operator fun invoke(
        parameters: P,
        callback: ResultCallback<R>
    ) {
        callback.onLoading(true)
        taskScheduler.execute {
            execute(parameters).let {
                callback.onLoading(false)
                when (it) {
                    is Result.Success -> callback.onSuccess(it.data)
                    is Result.Error -> callback.onError(it.exception)
                }
            }
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    protected abstract fun execute(parameters: P): Result<R>

}