package com.gocantar.cassidy.network.models.request

import com.gocantar.cassidy.network.constants.Method
import com.gocantar.cassidy.network.models.request.body.RequestBody

/**
 * @author Gonzalo Cantarero Pérez
 */

data class NetworkRequest(
    val method: Method,
    var url: String,
    var headers: Map<String, String>,
    var body: RequestBody? = null
)