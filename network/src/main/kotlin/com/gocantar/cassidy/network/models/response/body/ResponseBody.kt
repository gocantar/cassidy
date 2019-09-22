package com.gocantar.cassidy.network.models.response.body

import com.gocantar.cassidy.network.alias.OkHttpResponseBody

/**
 * @author Gonzalo Cantarero Pérez
 */
interface Body {
    val string: String
    val bytes: ByteArray
}

data class ResponseBody(private val body: OkHttpResponseBody?) : Body {
    override val string: String
        get() = body?.string() ?: ""

    override val bytes: ByteArray
        get() = body?.bytes() ?: ByteArray(0)
}