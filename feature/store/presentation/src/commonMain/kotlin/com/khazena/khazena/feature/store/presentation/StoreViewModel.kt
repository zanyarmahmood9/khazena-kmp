package com.khazena.khazena.feature.store.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.khazena.khazena.core.presentation.UiText
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.store.domain.StoreRepository
import khazena.feature.store.presentation.generated.resources.Res
import khazena.feature.store.presentation.generated.resources.store_saved_toast
import khazena.feature.store.presentation.generated.resources.store_unsaved_toast
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

private const val TAB_KEY = "tab"
private const val QUERY_KEY = "menuQuery"
private const val FILTER_KEY = "menuFilter"
private const val REVIEW_SORT_KEY = "reviewSort"

class StoreViewModel(
    repository: StoreRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val storeId = savedStateHandle.toRoute<StoreDetailRoute>().id

    private val eventChannel = Channel<StoreEvent>()
    val events = eventChannel.receiveAsFlow()

    private val tab = savedStateHandle.getStateFlow(TAB_KEY, StoreTab.Services.name)
    private val query = savedStateHandle.getStateFlow(QUERY_KEY, "")
    // "" = All, "*" = Popular, otherwise a menu section id.
    private val filter = savedStateHandle.getStateFlow(FILTER_KEY, "")
    private val reviewSort = savedStateHandle.getStateFlow(REVIEW_SORT_KEY, ReviewSort.Recent.name)
    // shortcut: saved places live in memory until the saved-places feature has a repository.
    private val saved = MutableStateFlow(false)

    private val store = ContentLoader(
        scope = viewModelScope,
        isEmpty = { false },
        onRefreshFailed = { eventChannel.send(StoreEvent.ShowMessage(it)) },
        fetch = { repository.getStore(storeId) },
    )

    val state = combine(
        combine(store.state, tab, query, ::Triple),
        filter,
        reviewSort,
        saved,
    ) { (store, tab, query), filter, sort, saved ->
        StoreState(store, StoreTab.valueOf(tab), query, filter.toMenuFilter(), ReviewSort.valueOf(sort), saved)
    }
        .onStart { store.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), StoreState())

    fun onAction(action: StoreAction) {
        when (action) {
            is StoreAction.OnTabSelect -> savedStateHandle[TAB_KEY] = action.tab.name
            StoreAction.OnSeeContactClick -> savedStateHandle[TAB_KEY] = StoreTab.Info.name
            is StoreAction.OnMenuQueryChange -> savedStateHandle[QUERY_KEY] = action.query
            is StoreAction.OnMenuFilterClick -> savedStateHandle[FILTER_KEY] = action.filter.toKey()
            is StoreAction.OnReviewSortClick -> savedStateHandle[REVIEW_SORT_KEY] = action.sort.name
            StoreAction.OnSaveClick -> {
                saved.value = !saved.value
                val message = if (saved.value) Res.string.store_saved_toast else Res.string.store_unsaved_toast
                viewModelScope.launch { eventChannel.send(StoreEvent.ShowMessage(UiText.Resource(message))) }
            }
            StoreAction.OnRetry -> store.load()
            StoreAction.OnRefresh -> store.refresh()
            else -> Unit
        }
    }
}

private fun String.toMenuFilter(): MenuFilter = when (this) {
    "" -> MenuFilter.All
    "*" -> MenuFilter.Popular
    else -> MenuFilter.Section(this)
}

private fun MenuFilter.toKey(): String = when (this) {
    MenuFilter.All -> ""
    MenuFilter.Popular -> "*"
    is MenuFilter.Section -> id
}
