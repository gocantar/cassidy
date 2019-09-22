package com.gocantar.cassidy.network.model.response

import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.model.response.body.ResponseBody

/**
 * @author Gonzalo Cantarero Pérez
 */

data class NetworkResponse(
    val headers: Map<String, String?>,
    val code: Int,
    val body: ResponseBody,
    val request: NetworkRequest
)