package com.khazena.khazena.feature.onboarding.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val SPLASH_DURATION_MS = 1_200L

sealed interface SplashEvent {
    data object NavigateNext : SplashEvent
}

class SplashViewModel : ViewModel() {
    private val eventChannel = Channel<SplashEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(SPLASH_DURATION_MS)
            eventChannel.send(SplashEvent.NavigateNext)
        }
    }
}
