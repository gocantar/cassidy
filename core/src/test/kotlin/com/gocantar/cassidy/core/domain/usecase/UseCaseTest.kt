package com.gocantar.cassidy.core.domain.usecase

import com.gocantar.cassidy.tools.functional.Either
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

/**
 * @author Gonzalo Cantarero Pérez
 */

@ExperimentalCoroutinesApi
@DisplayName("Use Case´s Tests")
class UseCaseTest {

    @get:ExtendWith
    val coroutinesTestRule = CoroutinesTestRule()

    private val dispatcher: TestCoroutineDispatcher
        get() = coroutinesTestRule.dispatcher

    private val useCase: MockedUseCase = spyk(MockedUseCase())

    @BeforeEach
    fun setUp() {
        every { useCase.backgroundTask(null) } returns Either.left("left")
        every { useCase.backgroundTask("params") } returns Either.right("right")
    }

    @Test
    @DisplayName("Execute sync with null params as default value")
    fun givenSyncExecution_whenThereAreNotParamsGiven_thenExecuteSyncTaskWithNullAsDefaultValue() = dispatcher.runBlockingTest {
        val result = useCase.execute()
        verify { useCase.backgroundTask(null) }
        assertEquals("left", result.left)
    }

    @Test
    @DisplayName("Execute sync with given params as value")
    fun givenSyncExecution_whenParamsAreGiven_thenExecuteSyncTaskWithParams() = dispatcher.runBlockingTest {
        val result = useCase.execute("params")
        verify { useCase.backgroundTask("params") }
        assertEquals("right", result.right)
    }

    @Test
    @DisplayName("Execute async with null params as default value")
    fun givenAsyncExecution_whenThereAreNotParamsGiven_thenExecuteSyncTaskWithNullAsDefaultValue() = dispatcher.runBlockingTest {
        val result = useCase.executeAsync()
        verify { useCase.backgroundTask(null) }
        assertEquals("left", result.await().left)
    }

    @Test
    @DisplayName("Execute async with given params as value")
    fun givenAsyncExecution_whenParamsAreGiven_thenExecuteSyncTaskWithParams() = dispatcher.runBlockingTest {
        val result = useCase.executeAsync("params")
        verify { useCase.backgroundTask("params") }
        assertEquals("right", result.await().right)
    }

    @Test
    @DisplayName("Execute async with given params as value")
    fun givenAsyncExecution_whenIsExecuteAsyncWithDelay_thenExecuteTaskAfterDelay() = dispatcher.runBlockingTest {
        val result = useCase.executeAsync("params", 1_000)
        advanceTimeBy(990)
        verify(exactly = 0) { useCase.backgroundTask("params") }
        advanceTimeBy(10)
        verify(exactly = 1) { useCase.backgroundTask("params") }
        assertEquals("right", result.await().right)
    }

    inner class MockedUseCase : UseCase<String?, String, String>(
        coroutinesTestRule.dispatcher,
        coroutinesTestRule.scope
    ) {
        override fun backgroundTask(params: String?): Either<String, String> = mockk()
    }
}
