package com.gocantar.cassidy.test.base

import com.gocantar.cassidy.test.UnitTest
import com.gocantar.cassidy.test.rules.CoroutinesTestExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
open class CoroutineTest : UnitTest {

    @get:ExtendWith
    protected val coroutineTestRule = CoroutinesTestExtension()

    protected val dispatcher: TestCoroutineDispatcher
        get() = coroutineTestRule.dispatcher

    fun executeBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        return runBlockingTest(dispatcher, block)
    }
}