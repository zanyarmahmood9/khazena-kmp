package com.khazena.khazena.feature.business.profile.presentation

import com.khazena.khazena.core.presentation.UiText

sealed interface StoreEvent {
    data class ShowMessage(val message: UiText) : StoreEvent
}
