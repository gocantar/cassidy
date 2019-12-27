package com.cassidy.widgets.text.amount.delegates

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import com.cassidy.widgets.text.amount.models.AmountModel

/**
 * @author Gonzalo Cantarero Pérez
 */

class AmountStyleDelegate {

    fun transform(amountFormatted: String?, symbol: String?, style: AmountModel.Style): Spannable {
        amountFormatted ?: return SpannableString.valueOf("")
        return when (style) {
            AmountModel.Style.TOP_MINIMIZED_SYMBOL -> amountFormatted.minimizeCharAtTop(symbol)
            AmountModel.Style.DEFAULT -> SpannableString.valueOf(amountFormatted)
        }
    }

    private fun String.minimizeCharAtTop(symbol: String?): Spannable {
        val spannable = SpannableStringBuilder(this)
        symbol?.forEach {
            val position = indexOf(it)
            if (position == -1) return@forEach
            spannable.moveCharAtTop(position)
            spannable.minimizeChar(position)
        }
        return spannable
    }

    private fun Spannable.minimizeChar(position: Int) {
        setSpan(RelativeSizeSpan(0.60f), position, position + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun Spannable.moveCharAtTop(position: Int) {
        setSpan(TopMinimizeChar(), position, position + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private inner class TopMinimizeChar : SuperscriptSpan() {

        private val COEFFICIENT_DISPLACEMENT = 3.25

        override fun updateDrawState(tp: TextPaint) {
            tp.baselineShift += (tp.ascent() / COEFFICIENT_DISPLACEMENT).toInt()
        }

        override fun updateMeasureState(tp: TextPaint) {
            tp.baselineShift += (tp.ascent() / COEFFICIENT_DISPLACEMENT).toInt()
        }
    }
}