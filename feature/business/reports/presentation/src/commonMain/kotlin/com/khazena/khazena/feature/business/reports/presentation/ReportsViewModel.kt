package com.khazena.khazena.feature.business.reports.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.business.reports.domain.ReportsRepository
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ReportsViewModel(repository: ReportsRepository) : ViewModel() {

    private val eventChannel = Channel<ReportsEvent>()
    val events = eventChannel.receiveAsFlow()

    private val loader = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it.visitsByDay.sum() == 0 },
        onRefreshFailed = { eventChannel.send(ReportsEvent.ShowMessage(it)) },
        fetch = repository::getWeeklyReport,
    )

    val state = loader.state
        .map { ReportsState(content = it) }
        .onStart { loader.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), ReportsState())

    fun onAction(action: ReportsAction) {
        when (action) {
            ReportsAction.OnRetry -> loader.load()
            ReportsAction.OnRefresh -> loader.refresh()
        }
    }
}
