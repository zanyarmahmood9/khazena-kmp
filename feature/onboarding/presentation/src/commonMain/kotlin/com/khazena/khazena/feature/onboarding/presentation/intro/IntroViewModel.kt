package com.khazena.khazena.feature.onboarding.presentation.intro

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val PAGE_KEY = "page"

class IntroViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val eventChannel = Channel<IntroEvent>()
    val events = eventChannel.receiveAsFlow()

    val state = savedStateHandle.getStateFlow(PAGE_KEY, 0)
        .map { IntroState(page = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), IntroState(page = savedStateHandle[PAGE_KEY] ?: 0))

    fun onAction(action: IntroAction) {
        when (action) {
            is IntroAction.OnPageSettled -> savedStateHandle[PAGE_KEY] = action.page
            IntroAction.OnNextClick -> {
                val page = state.value.page
                if (page >= INTRO_PAGE_COUNT - 1) finish() else savedStateHandle[PAGE_KEY] = page + 1
            }
            IntroAction.OnSkipClick -> finish()
        }
    }

    private fun finish() {
        viewModelScope.launch { eventChannel.send(IntroEvent.NavigateToChooseSide) }
    }
}
