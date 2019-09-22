package com.gocantar.cassidy.network.model.request

import com.gocantar.cassidy.network.constants.Method
import com.gocantar.cassidy.network.model.request.body.RequestBody

/**
 * @author Gonzalo Cantarero Pérez
 */

data class NetworkRequest(
    val method: Method,
    val url: String,
    val headers: Map<String, String?>,
    val body: RequestBody
)