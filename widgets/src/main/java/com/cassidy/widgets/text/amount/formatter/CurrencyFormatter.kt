package com.cassidy.widgets.text.amount.formatter

import com.cassidy.widgets.text.amount.models.CurrencyFormat
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @author Gonzalo Cantarero Pérez
 */

class CurrencyFormatter {

    private val NUMBER_OF_DECIMALS = 2

    fun format(amount: BigDecimal?, currentFormat: CurrencyFormat?): String? {
        amount ?: return null
        val format = currentFormat ?: CurrencyFormat()
        val formatter = getDecimalFormatterInstance().apply {
            roundingMode = RoundingMode.HALF_UP
            maximumFractionDigits = NUMBER_OF_DECIMALS
            minimumFractionDigits = NUMBER_OF_DECIMALS
            negativePrefix = format.configureNegativePrefix()
            positivePrefix = format.configurePositivePrefix()
            negativeSuffix = format.configureNegativeSuffix()
            positiveSuffix = format.configurePositiveSuffix()
        }
        formatter.decimalFormatSymbols = formatter.decimalFormatSymbols.apply {
            groupingSeparator = format.groupingSeparator.symbol
            decimalSeparator = format.decimalSeparator.symbol
        }
        return formatter.format(amount)
    }

    private fun getDecimalFormatterInstance(): DecimalFormat {
        return DecimalFormat()
    }

    private fun CurrencyFormat.configurePositivePrefix(): String {
        return when (symbolBehaviour) {
            CurrencyFormat.Behaviour.LEFT -> symbol
            CurrencyFormat.Behaviour.RIGHT -> ""
        }
    }

    private fun CurrencyFormat.configurePositiveSuffix(): String {
        return when (symbolBehaviour) {
            CurrencyFormat.Behaviour.RIGHT -> symbol
            CurrencyFormat.Behaviour.LEFT -> ""
        }
    }

    private fun CurrencyFormat.configureNegativePrefix(): String {
        return when {
            symbolBehaviour == CurrencyFormat.Behaviour.LEFT &&
                signBehavior == CurrencyFormat.Behaviour.LEFT -> "-${symbol}"
            symbolBehaviour == CurrencyFormat.Behaviour.LEFT &&
                signBehavior == CurrencyFormat.Behaviour.RIGHT -> "${symbol}-"
            else -> "-"
        }
    }

    private fun CurrencyFormat.configureNegativeSuffix(): String {
        return when (symbolBehaviour) {
            CurrencyFormat.Behaviour.RIGHT -> symbol
            CurrencyFormat.Behaviour.LEFT -> ""
        }
    }
}