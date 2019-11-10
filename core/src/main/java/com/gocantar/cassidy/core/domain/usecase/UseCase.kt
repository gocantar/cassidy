package com.gocantar.cassidy.core.domain.usecase

import com.gocantar.cassidy.tools.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * @author Gonzalo Cantarero Pérez
 */

class UseCase<Params, L, R>(
    private val delay: Long = 0,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val background: (Params?) -> Either<L, R>
) : Interactor<Params, L, R> {

    override suspend fun executeAsync(params: Params?): Deferred<Either<L, R>> {
        return coroutineScope {
            async(dispatcher) { executeBackgroundTask(params) }
        }
    }

    override suspend fun execute(params: Params?): Either<L, R> {
        return withContext(dispatcher) { executeBackgroundTask(params) }
    }

    private suspend fun executeBackgroundTask(params: Params?): Either<L, R> {
        delay(delay)
        return background(params)
    }
}