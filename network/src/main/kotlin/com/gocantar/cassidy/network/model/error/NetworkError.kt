package com.gocantar.cassidy.network.model.error

import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.model.response.NetworkResponse

/**
 * @author Gonzalo Cantarero Pérez
 */

sealed class NetworkError {

    data class FailedResponse(
        val code: Int = -1,
        val response: NetworkResponse? = null
    ) : NetworkError()

    data class Connection(val request: NetworkRequest): NetworkError()

    data class SSLConnection(val request: NetworkRequest): NetworkError()
}