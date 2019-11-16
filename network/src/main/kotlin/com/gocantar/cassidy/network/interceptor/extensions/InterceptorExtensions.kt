package com.gocantar.cassidy.network.interceptor.extensions

import com.gocantar.cassidy.network.interceptor.Interceptor
import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.model.response.NetworkResponse

/**
 * @author Gonzalo Cantarero Pérez
 */

internal fun List<Interceptor>.process(request: NetworkRequest): NetworkRequest {
    var networkRequest = request
    forEach { networkRequest = it.onRequest(networkRequest) }
    return networkRequest
}

internal fun List<Interceptor>.process(response: NetworkResponse): NetworkResponse {
    var networkResponse = response
    forEach { networkResponse = it.onResponse(networkResponse) }
    return networkResponse
}