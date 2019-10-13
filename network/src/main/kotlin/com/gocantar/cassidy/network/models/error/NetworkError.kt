package com.gocantar.cassidy.network.models.error

import com.gocantar.cassidy.network.models.request.NetworkRequest
import com.gocantar.cassidy.network.models.response.NetworkResponse

/**
 * @author Gonzalo Cantarero Pérez
 */

sealed class NetworkError {

    data class ServerResponse(
        val code: Int = -1,
        val response: NetworkResponse? = null
    ) : NetworkError()

    data class Connection(val request: NetworkRequest): NetworkError()

    data class SSLConnection(val request: NetworkRequest): NetworkError()
}