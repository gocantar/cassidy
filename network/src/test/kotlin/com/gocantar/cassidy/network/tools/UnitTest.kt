package com.gocantar.cassidy.network.tools

import io.mockk.mockk

interface UnitTest

inline fun <reified T: Any> UnitTest.mock(): T {
    return mockk(relaxed = true)
}

