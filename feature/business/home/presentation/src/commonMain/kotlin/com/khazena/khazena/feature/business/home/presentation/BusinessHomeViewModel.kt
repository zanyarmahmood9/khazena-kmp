package com.khazena.khazena.feature.business.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.business.home.domain.BusinessHomeRepository
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class BusinessHomeViewModel(repository: BusinessHomeRepository) : ViewModel() {

    private val eventChannel = Channel<BusinessHomeEvent>()
    val events = eventChannel.receiveAsFlow()

    private val loader = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it.recentScans.isEmpty() },
        onRefreshFailed = { eventChannel.send(BusinessHomeEvent.ShowMessage(it)) },
        fetch = repository::getDashboard,
    )

    val state = loader.state
        .map { BusinessHomeState(content = it) }
        .onStart { loader.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), BusinessHomeState())

    fun onAction(action: BusinessHomeAction) {
        when (action) {
            BusinessHomeAction.OnRetry -> loader.load()
            BusinessHomeAction.OnRefresh -> loader.refresh()
            BusinessHomeAction.OnSwitchAccountClick -> Unit
            BusinessHomeAction.OnScanMemberClick -> Unit
        }
    }
}
