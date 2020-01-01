package com.cassidy.widgets.text.amount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.cassidy.widgets.text.amount.formatters.CurrencyFormatter
import com.cassidy.widgets.text.amount.models.Amount
import com.cassidy.widgets.text.amount.models.CurrencyFormat

/**
 * @author Gonzalo Cantarero Pérez
 */

internal class AmountTextViewModel(private val formatter: CurrencyFormatter = CurrencyFormatter()) {

    internal var currenciesFormat: List<CurrencyFormat> = emptyList()

    private val state: MutableLiveData<Amount> = MutableLiveData()
    internal val amount: LiveData<Pair<String?, String?>> = state.map { format(it) }

    private val amountCode: String? get() = state.value?.code

    internal fun configure(code: String?) {
        state.value = Amount(code = code)
    }

    internal fun setAmount(value: Amount) {
        this.state.value = Amount(value.amount, value.code ?: amountCode)
    }

    private fun format(value: Amount): Pair<String?, String?> {
        val format = currenciesFormat.find { it.code.equals(value.code, ignoreCase = true) }
        return Pair(formatter.format(value.amount, format), format?.symbol)
    }
}