package com.khazena.khazena.core.data.network

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SafeCallTest {

    @Test
    fun successIsWrapped() = runTest {
        assertEquals(Result.Success(1), safeCall { 1 })
    }

    @Test
    fun serializationFailureMapsToSerialization() = runTest {
        assertEquals(Result.Error(DataError.Network.SERIALIZATION), safeCall { throw SerializationException("bad") })
    }

    @Test
    fun unexpectedFailureMapsToUnknown() = runTest {
        assertEquals(Result.Error(DataError.Network.UNKNOWN), safeCall { error("boom") })
    }

    @Test
    fun cancellationIsRethrown() = runTest {
        assertFailsWith<CancellationException> { safeCall { throw CancellationException("cancelled") } }
    }

    @Test
    fun statusCodesMap() {
        assertEquals(DataError.Network.UNAUTHORIZED, statusCodeToNetworkError(401))
        assertEquals(DataError.Network.SERVICE_UNAVAILABLE, statusCodeToNetworkError(503))
        assertEquals(DataError.Network.SERVER_ERROR, statusCodeToNetworkError(500))
        assertEquals(DataError.Network.UNKNOWN, statusCodeToNetworkError(418))
    }
}
