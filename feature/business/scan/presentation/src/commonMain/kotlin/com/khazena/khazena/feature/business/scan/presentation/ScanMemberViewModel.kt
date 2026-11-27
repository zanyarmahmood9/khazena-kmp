package com.khazena.khazena.feature.business.scan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.business.scan.domain.ScanMemberRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ScanMemberViewModel(repository: ScanMemberRepository) : ViewModel() {

    private val eventChannel = Channel<ScanMemberEvent>()
    val events = eventChannel.receiveAsFlow()

    private val loader = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it.isEmpty() },
        onRefreshFailed = { eventChannel.send(ScanMemberEvent.ShowMessage(it)) },
        fetch = repository::getRecentScans,
    )

    val state = loader.state
        .map { ScanMemberState(content = it) }
        .onStart { loader.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ScanMemberState())

    fun onAction(action: ScanMemberAction) {
        when (action) {
            ScanMemberAction.OnRetry -> loader.load()
            ScanMemberAction.OnRefresh -> loader.refresh()
            ScanMemberAction.OnBackClick -> Unit
        }
    }
}
