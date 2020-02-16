package com.gocantar.cassidy.core.domain.usecase

import com.gocantar.cassidy.test.base.CoroutineTest
import com.gocantar.cassidy.test.extensions.equal
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.coroutines.experimental.migration.toExperimentalCoroutineContext

/**
 * @author Gonzalo Cantarero Pérez
 */

@ExperimentalCoroutinesApi
@DisplayName("Use Case´s")
class UseCaseTest : CoroutineTest() {

    private val useCase: MockedUseCase = spyk(MockedUseCase())

    @BeforeEach
    fun setUp() {
        every { useCase.backgroundTask(null) } returns "null-params"
        every { useCase.backgroundTask("params") } returns "non-null-params"
    }

    @Test
    @DisplayName("Execute sync with null params as default value")
    fun givenSyncExecution_whenThereAreNotParamsGiven_thenExecuteSyncTaskWithNullAsDefaultValue() =
        executeBlockingTest {
            val result = useCase()
            verify { useCase.backgroundTask(null) }
            result equal "null-params"
        }

    @Test
    @DisplayName("Execute sync with given params as value")
    fun givenSyncExecution_whenParamsAreGiven_thenExecuteSyncTaskWithParams() =
        executeBlockingTest {
            val result = useCase("params")
            verify { useCase.backgroundTask("params") }
            result equal "non-null-params"
        }


    @Test
    @DisplayName("Execute sync after delay")
    fun givenExecution_whenIsExecuteWithDelay_thenExecuteTaskAfterDelay() =
        executeBlockingTest {
            val result = useCase("params", 1_000)
            dispatcher.currentTime equal 1_000
            result equal "non-null-params"
        }

    inner class MockedUseCase : UseCase<String?, String>(dispatcher) {
        override fun backgroundTask(params: String?): String = mockk()
    }
}
