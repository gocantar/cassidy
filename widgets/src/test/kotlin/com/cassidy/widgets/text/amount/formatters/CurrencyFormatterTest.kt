package com.cassidy.widgets.text.amount.formatters

import com.cassidy.widgets.text.amount.models.CurrencyFormat
import com.gocantar.cassidy.test.UnitTest
import com.gocantar.cassidy.test.extensions.equal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.util.stream.Stream
import kotlin.test.assertNull

/**
 * @author Gonzalo Cantarero Pérez, Dic 2019
 */

@DisplayName("Currency formatter")
class CurrencyFormatterTest : UnitTest {

    @DisplayName("Given positive amount")
    @ParameterizedTest(name = "Should be format to {1}")
    @MethodSource("positiveParameterizedArguments")
    fun shouldFormatPositiveAmount(format: CurrencyFormat?, amountFormatted: String) {
        val formatter = CurrencyFormatter()
        val amount = BigDecimal.valueOf(999999.99)
        val result = formatter.format(amount, format)
        result equal amountFormatted
    }

    @DisplayName("Given negative amount")
    @ParameterizedTest(name = "Should be format to {1}")
    @MethodSource("negativeParameterizedArguments")
    fun shouldFormatNegativeAmount(format: CurrencyFormat?, amountFormatted: String) {
        val formatter = CurrencyFormatter()
        val amount = BigDecimal.valueOf(-999999.99)
        val result = formatter.format(amount, format)
        result equal amountFormatted
    }

    @Nested
    @DisplayName("Given null amount")
    inner class NullAmountTest {
        @Test
        @DisplayName("Should return null")
        fun whenAmountIsNull_shouldReturnNull() {
            val formatter = CurrencyFormatter()
            val result = formatter.format(
                null,
                format01
            )
            assertNull(result)
        }
    }

    companion object {
        @JvmStatic
        fun positiveParameterizedArguments(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(format01, "999.999,99€"),
                Arguments.of(format02, "999.999,99€"),
                Arguments.of(format03, "€999,999.99"),
                Arguments.of(format04, "€999,999.99"),
                Arguments.of(null, "999.999,99")
            )
        }

        @JvmStatic
        fun negativeParameterizedArguments(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(format01, "-999.999,99€"),
                Arguments.of(format02, "-999.999,99€"),
                Arguments.of(format03, "-€999,999.99"),
                Arguments.of(format04, "€-999,999.99"),
                Arguments.of(null, "-999.999,99")
            )
        }

        private val format01 = CurrencyFormat(
            code = "EUR",
            symbol = "€",
            symbolBehaviour = CurrencyFormat.Behaviour.RIGHT,
            signBehavior = CurrencyFormat.Behaviour.LEFT,
            groupingSeparator = CurrencyFormat.Separator.DOT,
            decimalSeparator = CurrencyFormat.Separator.COMMA
        )
        private val format02 = CurrencyFormat(
            code = "EUR",
            symbol = "€",
            symbolBehaviour = CurrencyFormat.Behaviour.RIGHT,
            signBehavior = CurrencyFormat.Behaviour.RIGHT,
            groupingSeparator = CurrencyFormat.Separator.DOT,
            decimalSeparator = CurrencyFormat.Separator.COMMA
        )
        private val format03 = CurrencyFormat(
            code = "EUR",
            symbol = "€",
            symbolBehaviour = CurrencyFormat.Behaviour.LEFT,
            signBehavior = CurrencyFormat.Behaviour.LEFT,
            groupingSeparator = CurrencyFormat.Separator.COMMA,
            decimalSeparator = CurrencyFormat.Separator.DOT
        )
        private val format04 = CurrencyFormat(
            code = "EUR",
            symbol = "€",
            symbolBehaviour = CurrencyFormat.Behaviour.LEFT,
            signBehavior = CurrencyFormat.Behaviour.RIGHT,
            groupingSeparator = CurrencyFormat.Separator.COMMA,
            decimalSeparator = CurrencyFormat.Separator.DOT
        )
    }
}