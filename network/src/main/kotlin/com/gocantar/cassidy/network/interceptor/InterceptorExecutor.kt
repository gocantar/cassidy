package com.gocantar.cassidy.network.interceptor

interface InterceptorExecutor {
    val interceptors: MutableList<Interceptor>
    fun addInterceptor(interceptor: Interceptor)
}