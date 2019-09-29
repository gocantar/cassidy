package com.gocantar.cassidy.network.extensions

import com.gocantar.cassidy.network.constants.Method
import com.gocantar.cassidy.network.models.request.NetworkRequest
import org.junit.Test
import kotlin.test.assertTrue

/**
 * @author Gonzalo Cantarero Pérez
 */

class NetworkRequestExtensionsTest {

    @Test
    fun `given GET network request then map to OkHttp GET request`() {

        val getRequest = NetworkRequest(
            method = Method.GET,
            url = "https://com.gocantar.cassidy"
        )
        val okHttpGetRequest = getRequest.asOkHttpRequest()

        assertTrue {
            val isGet = okHttpGetRequest.method == "GET"
            val urlIsCorrect = okHttpGetRequest.url.host == "com.gocantar.cassidy"
            val hasEmptyHeaders = okHttpGetRequest.headers.size == 0
            val hasNullBody = okHttpGetRequest.body == null
            isGet && urlIsCorrect && hasEmptyHeaders && hasNullBody
        }
    }
}