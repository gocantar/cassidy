package com.gocantar.cassidy.network.models.request.body

/**
 * @author Gonzalo Cantarero Pérez
 */

class RequestBody(
    val data: ByteArray,
    val contentType: String = "application/json"
)