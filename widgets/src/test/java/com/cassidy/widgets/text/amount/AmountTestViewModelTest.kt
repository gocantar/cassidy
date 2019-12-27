package com.cassidy.widgets.text.amount

import androidx.lifecycle.Observer
import com.cassidy.widgets.text.amount.formatter.CurrencyFormatter
import com.cassidy.widgets.text.amount.models.Amount
import com.cassidy.widgets.text.amount.models.CurrencyFormat
import com.gocantar.cassidy.test.base.UnitTest
import com.gocantar.cassidy.test.base.mock
import com.gocantar.cassidy.test.rules.InstantTaskExecutorExtension
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

@ExtendWith(InstantTaskExecutorExtension::class)
class AmountTestViewModelTest : UnitTest {

    private val observer: Observer<Pair<String?, String?>> = mock()
    private val formatter: CurrencyFormatter = mock()

    private val viewModel: AmountTextViewModel = AmountTextViewModel(formatter)

    @BeforeEach
    fun setUp() {
        viewModel.currenciesFormat = listOf(format)
    }

    @AfterEach
    fun clear() {
        clearMocks(observer)
    }

    @Test
    @DisplayName("Given initial configuration then should be update label with [null,€]")
    fun givenInitialConfiguration_thenUpdateLabelWithNullAmount() {
        every { formatter.format(null, format) } returns null
        viewModel.amountLabel.observeForever(observer)
        viewModel.configure("EUR")
        verify { observer.onChanged(Pair(null, "€")) }
    }

    @Test
    @DisplayName("When set 9.99 as new amount then should be update label with [9.99€,€]")
    fun givenNewAmount_thenUpdateLabel() {
        warmUp()
        every { formatter.format(BigDecimal.valueOf(9.99), format) } returns "9.99€"
        viewModel.amountLabel.observeForever(observer)
        viewModel.setAmount(Amount(BigDecimal.valueOf(9.99)))
        verify { observer.onChanged(Pair("9.99€", "€")) }
    }

    private fun warmUp() {
        viewModel.configure("EUR")
        clearMocks(observer)
    }

    private val format = CurrencyFormat(
        code = "EUR",
        symbol = "€",
        symbolBehaviour = CurrencyFormat.Behaviour.RIGHT,
        signBehavior = CurrencyFormat.Behaviour.LEFT,
        groupingSeparator = CurrencyFormat.Separator.DOT,
        decimalSeparator = CurrencyFormat.Separator.COMMA
    )
}