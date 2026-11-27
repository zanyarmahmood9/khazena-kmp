package com.khazena.khazena.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.home.domain.HomeRepository
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(repository: HomeRepository) : ViewModel() {

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

    private val deals = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it.isEmpty() },
        onRefreshFailed = { eventChannel.send(HomeEvent.ShowMessage(it)) },
        fetch = repository::getNearbyDeals,
    )

    val state = deals.state
        .map { HomeState(deals = it) }
        .onStart { deals.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), HomeState())

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnRetry -> deals.load()
            HomeAction.OnRefresh -> deals.refresh()
            HomeAction.OnSwitchAccountClick -> Unit
        }
    }
}
