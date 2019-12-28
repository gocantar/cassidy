/*
 *  Created by Gonzalo Cantarero Pérez on Apr 2019
 *  Copyright (c) 2019 . All rights reserved.
 */

package com.gocantar.cassidy.tools.either

/**
 * @author Gonzalo Cantarero Pérez, Mar 2019
 */

sealed class Either<out L, out R> {

    val isRight: Boolean get() = this is Right<R>
    val isLeft: Boolean get() = this is Left<L>

    val left: L get() = when (this) {
        is Left -> value
        is Right -> throw Exception("No left value has been found")
    }

    val right: R get() = when (this) {
        is Left -> throw Exception("No right value has been found")
        is Right -> value
    }

    data class Left<out L>(val value: L) : Either<L, Nothing>()

    data class Right<out R>(val value: R) : Either<Nothing, R>()

    companion object {
        fun <L> left(value: L): Either<L, Nothing> = Left(value)
        fun <R> right(value: R): Either<Nothing, R> = Right(value)
    }

    inline fun <R2> map(transform: (R) -> R2): Either<L, R2> {
        return when (this) {
            is Left -> this
            is Right -> right(transform(value))
        }
    }

    inline fun <L2> mapError(transform: (L) -> L2): Either<L2, R> {
        return when (this) {
            is Left -> left(transform(value))
            is Right -> this
        }
    }
}