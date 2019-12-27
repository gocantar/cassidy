package com.cassidy.widgets.text.amount

import android.text.Spannable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.cassidy.widgets.text.amount.delegates.AmountStyleDelegate
import com.cassidy.widgets.text.amount.formatter.CurrencyFormatter
import com.cassidy.widgets.text.amount.models.Amount
import com.cassidy.widgets.text.amount.models.AmountModel
import com.cassidy.widgets.text.amount.models.CurrencyFormat

/**
 * @author Gonzalo Cantarero Pérez
 */

internal class AmountTextViewModel(
    private val formatter: CurrencyFormatter = CurrencyFormatter(),
    private val delegate: AmountStyleDelegate = AmountStyleDelegate()
) {

    private val TOP_MINIMIZED_SYMBOL_STYLE = 1

    internal var currenciesFormat: List<CurrencyFormat> = emptyList()

    private val amount: MutableLiveData<AmountModel> = MutableLiveData()
    internal val amountLabel: LiveData<Spannable> = amount.map { amount ->
        val (amountFormatted, symbol) = format(amount)
        transform(amountFormatted, symbol, amount.style)
    }

    internal fun configure(code: String?, symbolStyle: Int) {
        val style = when (symbolStyle) {
            TOP_MINIMIZED_SYMBOL_STYLE -> AmountModel.Style.TOP_MINIMIZED_SYMBOL
            else -> AmountModel.Style.DEFAULT
        }
        amount.value = AmountModel(code = code, style = style)
    }

    internal fun setAmount(amount: Amount) {
        this.amount.value = AmountModel(amount.value, amount.code ?: amountCode, amountStyle)
    }

    private fun format(amount: AmountModel): Pair<String?, String?> {
        val format = currenciesFormat.find { it.code.equals(amount.code, ignoreCase = true) }
        return Pair(formatter.format(amount.value, format), format?.symbol)
    }

    private fun transform(amount: String?, symbol: String?, style: AmountModel.Style): Spannable {
        return delegate.transform(amount, symbol, style)
    }

    private val amountCode: String?
        get() = amount.value?.code

    private val amountStyle: AmountModel.Style
        get() = amount.value?.style ?: AmountModel.Style.DEFAULT
}