package com.gocantar.cassidy.tools.either

import com.gocantar.cassidy.test.extensions.assertThat
import com.gocantar.cassidy.test.extensions.equal
import com.gocantar.cassidy.tools.either.extensions.either
import com.gocantar.cassidy.tools.either.extensions.fold
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

/**
 * @author Gonzalo Cantarero Pérez, Mar 2019
 */

@DisplayName("Either")
class EitherTest {

    @Nested
    @DisplayName("Sealed class")
    inner class SealedClassTest {

        @Test
        @DisplayName("Should create a either object with string as right value")
        fun shouldCreateRightEither() {
            val either: Either<Unit, String> = Either.right("someValue")
            either.isRight equal true
            either.right equal "someValue"
        }

        @Test
        @DisplayName("Should create a either object with string as left value")
        fun shouldCreateLeftEither() {
            val either: Either<String, Unit> = Either.left("someValue")
            either.isLeft equal true
            either.left equal "someValue"
        }

        @Test
        @DisplayName("Given right value when try to get the left value then throw exception")
        fun givenRightValue_whenTryToGetLeftValue_thenThrowAnException() {
            val either: Either<Unit, String> = Either.right("someValue")
            val result = runCatching { either.left }
            result.isFailure equal true
        }

        @Test
        @DisplayName("Given left value when try to get the right value then throw exception")
        fun givenLeftValue_whenTryToGetRightValue_thenThrowAnException() {
            val either: Either<String, Unit> = Either.left("someValue")
            val result = runCatching { either.right }
            result.isFailure equal true
        }
    }

    @Nested
    @DisplayName("EitherExtensions")
    inner class EitherExtensionsTest {

        @Test
        @DisplayName("Given a block when is execute successfully then return right either")
        fun givenABlock_whenIsExecutedSuccess_thenReturnARightValue() {
            val number = "10"
            val either: Either<Exception, Int> = either { number.toInt() }
            either.right equal 10
        }

        @Test
        @DisplayName("Given a block when an exception is thrown then return left either with exception as value")
        fun givenABlock_whenAnExceptionIsThrown_thenReturnALeftEitherWithExceptionAsValue() {
            val number = "10%"
            val either: Either<Exception, Int> = either { number.toInt() }
            either.left.assertThat { cause is NumberFormatException }
        }

        @Test
        @DisplayName("Should map the right value of the either")
        fun shouldMapRightValue() {
            val either: Either<Unit, Int> = Either.right(5)
            val eitherMapped: Either<Unit, Int> = either.map { it * 2 }
            eitherMapped.right equal 10
        }

        @Test
        @DisplayName("Should map the left value of the either")
        fun shouldMapErrorValue() {
            val either: Either<Int, Unit> = Either.left(5)
            val eitherMapped: Either<Int, Unit> = either.mapError { it * 2 }
            eitherMapped.left equal 10
        }

        @Test
        @DisplayName("Given eight either when try to map error then return same right value")
        fun givenRightEither_whenTryToMapLeftValue_thenReturnSameRightValue() {
            val either: Either<Int, Int> = Either.right(5)
            val eitherMapped: Either<Int, Int> = either.mapError { it * 2 }
            eitherMapped.right equal 5
        }

        @Test
        @DisplayName("Given left either when try to map right value then return same left value")
        fun givenLeftEither_whenTryToMapRightValue_thenReturnSameLeftValue() {
            val either: Either<Int, Int> = Either.left(5)
            val eitherMapped: Either<Int, Int> = either.map { it * 2 }
            eitherMapped.left equal 5
        }

        @Test
        @DisplayName("Given right either then should execute the right block")
        fun shouldExecuteRightBlock() {
            val either: Either<String, String> = Either.right("success")
            fold(either) {
                right { it equal "success" }
                left { fail(Exception()) }
            }
        }

        @Test
        @DisplayName("Given left either then should execute the left block")
        fun shouldExecuteLeftBlock() {
            val either: Either<String, String> = Either.left("error")
            fold(either) {
                right { fail(Exception()) }
                left { it equal "error" }
            }
        }
    }
}