package com.cassidy.widgets.text.amount.models

/**
 * @author Gonzalo Cantarero Pérez
 */

data class CurrencyFormat(
    val code: String = "",
    val symbol: String = "",
    val symbolBehaviour: Behaviour = Behaviour.RIGHT,
    val signBehavior: Behaviour = Behaviour.LEFT,
    val groupingSeparator: Separator = Separator.DOT,
    val decimalSeparator: Separator = Separator.COMMA
) {
    enum class Behaviour { LEFT, RIGHT }
    enum class Separator(val symbol: Char) {
        COMMA(','), DOT('.')
    }
}