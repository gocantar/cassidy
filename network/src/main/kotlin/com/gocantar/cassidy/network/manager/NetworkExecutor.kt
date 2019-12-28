package com.gocantar.cassidy.network.manager

import com.gocantar.cassidy.network.model.error.NetworkError
import com.gocantar.cassidy.network.model.request.NetworkRequest
import com.gocantar.cassidy.network.model.response.NetworkResponse
import com.gocantar.cassidy.tools.either.Either

/**
 * @author Gonzalo Cantarero Pérez
 */

interface NetworkExecutor {
    fun execute(request: NetworkRequest): Either<NetworkError, NetworkResponse>
    fun enqueue(request: NetworkRequest, callback: (Either<NetworkError, NetworkResponse>) -> Unit)
}