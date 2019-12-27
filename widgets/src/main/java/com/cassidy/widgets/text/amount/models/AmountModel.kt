package com.cassidy.widgets.text.amount.models

import java.math.BigDecimal

/**
 * @author Gonzalo Cantarero Pérez
 */

data class AmountModel(
    var value: BigDecimal? = null,
    var code: String? = null,
    var style: Style = Style.DEFAULT
) {
    enum class Style { DEFAULT, TOP_MINIMIZED_SYMBOL }
}