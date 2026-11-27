package com.khazena.khazena.feature.business.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.business.profile.domain.StoreRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class StoreViewModel(repository: StoreRepository) : ViewModel() {

    private val eventChannel = Channel<StoreEvent>()
    val events = eventChannel.receiveAsFlow()

    private val loader = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it == null },
        onRefreshFailed = { eventChannel.send(StoreEvent.ShowMessage(it)) },
        fetch = repository::getStore,
    )

    val state = loader.state
        .map { StoreState(content = it) }
        .onStart { loader.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), StoreState())

    fun onAction(action: StoreAction) {
        when (action) {
            StoreAction.OnRetry -> loader.load()
            StoreAction.OnRefresh -> loader.refresh()
        }
    }
}
