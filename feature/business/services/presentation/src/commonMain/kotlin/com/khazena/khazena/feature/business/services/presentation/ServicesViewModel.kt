package com.khazena.khazena.feature.business.services.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.business.services.domain.ServicesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ServicesViewModel(repository: ServicesRepository) : ViewModel() {

    private val eventChannel = Channel<ServicesEvent>()
    val events = eventChannel.receiveAsFlow()

    private val loader = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it.isEmpty() },
        onRefreshFailed = { eventChannel.send(ServicesEvent.ShowMessage(it)) },
        fetch = repository::getServices,
    )

    val state = loader.state
        .map { ServicesState(content = it) }
        .onStart { loader.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ServicesState())

    fun onAction(action: ServicesAction) {
        when (action) {
            ServicesAction.OnRetry -> loader.load()
            ServicesAction.OnRefresh -> loader.refresh()
        }
    }
}
