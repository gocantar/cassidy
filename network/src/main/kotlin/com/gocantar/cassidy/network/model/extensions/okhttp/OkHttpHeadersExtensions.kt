package com.gocantar.cassidy.network.model.extensions.okhttp

import okhttp3.Headers

/**
 * @author Gonzalo Cantarero Pérez
 */

internal fun Headers.toMap(): Map<String, String?> {
    return names().map {
        it to get(it)
    }.toMap()
}