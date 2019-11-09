package com.gocantar.cassidy.network.model.extensions

import com.gocantar.cassidy.network.alias.OkHttpRequest
import com.gocantar.cassidy.network.alias.OkHttpRequestBody
import com.gocantar.cassidy.network.interceptor.Interceptor
import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.model.request.body.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @author Gonzalo Cantarero Pérez
 */

internal fun NetworkRequest.asOkHttpRequest(): OkHttpRequest {
    return Request.Builder().apply {
        url(url)
        method(method.name, body.okHttpRequestBody())
        headers(headers.okHttpHeaders())
    }.build()
}

private fun RequestBody?.okHttpRequestBody(): OkHttpRequestBody? {
    val body = this ?: return null
    val mediaType = body.contentType.toMediaTypeOrNull()
    val content = body.data
    return content.toRequestBody(mediaType)
}
