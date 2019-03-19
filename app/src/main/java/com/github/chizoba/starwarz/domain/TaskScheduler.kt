package com.github.chizoba.starwarz.domain

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val NUMBER_OF_THREADS = 3

interface Scheduler {

    fun execute(task: () -> Unit)

}

/**
 * A shim [Scheduler] that by default handles operations in the [AsyncScheduler].
 */
object DefaultScheduler : Scheduler {

    private var delegate: Scheduler = AsyncScheduler

    override fun execute(task: () -> Unit) {
        delegate.execute(task)
    }

}

/**
 * Runs tasks in a [ExecutorService] with a fixed thread of pools
 */
internal object AsyncScheduler : Scheduler {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

    override fun execute(task: () -> Unit) {
        executorService.execute(task)
    }

}

/**
 * Runs tasks synchronously. (Mostly need in this project for tests)
 */
object SyncScheduler : Scheduler {

    override fun execute(task: () -> Unit) {
        task()
    }

}