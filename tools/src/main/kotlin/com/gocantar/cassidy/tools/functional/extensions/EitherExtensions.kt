package com.gocantar.cassidy.tools.functional.extensions

import com.gocantar.cassidy.tools.functional.Either
import com.gocantar.cassidy.tools.functional.EitherProcessorBuilder

/**
 * @author Gonzalo Cantarero Pérez, Mar 2019
 */

inline fun <L, R> fold(either: Either<L, R>, block: EitherProcessorBuilder<L, R>.() -> Unit) {
    EitherProcessorBuilder<L, R>().apply {
        this.either = either
        block()
    }.process()
}

inline fun <R> either(block: () -> R): Either<Exception, R> {
    return try {
        Either.right(block())
    } catch (exception: Exception) {
        Either.left(Exception("Exception occurs executing the block", exception))
    }
}

inline fun <L, R, R2> Either<L, R>.map(transform: (R) -> R2): Either<L, R2> {
    return when (this) {
        is Either.Left -> this
        is Either.Right -> Either.right(transform(this.value))
    }
}

inline fun <L, L2, R> Either<L, R>.mapError(errorTransform: (L) -> L2): Either<L2, R> {
    return when (this) {
        is Either.Left -> Either.left(errorTransform(value))
        is Either.Right -> this
    }
}
