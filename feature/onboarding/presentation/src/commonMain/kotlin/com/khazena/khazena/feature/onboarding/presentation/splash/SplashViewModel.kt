package com.khazena.khazena.feature.onboarding.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private val SPLASH_DURATION = 2_000.milliseconds

sealed interface SplashEvent {
    data object NavigateNext : SplashEvent
}

class SplashViewModel : ViewModel() {
    private val eventChannel = Channel<SplashEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(SPLASH_DURATION)
            eventChannel.send(SplashEvent.NavigateNext)
        }
    }
}
