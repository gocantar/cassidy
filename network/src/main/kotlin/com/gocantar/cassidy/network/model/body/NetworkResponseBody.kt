package com.gocantar.cassidy.network.model.body

/**
 * @author Gonzalo Cantarero Pérez
 */

typealias OkHttpBody = okhttp3.ResponseBody

class NetworkResponseBody(private val body: OkHttpBody): ResponseBody {

    override val string: String
        get() = body.string()

    override val bytes: ByteArray
        get() = body.bytes()
}