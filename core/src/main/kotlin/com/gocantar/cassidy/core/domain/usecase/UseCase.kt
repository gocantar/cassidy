package com.gocantar.cassidy.core.domain.usecase

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

abstract class UseCase<Params, Result>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    /**
     * Execute use case with provided delay
     * @params Use case params. If no params are provided his value is null.
     * @delay Delay before execute the use case. If no delay is provided default delay is 0.
     * @return Use case result as Deferred. Call result.await() to get the result value.
     */
    suspend operator fun invoke(params: Params? = null, delay: Long = 0L): Result {
        return withContext(dispatcher) {
            runTask(params, delay)
        }
    }

    abstract fun backgroundTask(params: Params?): Result

    private suspend fun runTask(params: Params?, delay: Long): Result {
        delay(delay)
        return backgroundTask(params)
    }
}