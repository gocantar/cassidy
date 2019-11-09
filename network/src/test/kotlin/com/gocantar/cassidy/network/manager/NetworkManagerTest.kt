package com.gocantar.cassidy.network.manager

import com.gocantar.cassidy.network.alias.NetworkCallback
import com.gocantar.cassidy.network.model.extensions.asOkHttpRequest
import com.gocantar.cassidy.network.model.extensions.okhttp.asNetworkResponse
import com.gocantar.cassidy.network.model.error.NetworkError
import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.model.response.NetworkResponse
import com.gocantar.cassidy.network.tools.UnitTest
import com.gocantar.cassidy.network.tools.assertThat
import com.gocantar.cassidy.network.tools.mock
import com.gocantar.cassidy.tools.functional.Either
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.IOException
import javax.net.ssl.SSLException
import kotlin.test.assertEquals

/**
 * @author Gonzalo Cantarero Pérez
 */

@DisplayName("OkHttp Network Manager")
class NetworkManagerTest : UnitTest {

    private val networkRequest: NetworkRequest = mock()
    private val networkResponse: NetworkResponse = mock()
    private val networkError: NetworkError.FailedResponse = mock()
    private val okHttpRequest: Request = mock()
    private val okHttpResponse: Response = mock()
    private val call: Call = mock()

    private val client: OkHttpClient = mock()
    private val networkManager = NetworkManager(client)

    @BeforeEach
    fun setUpTest() {
        configureMocks()
    }

    @Nested
    @DisplayName("Given a request when do sync call")
    inner class SynchronousCalls {
        @Test
        @DisplayName("When response is success then return success network result")
        fun givenNetworkRequest_whenResponseIsSuccess_thenReturnSuccessNetworkResult() {
            mockSyncResponseResult(true)
            val result = networkManager.execute(networkRequest)
            assertEquals(networkResponse, result.right)
        }

        @Test
        @DisplayName("When response is an error then return failed response error")
        fun givenNetworkRequest_whenResponseIsNoSuccessFull_thenReturnErrorNetworkResult() {
            mockSyncResponseResult(false)
            val result = networkManager.execute(networkRequest)
            assertEquals(networkError, result.left)
        }

        @Test
        @DisplayName("When SSL exception is thrown then return SSL error")
        fun givenNetworkRequest_whenSSLExceptionIsThrows_thenReturnErrorNetworkResult() {
            val exception: SSLException = mock()
            mockThrowException(exception)
            val result = networkManager.execute(networkRequest)
            result.left.assertThat { this is NetworkError.SSLConnection }
        }

        @Test
        @DisplayName("When IO exception is thrown then return connection error")
        fun givenNetworkRequest_whenConnectionExceptionIsThrows_thenReturnErrorNetworkResult() {
            val exception: IOException = mock()
            mockThrowException(exception)
            val result = networkManager.execute(networkRequest)
            result.left.assertThat { this is NetworkError.Connection }
        }

        private fun mockSyncResponseResult(isSuccess: Boolean) {
            every { call.execute() } returns okHttpResponse
            mockResponseResult(isSuccess)
        }

        private fun mockThrowException(exception: Exception) {
            every { call.execute() } throws exception
        }
    }

    @Nested
    @DisplayName("Given a request when do async call")
    inner class AsynchronousCalls {

        private val callbackSlot: CapturingSlot<Callback> = slot()
        private val networkCallback: NetworkCallback = mock()

        @Test
        @DisplayName("When response is success then invoke callback with success network result")
        fun givenNetworkRequest_whenResponseIsSuccess_thenInvokeCallBackWithSuccessNetworkResult() {
            mockAsyncResponseResult(true)
            networkManager.enqueue(networkRequest, networkCallback)
            verify { networkCallback(Either.right(networkResponse)) }
        }

        @Test
        @DisplayName("When response is an error then invoke callback with failed response error")
        fun givenNetworkRequest_whenResponseIsNoSuccessFull_thenInvokeCallBackWithErrorNetworkResult() {
            mockAsyncResponseResult(false)
            networkManager.enqueue(networkRequest, networkCallback)
            verify { networkCallback(Either.left(networkError)) }
        }

        @Test
        @DisplayName("When SSL exception is thrown then invoke callback with SSL error")
        fun givenNetworkRequest_whenSSLExceptionIsThrows_thenInvokeCallBackWithErrorNetworkResult() {
            val exception: SSLException = mock()
            mockAsyncResponseFailure(exception)
            networkManager.enqueue(networkRequest, networkCallback)
            verify { networkCallback(Either.left(NetworkError.SSLConnection(networkRequest))) }
        }

        @Test
        @DisplayName("When IO exception is thrown then invoke callback with connection error")
        fun givenNetworkRequest_whenConnectionExceptionIsThrows_thenInvokeCallBackWithErrorNetworkResult() {
            val exception: IOException = mock()
            mockAsyncResponseFailure(exception)
            networkManager.enqueue(networkRequest, networkCallback)
            verify { networkCallback(Either.left(NetworkError.Connection(networkRequest))) }
        }

        private fun mockAsyncResponseResult(isSuccess: Boolean) {
            every { call.enqueue(capture(callbackSlot)) } answers {
                callbackSlot.captured.onResponse(mock(), okHttpResponse)
            }
            mockResponseResult(isSuccess)
        }

        private fun mockAsyncResponseFailure(exception: IOException) {
            every { call.enqueue(capture(callbackSlot)) } answers {
                callbackSlot.captured.onFailure(mock(), exception)
            }
        }
    }

    private fun mockResponseResult(isSuccess: Boolean) {
        every { okHttpResponse.isSuccessful } returns isSuccess
    }

    private fun configureMocks() {
        mockExtensions()
        every { client.newCall(any()) } returns call
        every { networkRequest.asOkHttpRequest() } returns okHttpRequest
        every { okHttpResponse.asNetworkResponse(networkRequest) } returns networkResponse
        every { networkResponse.asError() } returns networkError
    }

    private fun mockExtensions() {
        mockkStatic("com.gocantar.cassidy.network.model.extensions.NetworkRequestExtensionsKt")
        mockkStatic("com.gocantar.cassidy.network.model.extensions.okhttp.OkHttpResponseExtensionsKt")
    }
}