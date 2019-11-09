package com.gocantar.cassidy.network.alias

import com.gocantar.cassidy.network.model.error.NetworkError
import com.gocantar.cassidy.network.model.response.NetworkResponse
import com.gocantar.cassidy.tools.functional.Either
import okhttp3.Request

/**
 * @author Gonzalo Cantarero Pérez
 */

typealias NetworkResult = Either<NetworkError, NetworkResponse>

internal typealias OkHttpRequest = Request
internal typealias OkHttpRequestBody = okhttp3.RequestBody
internal typealias OkHttpResponse = okhttp3.Response
internal typealias OkHttpResponseBody = okhttp3.ResponseBody
internal typealias NetworkCallback = (NetworkResult) -> Unit