package com.cassidy.widgets.text.amount.models

import java.math.BigDecimal

/**
 * @author Gonzalo Cantarero Pérez
 */

data class Amount(val amount: BigDecimal? = null, val code: String? = null) {
    enum class Style { DEFAULT, TOP_MINIMIZED_SYMBOL }
}