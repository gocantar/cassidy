package com.gocantar.cassidy.core.domain.usecase

import kotlinx.coroutines.Deferred

/**
 * @author Gonzalo Cantarero Pérez
 */

interface Interactor<Params, Result> {
    suspend fun executeAsync(params: Params? = null, delay: Long = 0L): Deferred<Result>
    suspend fun execute(params: Params? = null, delay: Long = 0L): Result
}