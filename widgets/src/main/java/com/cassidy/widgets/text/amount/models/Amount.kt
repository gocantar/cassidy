package com.cassidy.widgets.text.amount.models

import java.math.BigDecimal

/**
 * @author Gonzalo Cantarero Pérez
 */

data class Amount(val value: BigDecimal, val code: String? = null)