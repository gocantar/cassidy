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

@DisplayName("View property delegate")
class ViewPropertyDelegateTest: UnitTest {

    private val view: View = mock()
    private val kProperty: KProperty<String> = mock()

    @Test
    @DisplayName("Should be initialize with the given value")
    fun givenDelegateWithValue_thenInitializeWithTheGivenValue() {
        val delegate = ViewPropertyDelegate("value")
        val value = delegate.getValue(view, kProperty)
        value equal "value"
    }

    @Test
    @DisplayName("Should set the new given value")
    fun givenValueToSet_thenShouldUpdateTheValue() {
        val delegate = ViewPropertyDelegate("value")
        delegate.setValue(view, kProperty, "new_value")
        val value = delegate.getValue(view, kProperty)
        value equal "new_value"
    }

    @Test
    @DisplayName("Should invalidate the view after set a new value")
    fun givenValueToSet_thenShouldInvalidateTheView() {
        val delegate = ViewPropertyDelegate("value")
        delegate.setValue(view, kProperty, "new_value")
        verify { view.invalidate() }
    }
}