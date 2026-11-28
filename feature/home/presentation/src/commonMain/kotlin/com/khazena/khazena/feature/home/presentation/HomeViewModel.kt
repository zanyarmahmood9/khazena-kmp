package com.khazena.khazena.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.home.domain.HomeRepository
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    repository: HomeRepository,
    private val hourOfDay: () -> Int = { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour },
) : ViewModel() {

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

    private val feed = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it.isEmpty },
        onRefreshFailed = { eventChannel.send(HomeEvent.ShowMessage(it)) },
        fetch = repository::getHomeFeed,
    )

    val state = feed.state
        .map { HomeState(feed = it, daypart = daypartAt(hourOfDay())) }
        .onStart { feed.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), HomeState(daypart = daypartAt(hourOfDay())))

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnRetry -> feed.load()
            HomeAction.OnRefresh -> feed.refresh()
            else -> Unit
        }
    }
}
