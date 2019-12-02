package com.cassidy.widgets.delegates

import android.view.View
import com.gocantar.cassidy.test.base.UnitTest
import com.gocantar.cassidy.test.base.mock
import com.gocantar.cassidy.test.extensions.equal
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.reflect.KProperty

/**
 * @author Gonzalo Cantarero Pérez
 */

@DisplayName("Nullable view property delegate")
class NullableViewPropertyDelegateTest: UnitTest {

    private val view: View = mock()
    private val kProperty: KProperty<String> = mock()

    @Test
    @DisplayName("Should be initialize with null as value")
    fun givenDelegateWithoutValue_thenInitializeWithNullAsValue() {
        val delegate = NullableViewPropertyDelegate<String>()
        val value = delegate.getValue(view, kProperty)
        value equal null
    }

    @Test
    @DisplayName("Should be initialize with the given value")
    fun givenDelegateWithValue_thenInitializeWithTheGivenValue() {
        val delegate = NullableViewPropertyDelegate("value")
        val value = delegate.getValue(view, kProperty)
        value equal "value"
    }

    @Test
    @DisplayName("Should set the new given value")
    fun givenValueToSet_thenShouldUpdateTheValue() {
        val delegate = NullableViewPropertyDelegate<String>()
        delegate.setValue(view, kProperty, "value")
        val value = delegate.getValue(view, kProperty)
        value equal "value"
    }

    @Test
    @DisplayName("Should invalidate the view after set a new value")
    fun givenValueToSet_thenShouldInvalidateTheView() {
        val delegate = NullableViewPropertyDelegate<String>()
        delegate.setValue(view, kProperty, "value")
        verify { view.invalidate() }
    }
}