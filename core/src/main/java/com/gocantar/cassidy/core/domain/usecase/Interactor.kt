package com.gocantar.cassidy.core.domain.usecase

import com.gocantar.cassidy.tools.functional.Either
import kotlinx.coroutines.Deferred

/**
 * @author Gonzalo Cantarero Pérez
 */

interface Interactor<Params, L, R> {
    suspend fun executeAsync(params: Params? = null): Deferred<Either<L, R>>
    suspend fun execute(params: Params? = null): Either<L, R>
}