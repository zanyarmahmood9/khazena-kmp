package com.khazena.khazena.core.presentation.state

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.presentation.UiText
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ContentLoaderTest {

    private var result: Result<List<String>, DataError> = Result.Success(listOf("a"))
    private var gate: CompletableDeferred<Unit>? = null
    private val messages = mutableListOf<UiText>()

    private fun TestScope.loader() = ContentLoader<List<String>>(
        scope = backgroundScope,
        isEmpty = { it.isEmpty() },
        onRefreshFailed = { messages += it },
        fetch = { gate?.await(); result },
    )

    @Test
    fun startsLoadingThenShowsData() = runTest(UnconfinedTestDispatcher()) {
        gate = CompletableDeferred()
        val loader = loader()
        loader.loadOnce()
        assertEquals(LoadState.Loading, loader.state.value.content)
        gate!!.complete(Unit)
        assertEquals(LoadState.Data(listOf("a")), loader.state.value.content)
    }

    @Test
    fun emptyResultIsEmptyState() = runTest(UnconfinedTestDispatcher()) {
        result = Result.Success(emptyList())
        val loader = loader()
        loader.loadOnce()
        assertEquals(LoadState.Empty, loader.state.value.content)
    }

    @Test
    fun failureIsErrorStateAndRetryRecovers() = runTest(UnconfinedTestDispatcher()) {
        result = Result.Error(DataError.Network.NO_INTERNET)
        val loader = loader()
        loader.loadOnce()
        assertIs<LoadState.Error>(loader.state.value.content)

        result = Result.Success(listOf("b"))
        loader.load()
        assertEquals(LoadState.Data(listOf("b")), loader.state.value.content)
    }

    @Test
    fun loadOnceDoesNotReloadExistingData() = runTest(UnconfinedTestDispatcher()) {
        val loader = loader()
        loader.loadOnce()
        result = Result.Success(listOf("changed"))
        loader.loadOnce()
        assertEquals(LoadState.Data(listOf("a")), loader.state.value.content)
    }

    @Test
    fun refreshKeepsDataVisibleWhileRefreshing() = runTest(UnconfinedTestDispatcher()) {
        val loader = loader()
        loader.loadOnce()
        gate = CompletableDeferred()
        result = Result.Success(listOf("new"))
        loader.refresh()
        assertTrue(loader.state.value.isRefreshing)
        assertEquals(LoadState.Data(listOf("a")), loader.state.value.content)
        gate!!.complete(Unit)
        assertEquals(LoadState.Data(listOf("new")), loader.state.value.content)
        assertEquals(false, loader.state.value.isRefreshing)
    }

    @Test
    fun failedRefreshKeepsDataAndReportsMessage() = runTest(UnconfinedTestDispatcher()) {
        val loader = loader()
        loader.loadOnce()
        result = Result.Error(DataError.Network.SERVER_ERROR)
        loader.refresh()
        assertEquals(LoadState.Data(listOf("a")), loader.state.value.content)
        assertEquals(1, messages.size)
    }

    @Test
    fun refreshWithoutDataLoadsFromScratch() = runTest(UnconfinedTestDispatcher()) {
        result = Result.Error(DataError.Network.NO_INTERNET)
        val loader = loader()
        loader.loadOnce()
        result = Result.Success(listOf("a"))
        loader.refresh()
        assertEquals(LoadState.Data(listOf("a")), loader.state.value.content)
        assertTrue(messages.isEmpty())
    }
}
