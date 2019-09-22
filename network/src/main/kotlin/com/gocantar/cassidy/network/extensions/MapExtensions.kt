package com.gocantar.cassidy.network.extensions

import okhttp3.Headers.Companion.toHeaders
import okhttp3.Headers

/**
 * @author Gonzalo Cantarero Pérez
 */

fun Map<String, String>.okHttpHeaders(): Headers {
    return toHeaders()
}