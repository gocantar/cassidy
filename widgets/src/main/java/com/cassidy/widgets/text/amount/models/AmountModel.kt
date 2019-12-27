package com.cassidy.widgets.text.amount.models

/**
 * @author Gonzalo Cantarero Pérez
 */

data class AmountModel(
    var value: Double? = null,
    var code: String? = null,
    var style: Style = Style.DEFAULT
) {
    enum class Style { DEFAULT, TOP_MINIMIZED_SYMBOL }
}