package com.gocantar.cassidy.network.extensions

import com.gocantar.cassidy.network.manager.NetworkExecutor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import java.net.CookieManager
import java.net.CookiePolicy

/**
 * @author Gonzalo Cantarero Pérez
 */

internal fun NetworkExecutor.defaultOkHttpClient(): OkHttpClient {
    val cookieManager = CookieManager(null, CookiePolicy.ACCEPT_ALL)
    return OkHttpClient.Builder().apply {
        cookieJar(JavaNetCookieJar(cookieManager))
    }.build()
}