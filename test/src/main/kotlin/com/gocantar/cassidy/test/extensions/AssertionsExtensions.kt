package com.gocantar.cassidy.test.extensions

import kotlin.test.assertTrue

inline fun <T> T.assertThat(block: T.() -> Boolean) {
    assertTrue(block.invoke(this))
}