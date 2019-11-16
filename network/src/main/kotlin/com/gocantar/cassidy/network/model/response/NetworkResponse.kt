package com.gocantar.cassidy.network.model.response

import com.gocantar.cassidy.network.model.error.NetworkError
import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.model.response.body.Body

/**
 * @author Gonzalo Cantarero Pérez
 */

data class NetworkResponse(
    val code: Int,
    val headers: Map<String, String?>,
    val body: Body,
    val request: NetworkRequest
) {
    fun asError(): NetworkError.FailedResponse {
        return NetworkError.FailedResponse(code, this)
    }
}