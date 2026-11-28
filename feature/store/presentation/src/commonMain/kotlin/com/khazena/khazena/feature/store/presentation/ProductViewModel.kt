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

class ProductViewModel(
    repository: StoreRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ProductRoute>()

    private val eventChannel = Channel<ProductEvent>()
    val events = eventChannel.receiveAsFlow()

    // shortcut: saved items live in memory until saved places has a repository.
    private val saved = MutableStateFlow(false)

    // The product comes with its store (name, logo, offer, the rest of the menu), so the page loads the store.
    private val store = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it.item(route.itemId) == null },
        onRefreshFailed = { eventChannel.send(ProductEvent.ShowMessage(it)) },
        fetch = { repository.getStore(route.storeId) },
    )

    val state = combine(store.state, saved) { store, saved -> ProductState(store, route.itemId, saved) }
        .onStart { store.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), ProductState(itemId = route.itemId))

    fun onAction(action: ProductAction) {
        when (action) {
            ProductAction.OnSaveClick -> {
                saved.value = !saved.value
                val message = if (saved.value) Res.string.store_saved_toast else Res.string.store_unsaved_toast
                viewModelScope.launch { eventChannel.send(ProductEvent.ShowMessage(UiText.Resource(message))) }
            }
            ProductAction.OnRetry -> store.load()
            ProductAction.OnRefresh -> store.refresh()
            else -> Unit
        }
    }
}
