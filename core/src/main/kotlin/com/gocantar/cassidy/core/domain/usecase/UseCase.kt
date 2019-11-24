package com.gocantar.cassidy.core.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * @author Gonzalo Cantarero Pérez
 */

abstract class UseCase<Params, Result>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val scope: CoroutineScope = CoroutineScope(dispatcher)
) : Interactor<Params, Result> {

    override suspend fun executeAsync(params: Params?, delay: Long): Deferred<Result> {
        return scope.async { runTask(params, delay) }
    }

    override suspend fun execute(params: Params?, delay: Long): Result {
        return withContext(dispatcher) { runTask(params, delay) }
    }

    internal abstract fun backgroundTask(params: Params?): Result

    private suspend fun runTask(params: Params?, delay: Long): Result {
        delay(delay)
        return backgroundTask(params)
    }
}