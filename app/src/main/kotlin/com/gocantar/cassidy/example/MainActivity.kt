package com.gocantar.cassidy.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cassidy.widgets.text.amount.models.Amount
import com.cassidy.widgets.text.amount.models.CurrencyFormat
import com.gocantar.cassidy.app.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val EURO_FORMAT = CurrencyFormat(
        code = "EUR",
        symbol = "€",
        symbolBehaviour = CurrencyFormat.Behaviour.RIGHT,
        signBehavior = CurrencyFormat.Behaviour.LEFT,
        groupingSeparator = CurrencyFormat.Separator.DOT,
        decimalSeparator = CurrencyFormat.Separator.COMMA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureRoundImage()
        configureAmountLabel()
    }

    private fun configureRoundImage() {
        roundImage.setOnClickListener {
            Toast.makeText(baseContext, "Image tapped", Toast.LENGTH_LONG).show()
        }
    }

    private fun configureAmountLabel() {
        amountLabel.provideFormats(listOf(EURO_FORMAT))
        amountLabel.setAmount(Amount(200.00))
    }
}
