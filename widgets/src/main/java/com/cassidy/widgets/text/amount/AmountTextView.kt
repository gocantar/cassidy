package com.cassidy.widgets.text.amount

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.cassidy.widgets.R
import com.cassidy.widgets.text.CustomTextView
import com.cassidy.widgets.text.amount.models.Amount
import com.cassidy.widgets.text.amount.models.AmountModel
import com.cassidy.widgets.text.amount.models.CurrencyFormat

/**
 * @author Gonzalo Cantarero Pérez
 */

class AmountTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomTextView(context, attrs, defStyleAttr) {

    private val viewModel: AmountTextViewModel = AmountTextViewModel()

    init {
        attrs?.let { initializeAttributes(it, defStyleAttr) }
    }

    private fun initializeAttributes(attributes: AttributeSet, defStyleAttr: Int) {
        val styles = context.obtainStyledAttributes(
            attributes, R.styleable.AmountTextView, defStyleAttr, 0
        )
        with(styles) {
            val code = getString(R.styleable.AmountTextView_code)
            val symbolStyle = getInt(R.styleable.AmountTextView_symbolStyle, 0)
            viewModel.configure(code, symbolStyle)
            recycle()
        }
    }

    override fun onLifecycleOwnerAttached(lifecycleOwner: LifecycleOwner) {
        viewModel.amountLabel.observe(lifecycleOwner, Observer { text = it })
    }

    fun setAmount(amount: Amount) {
        viewModel.setAmount(amount)
    }

    fun provideFormats(currenciesFormat: List<CurrencyFormat>) {
        viewModel.currenciesFormat = currenciesFormat
    }
}