package com.gocantar.cassidy.network.model.extensions

import com.gocantar.cassidy.network.model.extensions.okhttp.asNetworkResponse
import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.tools.UnitTest
import com.gocantar.cassidy.network.tools.assertThat
import com.gocantar.cassidy.network.tools.mock
import io.mockk.every
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * @author Gonzalo Cantarero Pérez
 */

@DisplayName("OkHttp Response Extensions")
class OkHttpResponseExtensionsTest : UnitTest {

    private val networkRequest: NetworkRequest = mock()
    private val okHttpResponse: Response = mock()
    private val okHTtpBody: ResponseBody = mock()

    @Test
    @DisplayName("Given success OkHttp response then map to network response")
    fun givenSuccessOkHttpResponse_thenMapToNetworkResponse() {
        mockOkHttpResponse(200, mapOf("header" to "firstHeader"), "Cassidy Project")
        val networkResponse = okHttpResponse.asNetworkResponse(networkRequest)
        networkResponse.assertThat {
            val isSuccess = code == 200
            val areHeadersCorrect = headers["header"] == "firstHeader" && headers.size == 1
            val isBodyCorrect = body.string == "Cassidy Project"
            val isRequestCorrect = request == networkRequest
            isSuccess && areHeadersCorrect && isBodyCorrect && isRequestCorrect
        }
    }

    @Test
    @DisplayName("Given error OkHttp response then map to network error response")
    fun givenErrorOkHttpResponse_thenMapToNetworkErrorResponse() {
        mockOkHttpResponse(400, mapOf(), "Cassidy Project Error")
        val networkError = okHttpResponse.asNetworkResponse(networkRequest).asError()
        networkError.assertThat {
            val isError = code == 400
            val isBodyCorrect = response?.body?.bytes?.contentEquals("Cassidy Project Error".toByteArray(Charsets.UTF_8))
                ?: false
            val isRequestCorrect = response?.request == networkRequest
            isError && isBodyCorrect && isRequestCorrect
        }
    }

    private fun mockOkHttpResponse(code: Int, headers: Map<String, String>, body: String) {
        every { okHttpResponse.code } returns code
        every { okHttpResponse.headers } returns headers.okHttpHeaders()
        every { okHttpResponse.body } returns okHTtpBody
        every { okHTtpBody.string() } returns body
        every { okHTtpBody.bytes() } returns body.toByteArray(Charsets.UTF_8)
    }
}