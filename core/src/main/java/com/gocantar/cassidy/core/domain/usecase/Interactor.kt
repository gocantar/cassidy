package com.gocantar.cassidy.core.domain.usecase

import com.gocantar.cassidy.tools.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers

/**
 * @author Gonzalo Cantarero Pérez
 */

interface Interactor<Params, L, R> {
    suspend fun executeAsync(params: Params? = null, delay: Long = 0L): Deferred<Either<L, R>>
    suspend fun execute(params: Params? = null, delay: Long = 0L): Either<L, R>
}