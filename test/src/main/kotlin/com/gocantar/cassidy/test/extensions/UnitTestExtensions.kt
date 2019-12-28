package com.gocantar.cassidy.test.extensions

import com.gocantar.cassidy.test.UnitTest
import io.mockk.mockk
import io.mockk.spyk

inline fun <reified T : Any> UnitTest.mock(): T {
    return mockk(relaxed = true)
}

inline fun <reified T : Any> UnitTest.spy(): T {
    return spyk()
}