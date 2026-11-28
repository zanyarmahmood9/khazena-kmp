package com.khazena.khazena.feature.profile.presentation

import com.khazena.khazena.core.presentation.UiText

sealed interface ProfileEvent {
    data class ShowMessage(val message: UiText) : ProfileEvent
}
