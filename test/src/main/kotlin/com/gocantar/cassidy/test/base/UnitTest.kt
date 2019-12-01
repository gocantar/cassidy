package com.gocantar.cassidy.test.base

import io.mockk.mockk

interface UnitTest

inline fun <reified T : Any> UnitTest.mock(): T {
    return mockk(relaxed = true)
}