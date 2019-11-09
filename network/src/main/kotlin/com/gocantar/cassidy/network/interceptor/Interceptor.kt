package com.gocantar.cassidy.network.interceptor

import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.model.response.NetworkResponse

/**
 * @author Gonzalo Cantarero Pérez
 */

interface Interceptor {
    fun onRequest(request: NetworkRequest) : NetworkRequest
    fun onResponse(response: NetworkResponse) : NetworkResponse
}