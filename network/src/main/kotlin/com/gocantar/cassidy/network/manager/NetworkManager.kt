package com.gocantar.cassidy.network.manager

import com.gocantar.cassidy.network.alias.NetworkCallback
import com.gocantar.cassidy.network.alias.NetworkResult
import com.gocantar.cassidy.network.alias.OkHttpRequest
import com.gocantar.cassidy.network.alias.OkHttpResponse
import com.gocantar.cassidy.network.extensions.asOkHttpRequest
import com.gocantar.cassidy.network.extensions.okHttpClient
import com.gocantar.cassidy.network.extensions.okhttp.asNetworkError
import com.gocantar.cassidy.network.extensions.okhttp.asNetworkResponse
import com.gocantar.cassidy.network.models.error.NetworkError
import com.gocantar.cassidy.network.models.request.NetworkRequest
import com.gocantar.cassidy.tools.functional.Either
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import java.io.IOException
import javax.net.ssl.SSLException

/**
 * @author Gonzalo Cantarero Pérez
 */

class NetworkManager(private val client: OkHttpClient = okHttpClient()) : NetworkExecutor {

    override fun execute(request: NetworkRequest): NetworkResult {
        val clientRequest = request.asOkHttpRequest()
        return clientRequest.executeSync(request)
    }

    override fun enqueue(request: NetworkRequest, callback: NetworkCallback) {
        val clientRequest = request.asOkHttpRequest()
        clientRequest.executeAsync(callback, request)
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
                callback(handleResponse(response, request))
            }
        })
    }

    private fun handleResponse(response: OkHttpResponse, request: NetworkRequest): NetworkResult {

        return when (response.isSuccessful) {
            true -> Either.right(response.asNetworkResponse(request))
            false -> Either.left(response.asNetworkError(request))
        }
    }
}