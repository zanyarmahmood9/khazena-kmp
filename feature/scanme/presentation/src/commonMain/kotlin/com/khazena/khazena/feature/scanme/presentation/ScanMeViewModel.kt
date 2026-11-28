package com.khazena.khazena.feature.scanme.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.scanme.domain.ScanMeRepository
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ScanMeViewModel(repository: ScanMeRepository) : ViewModel() {

    private val eventChannel = Channel<ScanMeEvent>()
    val events = eventChannel.receiveAsFlow()

    private val loader = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it == null },
        onRefreshFailed = { eventChannel.send(ScanMeEvent.ShowMessage(it)) },
        fetch = repository::getMemberCode,
    )

    val state = loader.state
        .map { ScanMeState(content = it) }
        .onStart { loader.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), ScanMeState())

    fun onAction(action: ScanMeAction) {
        when (action) {
            ScanMeAction.OnRetry -> loader.load()
            ScanMeAction.OnRefresh -> loader.refresh()
            ScanMeAction.OnBackClick -> Unit
            ScanMeAction.OnCodeExpired -> loader.refresh()
        }
    }
}
