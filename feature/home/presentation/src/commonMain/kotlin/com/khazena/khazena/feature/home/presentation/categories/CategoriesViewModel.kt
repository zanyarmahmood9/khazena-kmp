package com.khazena.khazena.feature.home.presentation.categories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.home.domain.HomeRepository
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

private const val QUERY_KEY = "query"

class CategoriesViewModel(
    repository: HomeRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val eventChannel = Channel<CategoriesEvent>()
    val events = eventChannel.receiveAsFlow()

    private val query = savedStateHandle.getStateFlow(QUERY_KEY, "")

    private val directory = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it.categories.isEmpty() },
        onRefreshFailed = { eventChannel.send(CategoriesEvent.ShowMessage(it)) },
        fetch = repository::getCategories,
    )

    val state = combine(query, directory.state, ::CategoriesState)
        .onStart { directory.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), CategoriesState())

    fun onAction(action: CategoriesAction) {
        when (action) {
            is CategoriesAction.OnQueryChange -> savedStateHandle[QUERY_KEY] = action.query
            CategoriesAction.OnRetry -> directory.load()
            CategoriesAction.OnRefresh -> directory.refresh()
            else -> Unit
        }
    }
}
