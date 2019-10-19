package com.gocantar.cassidy.network.tools

import kotlin.test.assertTrue

internal inline fun <T> T.assertThat(block: T.() -> Boolean) {
    assertTrue(block.invoke(this))
}
