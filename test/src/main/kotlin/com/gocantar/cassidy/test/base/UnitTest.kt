package com.gocantar.cassidy.test.base

import io.mockk.mockk
import io.mockk.spyk

interface UnitTest

inline fun <reified T : Any> UnitTest.mock(): T {
    return mockk(relaxed = true)
}

inline fun <reified T : Any> UnitTest.spy(): T {
    return spyk()
}