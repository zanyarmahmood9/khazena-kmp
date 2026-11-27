package com.khazena.khazena.feature.search.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.search.domain.SearchRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

private const val QUERY_KEY = "query"
private const val SEARCH_DEBOUNCE_MS = 350L

@OptIn(FlowPreview::class)
class SearchViewModel(
    repository: SearchRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val eventChannel = Channel<SearchEvent>()
    val events = eventChannel.receiveAsFlow()

    private val query = savedStateHandle.getStateFlow(QUERY_KEY, "")

    private val results = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it.isEmpty() },
        onRefreshFailed = { eventChannel.send(SearchEvent.ShowMessage(it)) },
        fetch = { repository.search(query.value) },
    )

    init {
        query
            .map { it.trim() }
            .distinctUntilChanged()
            .drop(1)
            .debounce(SEARCH_DEBOUNCE_MS)
            .onEach { results.load() }
            .launchIn(viewModelScope)
    }

    val state = combine(query, results.state, ::SearchState)
        .onStart { results.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SearchState())

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnQueryChange -> savedStateHandle[QUERY_KEY] = action.query
            SearchAction.OnClearQuery -> savedStateHandle[QUERY_KEY] = ""
            SearchAction.OnRetry -> results.load()
            SearchAction.OnRefresh -> results.refresh()
        }
    }
}
