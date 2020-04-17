package com.gocantar.cassidy.tools.models

import com.gocantar.cassidy.test.UnitTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * @author Gonzalo Cantarero Pérez, Mar 2019
 */

@DisplayName("Event")
class EventTest : UnitTest {

    @Test
    @DisplayName("Given event should be dispatched just once")
    fun givenAnEvent_shouldBeDispatchedJustOnce() {
        val event = Event(true)
        assertTrue(event.getOrNull() == true)
        assertNull(event.getOrNull())
    }

    @Test
    @DisplayName("Given event when it's already been handled then can be peeked")
    fun givenAnEvent_whenAlreadyHasBeenHandled_thenCanBePeeked() {
        val event = Event(true)
        assertTrue(event.getOrNull() == true)
        assertTrue(event.peek())
    }
}