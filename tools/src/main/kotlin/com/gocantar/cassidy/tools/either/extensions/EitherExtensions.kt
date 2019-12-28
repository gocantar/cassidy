package com.gocantar.cassidy.tools.either.extensions

import com.gocantar.cassidy.tools.either.Either
import com.gocantar.cassidy.tools.either.EitherFoldDSLBuilder

/**
 * @author Gonzalo Cantarero Pérez, Mar 2019
 */

inline fun <L, R> fold(either: Either<L, R>, block: EitherFoldDSLBuilder<L, R>.() -> Unit) {
    EitherFoldDSLBuilder<L, R>().apply {
        this.either = either
        block()
    }.process()
}

inline fun <R> either(block: () -> R): Either<Exception, R> {
    return try {
        Either.right(block())
    } catch (exception: Exception) {
        Either.left(
            Exception(
                "Exception occurs executing the block",
                exception
            )
        )
    }
}