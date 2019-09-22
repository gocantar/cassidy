package com.gocantar.cassidy.network.model.error

import com.gocantar.cassidy.network.model.response.NetworkResponse

/**
 * @author Gonzalo Cantarero Pérez
 */

sealed class NetworkError {
    data class NetworkResponseError(val code: Int, val response: NetworkResponse) : NetworkError()
}