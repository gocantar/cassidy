package com.gocantar.cassidy.test.extensions

import kotlin.test.assertEquals
import kotlin.test.assertTrue

inline fun <T> T.assertThat(block: T.() -> Boolean) {
    assertTrue(block.invoke(this))
}

infix fun <T> T.equal(value: T) {
    assertEquals(this, value)
}