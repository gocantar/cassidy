package com.gocantar.cassidy.core.domain.usecase

import com.gocantar.cassidy.tools.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * @author Gonzalo Cantarero Pérez
 */

abstract class UseCase<Params, L, R>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val scope: CoroutineScope = CoroutineScope(dispatcher)
) : Interactor<Params, L, R> {

    override suspend fun executeAsync(params: Params?, delay: Long): Deferred<Either<L, R>> {
        return scope.async { runTask(params, delay) }
    }

    override suspend fun execute(params: Params?, delay: Long): Either<L, R> {
        return withContext(dispatcher) { runTask(params, delay) }
    }

    internal abstract fun backgroundTask(params: Params?): Either<L,R>

    private suspend fun runTask(params: Params?, delay: Long): Either<L, R> {
        delay(delay)
        return backgroundTask(params)
    }
}