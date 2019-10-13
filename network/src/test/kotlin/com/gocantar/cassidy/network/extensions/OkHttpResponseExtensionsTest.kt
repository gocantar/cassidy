package com.gocantar.cassidy.network.extensions

import com.gocantar.cassidy.network.extensions.okhttp.asNetworkError
import com.gocantar.cassidy.network.extensions.okhttp.asNetworkResponse
import com.gocantar.cassidy.network.models.error.NetworkError
import com.gocantar.cassidy.network.models.request.NetworkRequest
import com.gocantar.cassidy.network.tools.assertThat
import io.mockk.every
import io.mockk.mockk
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * @author Gonzalo Cantarero Pérez
 */

@DisplayName("OkHttp Response Extensions Tests")
class OkHttpResponseExtensionsTest {

    private val networkRequest: NetworkRequest = mockk()
    private val okHttpResponse: Response = mockk()
    private val okHTtpBody: ResponseBody = mockk()

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

        val networkError = okHttpResponse.asNetworkError(networkRequest) as NetworkError.ServerResponse

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