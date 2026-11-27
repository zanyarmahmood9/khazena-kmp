package com.khazena.khazena.core.presentation.state

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.presentation.UiText
import khazena.core.presentation.generated.resources.Res
import khazena.core.presentation.generated.resources.refresh_failed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ContentState<T>(
    val content: LoadState<T> = LoadState.Loading,
    val isRefreshing: Boolean = false,
)

class ContentLoader<T>(
    private val scope: CoroutineScope,
    private val isEmpty: (T) -> Boolean,
    private val onRefreshFailed: suspend (UiText) -> Unit,
    private val fetch: suspend () -> Result<T, DataError>,
) {
    private val _state = MutableStateFlow(ContentState<T>())
    val state: StateFlow<ContentState<T>> = _state.asStateFlow()
    private var job: Job? = null
    private var started = false

    fun loadOnce() {
        if (started) return
        started = true
        load()
    }

    fun load() {
        job?.cancel()
        _state.update { it.copy(content = LoadState.Loading, isRefreshing = false) }
        job = scope.launch {
            val result = fetch()
            _state.update { it.copy(content = result.toLoadState(isEmpty)) }
        }
    }

    fun refresh() {
        if (!_state.value.content.hasData) return load()
        job?.cancel()
        _state.update { it.copy(isRefreshing = true) }
        job = scope.launch {
            when (val result = fetch()) {
                is Result.Success -> _state.update { it.copy(content = result.toLoadState(isEmpty), isRefreshing = false) }
                is Result.Error -> {
                    _state.update { it.copy(isRefreshing = false) }
                    onRefreshFailed(UiText.Resource(Res.string.refresh_failed))
                }
            }
        }
    }
}
