package com.cassidy.widgets.text.amount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.cassidy.widgets.text.amount.formatter.CurrencyFormatter
import com.cassidy.widgets.text.amount.models.Amount
import com.cassidy.widgets.text.amount.models.CurrencyFormat

/**
 * @author Gonzalo Cantarero Pérez
 */

internal class AmountTextViewModel(private val formatter: CurrencyFormatter = CurrencyFormatter()) {

    internal var currenciesFormat: List<CurrencyFormat> = emptyList()

    private val amount: MutableLiveData<Amount> = MutableLiveData()
    internal val amountLabel: LiveData<Pair<String?, String?>> = amount.map { format(it) }

    internal fun configure(code: String?) {
        amount.value = Amount(code = code)
    }

    internal fun setAmount(amount: Amount) {
        this.amount.value = Amount(amount.value, amount.code ?: amountCode)
    }

    private fun format(amount: Amount): Pair<String?, String?> {
        val format = currenciesFormat.find { it.code.equals(amount.code, ignoreCase = true) }
        return Pair(formatter.format(amount.value, format), format?.symbol)
    }

    private val amountCode: String?
        get() = amount.value?.code
}