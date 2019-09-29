package com.gocantar.cassidy.network.manager

import com.gocantar.cassidy.network.models.error.NetworkError
import com.gocantar.cassidy.network.models.request.NetworkRequest
import com.gocantar.cassidy.network.models.response.NetworkResponse
import com.gocantar.cassidy.tools.funtional.models.Either

/**
 * @author Gonzalo Cantarero Pérez
 */

interface NetworkExecutor {
    fun execute(request: NetworkRequest): Either<NetworkError, NetworkResponse>
    fun enqueue(request: NetworkRequest, callback: (Either<NetworkError, NetworkResponse>) -> Unit)
}