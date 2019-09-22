package com.gocantar.cassidy.network.extensions

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import java.net.CookieManager
import java.net.CookiePolicy

/**
 * @author Gonzalo Cantarero Pérez
 */

internal fun okHttpClient(): OkHttpClient {
    val cookieManager = CookieManager(null, CookiePolicy.ACCEPT_ALL)
    return OkHttpClient.Builder().apply {
        cookieJar(JavaNetCookieJar(cookieManager))
    }.build()
}