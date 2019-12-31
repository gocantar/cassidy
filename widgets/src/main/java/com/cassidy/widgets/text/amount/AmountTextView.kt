package com.cassidy.widgets.text.amount

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.cassidy.widgets.R
import com.cassidy.widgets.text.CustomTextView
import com.cassidy.widgets.text.amount.delegates.AmountStyleDelegate
import com.cassidy.widgets.text.amount.models.Amount
import com.cassidy.widgets.text.amount.models.CurrencyFormat

/**
 * @author Gonzalo Cantarero Pérez
 */

class AmountTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CustomTextView(context, attrs, defStyleAttr) {

    private val viewModel: AmountTextViewModel = AmountTextViewModel()

    private val style: Amount.Style
    private val styleDelegate: AmountStyleDelegate = AmountStyleDelegate()

    init {
        val styles = context.obtainStyledAttributes(
            attrs, R.styleable.AmountTextView, defStyleAttr, 0
        )
        with(styles) {
            style = Amount.Style.values()[getInt(R.styleable.AmountTextView_symbolStyle, 0)]
            viewModel.configure(getString(R.styleable.AmountTextView_code))
            recycle()
        }
    }

    override fun onLifecycleOwnerAttached(lifecycleOwner: LifecycleOwner) {
        viewModel.amount.observe(lifecycleOwner, Observer { amountLabel ->
            val (amount, symbol) = amountLabel
            text = styleDelegate.transform(amount, symbol, style)
        })
    }

    fun setAmount(amount: Amount) {
        viewModel.setAmount(amount)
    }

    fun provideFormats(currenciesFormat: List<CurrencyFormat>) {
        viewModel.currenciesFormat = currenciesFormat
    }
}