package com.cassidy.widgets.text.amount

import android.text.Spannable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val amount: AmountModel = AmountModel()

    private var _amountLabel = MutableLiveData<Spannable>()
    internal val amountLabel: LiveData<Spannable>
        get() = _amountLabel

    internal fun configure(code: String?, symbolStyle: Int) {
        amount.code = code
        amount.style = when (symbolStyle) {
            TOP_MINIMIZED_SYMBOL_STYLE -> AmountModel.Style.TOP_MINIMIZED_SYMBOL
            else -> AmountModel.Style.DEFAULT
        }
    }

    internal fun setAmount(amount: Amount) {
        updateModel(amount)
        updateLabel()
    }

    private fun updateModel(amount: Amount) {
        this.amount.value = amount.value
        if (amount.code.isNullOrBlank().not() && this.amount.code != amount.code) {
            this.amount.code = amount.code
        }
    }

    private fun updateLabel() {
        val format = currenciesFormat.find { it.code.equals(amount.code, ignoreCase = true) }
        val amountFormatted = formatter.format(amount.value, format)
        _amountLabel.value = delegate.transform(amountFormatted, format?.symbol, amount.style)
    }
}