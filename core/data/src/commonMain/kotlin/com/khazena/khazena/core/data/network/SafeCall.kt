package com.khazena.khazena.core.data.network

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result
import kotlinx.serialization.SerializationException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <T> safeCall(execute: suspend () -> T): Result<T, DataError.Network> {
    return try {
        Result.Success(execute())
    } catch (e: SerializationException) {
        Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Result.Error(DataError.Network.UNKNOWN)
    }
}

fun statusCodeToNetworkError(code: Int): DataError.Network = when (code) {
    400 -> DataError.Network.BAD_REQUEST
    401 -> DataError.Network.UNAUTHORIZED
    403 -> DataError.Network.FORBIDDEN
    404 -> DataError.Network.NOT_FOUND
    408 -> DataError.Network.REQUEST_TIMEOUT
    409 -> DataError.Network.CONFLICT
    413 -> DataError.Network.PAYLOAD_TOO_LARGE
    429 -> DataError.Network.TOO_MANY_REQUESTS
    503 -> DataError.Network.SERVICE_UNAVAILABLE
    in 500..599 -> DataError.Network.SERVER_ERROR
    else -> DataError.Network.UNKNOWN
}
