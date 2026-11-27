package com.khazena.khazena.feature.home.presentation

import com.khazena.khazena.core.presentation.UiText

sealed interface HomeEvent {
    data class ShowMessage(val message: UiText) : HomeEvent
}
