package com.gocantar.cassidy.network.model.response.body

/**
 * @author Gonzalo Cantarero Pérez
 */

typealias OkHttpBody = okhttp3.ResponseBody

data class ResponseBody(private val body: OkHttpBody) {
    val string: String
        get() = body.string()

    val bytes: ByteArray
        get() = body.bytes()
}