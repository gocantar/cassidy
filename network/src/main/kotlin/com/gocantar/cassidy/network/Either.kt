/*
 *  Created by Gonzalo Cantarero Pérez on Apr 2019
 *  Copyright (c) 2019 . All rights reserved.
 */

package com.gocantar.cassidy.network

/**
 * @author Gonzalo Cantarero Pérez, Mar 2019
 */

sealed class Either<out L, out R> {

    val isRight: Boolean get() = this is Right<R>

    val isLeft: Boolean get() = this is Left<L>

    val left: L get() = when (this) {
        is Left -> value
        is Right -> throw Exception("This either object has not a left value")
    }

    val right: R get() = when (this) {
        is Left -> throw Exception("This either object has not a right value")
        is Right -> value
    }

    data class Left<out L>(val value: L) : Either<L, Nothing>()

    data class Right<out R>(val value: R) : Either<Nothing, R>()

    companion object {
        fun <L> left(value: L): Either<L, Nothing> = Left(value)
        fun <R> right(value: R): Either<Nothing, R> = Right(value)
    }
}

class FoldBuilder<L, R> {

    private var left: (L) -> Unit = {}

    private var right: (R) -> Unit = {}

    lateinit var either: Either<L, R>

    fun left(block: (L) -> Unit) { this.left = block }

    fun right(block: (R) -> Unit) { this.right = block }

    fun process() {
        return either.fold(left, right)
    }
}

inline fun <L, R> fold(either: Either<L, R>, block: FoldBuilder<L, R>.() -> Unit) {

    FoldBuilder<L, R>().apply {
        this.either = either
        block()
    }.process()
}

private inline fun <L, R> Either<L, R>.fold(left: (L) -> Unit, right: (R) -> Unit) {

    return when (this) {
        is Either.Left -> left(this.value)
        is Either.Right -> right(this.value)
    }
}

inline fun <R> either(block: () -> R): Either<Exception, R> {

    return try {
        Either.right(block())
    } catch (exception: Exception) {
        Either.left(Exception("Exception while wrapping either", exception))
    }
}

inline infix fun <L, R, R2> Either<L, R>.mapRight(transform: (R) -> R2): Either<L, R2> {

    return when (this) {
        is Either.Left -> this
        is Either.Right -> Either.right(transform(this.value))
    }
}

inline infix fun <L, L2, R> Either<L, R>.mapLeft(errorTransform: (L) -> L2): Either<L2, R> {

    return when (this) {
        is Either.Left -> Either.left(errorTransform(value))
        is Either.Right -> this
    }
}
