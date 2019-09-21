package com.gocantar.cassidy.network.model

import com.gocantar.cassidy.network.model.body.ResponseBody

/**
 * @author Gonzalo Cantarero Pérez
 */

data class NetworkResponse(
    val headers: Map<String, String?>,
    val code: Int,
    val body: ResponseBody,
    val request: NetworkRequest
)