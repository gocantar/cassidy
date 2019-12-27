package com.gocantar.cassidy.tools.functional

/**
 * @author Gonzalo Cantarero Pérez, Mar 2019
 */

class EitherProcessorBuilder<L, R> {

    lateinit var either: Either<L, R>

    private var left: (L) -> Unit = {}
    private var right: (R) -> Unit = {}

    fun left(block: (L) -> Unit) { this.left = block }

    fun right(block: (R) -> Unit) { this.right = block }

    fun process() {
        either.fold(left, right)
    }

    private inline fun <L, R> Either<L, R>.fold(left: (L) -> Unit, right: (R) -> Unit) {
        when (this) {
            is Either.Left -> left(this.value)
            is Either.Right -> right(this.value)
        }
    }
}