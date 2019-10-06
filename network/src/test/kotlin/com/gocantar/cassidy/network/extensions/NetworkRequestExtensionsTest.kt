package com.gocantar.cassidy.network.extensions

import com.gocantar.cassidy.network.constants.Method
import com.gocantar.cassidy.network.models.request.NetworkRequest
import com.gocantar.cassidy.network.models.request.body.RequestBody
import com.gocantar.cassidy.network.tools.assertThat
import okio.Buffer
import org.junit.Test

/**
 * @author Gonzalo Cantarero Pérez
 */

class NetworkRequestExtensionsTest {

    @Test
    fun `given GET network request then map to OkHttp format request`() {

        val getRequest = NetworkRequest(
            method = Method.GET,
            url = "https://com.gocantar.cassidy"
        )
        val okHttpGetRequest = getRequest.asOkHttpRequest()

        okHttpGetRequest.assertThat {
            val isGet = method == "GET"
            val urlIsCorrect = url.host == "com.gocantar.cassidy"
            val hasEmptyHeaders = headers.size == 0
            val hasNullBody = body == null
            isGet && urlIsCorrect && hasEmptyHeaders && hasNullBody
        }

    }

    @Test
    fun `given POST network request then map to OkHttp format request`() {

        val getRequest = NetworkRequest(
            method = Method.POST,
            url = "https://com.gocantar.cassidy",
            body = RequestBody("Cassidy Project".toByteArray(Charsets.UTF_8))
        )
        val okHttpGetRequest = getRequest.asOkHttpRequest()

        okHttpGetRequest.assertThat {
            val isPost = method == "POST"
            val urlIsCorrect = url.host == "com.gocantar.cassidy"
            val hasEmptyHeaders = headers.size == 0
            val isCorrectBody = run {
                val buffer = Buffer()
                body?.writeTo(buffer)
                buffer.readByteString().utf8() == "Cassidy Project"
            }
            isPost && urlIsCorrect && hasEmptyHeaders && isCorrectBody
        }
    }

    @Test
    fun `given PUT network request then map to OkHttp format request`() {

        val getRequest = NetworkRequest(
            method = Method.PUT,
            url = "https://com.gocantar.cassidy",
            headers = mapOf("header" to "firstHeader"),
            body = RequestBody("Cassidy Project".toByteArray(Charsets.UTF_8))
        )
        val okHttpGetRequest = getRequest.asOkHttpRequest()

        okHttpGetRequest.assertThat {
            val isPut = method == "PUT"
            val urlIsCorrect = url.host == "com.gocantar.cassidy"
            val areHeadersCorrect = headers["header"] == "firstHeader"
            isPut && urlIsCorrect && areHeadersCorrect
        }
    }

    @Test
    fun `given DELETE network request then map to OkHttp format request`() {

        val getRequest = NetworkRequest(
            method = Method.DELETE,
            url = "https://com.gocantar.cassidy"
        )
        val okHttpGetRequest = getRequest.asOkHttpRequest()

        okHttpGetRequest.assertThat {
            val isDelete = method == "DELETE"
            val urlIsCorrect = url.host == "com.gocantar.cassidy"
            isDelete && urlIsCorrect
        }
    }
}