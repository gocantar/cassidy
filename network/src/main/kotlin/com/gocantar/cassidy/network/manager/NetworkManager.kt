package com.gocantar.cassidy.network.manager

import com.gocantar.cassidy.network.alias.NetworkCallback
import com.gocantar.cassidy.network.alias.NetworkResult
import com.gocantar.cassidy.network.alias.OkHttpRequest
import com.gocantar.cassidy.network.alias.OkHttpResponse
import com.gocantar.cassidy.network.interceptor.Interceptor
import com.gocantar.cassidy.network.interceptor.InterceptorExecutor
import com.gocantar.cassidy.network.interceptor.extensions.process
import com.gocantar.cassidy.network.model.error.NetworkError
import com.gocantar.cassidy.network.model.extensions.asOkHttpRequest
import com.gocantar.cassidy.network.model.extensions.defaultOkHttpClient
import com.gocantar.cassidy.network.model.extensions.okhttp.asNetworkResponse
import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.tools.functional.Either
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import java.io.IOException
import javax.net.ssl.SSLException

/**
 * @author Gonzalo Cantarero Pérez
 */

class NetworkManager(networkClient: OkHttpClient? = null)
    : NetworkExecutor, InterceptorExecutor {

    private val client: OkHttpClient = networkClient ?: defaultOkHttpClient()

    override val interceptors: MutableList<Interceptor> = mutableListOf()

    override fun execute(request: NetworkRequest): NetworkResult {
        val networkRequest = interceptors.process(request = request)
        val clientRequest = networkRequest.asOkHttpRequest()
        return clientRequest.executeSync(networkRequest)
    }

    override fun enqueue(request: NetworkRequest, callback: NetworkCallback) {
        val networkRequest = interceptors.process(request = request)
        val clientRequest = networkRequest.asOkHttpRequest()
        clientRequest.executeAsync(callback, networkRequest)
    }

    private fun OkHttpRequest.executeSync(request: NetworkRequest): NetworkResult {
        return try {
            val clientResponse = client.newCall(this).execute()
            handleResponse(clientResponse, request)
        } catch (sslException: SSLException) {
            Either.left(NetworkError.SSLConnection(request))
        } catch (exception: IOException) {
            Either.left(NetworkError.Connection(request))
        }
    }

    private fun OkHttpRequest.executeAsync(callback: NetworkCallback, request: NetworkRequest) {
        client.newCall(this).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val networkResult = when (e) {
                    is SSLException -> Either.left(NetworkError.SSLConnection(request))
                    else -> Either.left(NetworkError.Connection(request))
                }
                callback(networkResult)
            }

            override fun onResponse(call: Call, response: OkHttpResponse) {
                val networkResponse = handleResponse(response, request)
                callback(networkResponse)
            }
        })
    }

    private fun handleResponse(response: OkHttpResponse, request: NetworkRequest): NetworkResult {
        val networkResponse = interceptors.process(response = response.asNetworkResponse(request))
        return when (response.isSuccessful) {
            true -> Either.right(networkResponse)
            false -> Either.left(networkResponse.asError())
        }
    }

    override fun addInterceptor(interceptor: Interceptor) {
        interceptors.add(interceptor)
    }
}