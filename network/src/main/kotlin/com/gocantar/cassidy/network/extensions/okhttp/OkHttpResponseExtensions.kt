package com.gocantar.cassidy.network.extensions.okhttp

import com.gocantar.cassidy.network.alias.OkHttpResponse
import com.gocantar.cassidy.network.models.error.NetworkError
import com.gocantar.cassidy.network.models.request.NetworkRequest
import com.gocantar.cassidy.network.models.response.NetworkResponse
import com.gocantar.cassidy.network.models.response.body.ResponseBody

/**
 * @author Gonzalo Cantarero Pérez
 */

internal fun OkHttpResponse.asNetworkResponse(request: NetworkRequest): NetworkResponse {
    return NetworkResponse(
        code = code,
        headers = headers.toMap(),
        body = ResponseBody(body),
        request = request
    )
}

internal fun OkHttpResponse.asNetworkError(request: NetworkRequest): NetworkError {
    return NetworkError.FailedResponse(
        code = code,
        response = asNetworkResponse(request)
    )
}
