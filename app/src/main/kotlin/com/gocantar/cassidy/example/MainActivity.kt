package com.gocantar.cassidy.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cassidy.widgets.image.avatar.models.Avatar
import com.cassidy.widgets.text.amount.models.Amount
import com.cassidy.widgets.text.amount.models.CurrencyFormat
import com.gocantar.cassidy.app.R
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal

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
        roundImage.setAvatar(Avatar("Gonzalo", "M"))
        roundImage.setOnClickListener {
            Toast.makeText(baseContext, "Image tapped", Toast.LENGTH_LONG).show()
            roundImage.setAvatar(Avatar("Maria", "M", Avatar.Style.TWO_INITIALS))
        }
    }

    private fun configureAmountLabel() {
        amountLabel.provideFormats(listOf(EURO_FORMAT))
        val amount = BigDecimal.valueOf(2000.00)
        amountLabel.setAmount(Amount(amount))
    }
}
