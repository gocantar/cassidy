package com.gocantar.cassidy.network.model.extensions

import com.gocantar.cassidy.network.constants.Method
import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.model.request.body.RequestBody
import com.gocantar.cassidy.test.extensions.assertThat
import okio.Buffer
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * @author Gonzalo Cantarero Pérez
 */

@DisplayName("Network Request Extensions")
class NetworkRequestExtensionsTest {

    @Test
    @DisplayName("Given GET network request then map to GET with OkHttp format")
    fun givenGetRequest_thenMapToOkHttpFormat() {
        val getRequest = NetworkRequest(
            method = Method.GET,
            url = "https://com.gocantar.cassidy"
        )
        val okHttpGetRequest = getRequest.asOkHttpRequest()
        okHttpGetRequest.assertThat {
            val isGet = method == "GET"
            val isUrlCorrect = url.host == "com.gocantar.cassidy"
            val hasEmptyHeaders = headers.size == 0
            val hasNullBody = body == null
            isGet && isUrlCorrect && hasEmptyHeaders && hasNullBody
        }
    }

    @Test
    @DisplayName("Given POST network request then map to POST with OkHttp format")
    fun givenPostRequest_thenMapToOkHttpFormat() {
        val postRequest = NetworkRequest(
            method = Method.POST,
            url = "https://com.gocantar.cassidy",
            body = RequestBody("Cassidy Project".toByteArray(Charsets.UTF_8))
        )

        val okHttpGetRequest = postRequest.asOkHttpRequest()

        okHttpGetRequest.assertThat {
            val isPost = method == "POST"
            val isUrlCorrect = url.host == "com.gocantar.cassidy"
            val hasEmptyHeaders = headers.size == 0
            val isBodyCorrect = run {
                val buffer = Buffer()
                body?.writeTo(buffer)
                buffer.readByteString().utf8() == "Cassidy Project"
            }
            isPost && isUrlCorrect && hasEmptyHeaders && isBodyCorrect
        }
    }

    @Test
    @DisplayName("Given PUT network request then map to PUT with OkHttp format")
    fun givenPutRequest_thenMapToOkHttpFormat() {
        val putRequest = NetworkRequest(
            method = Method.PUT,
            url = "https://com.gocantar.cassidy",
            headers = mapOf("header" to "firstHeader"),
            body = RequestBody("Cassidy Project".toByteArray(Charsets.UTF_8))
        )

        val okHttpGetRequest = putRequest.asOkHttpRequest()

        okHttpGetRequest.assertThat {
            val isPut = method == "PUT"
            val isUrlCorrect = url.host == "com.gocantar.cassidy"
            val areHeadersCorrect = headers["header"] == "firstHeader"
            isPut && isUrlCorrect && areHeadersCorrect
        }
    }

    @Test
    @DisplayName("Given DELETE network request then map to DELETE with OkHttp format")
    fun givenDeleteRequest_thenMapToOkHttpFormat() {
        val deleteRequest = NetworkRequest(
            method = Method.DELETE,
            url = "https://com.gocantar.cassidy"
        )

        val okHttpGetRequest = deleteRequest.asOkHttpRequest()

        okHttpGetRequest.assertThat {
            val isDelete = method == "DELETE"
            val isUrlCorrect = url.host == "com.gocantar.cassidy"
            isDelete && isUrlCorrect
        }
    }
}