package com.gocantar.cassidy.network.models.response

import com.gocantar.cassidy.network.models.request.NetworkRequest
import com.gocantar.cassidy.network.models.response.body.Body
import com.gocantar.cassidy.network.models.response.body.ResponseBody

/**
 * @author Gonzalo Cantarero Pérez
 */

data class NetworkResponse(
    val code: Int,
    var headers: Map<String, String?>,
    var body: Body,
    val request: NetworkRequest
)