package com.gocantar.cassidy.network.model.extensions

import okhttp3.Headers.Companion.toHeaders
import okhttp3.Headers

/**
 * @author Gonzalo Cantarero Pérez
 */

internal fun Map<String, String>.okHttpHeaders(): Headers {
    return toHeaders()
}