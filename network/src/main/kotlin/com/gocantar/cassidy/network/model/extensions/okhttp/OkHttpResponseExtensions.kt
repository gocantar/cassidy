package com.gocantar.cassidy.network.model.extensions.okhttp

import com.gocantar.cassidy.network.alias.OkHttpResponse
import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.model.response.NetworkResponse
import com.gocantar.cassidy.network.model.response.body.ResponseBody

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