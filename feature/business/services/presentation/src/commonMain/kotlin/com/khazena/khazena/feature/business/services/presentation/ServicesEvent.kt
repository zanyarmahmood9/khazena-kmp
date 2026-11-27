package com.khazena.khazena.feature.business.services.presentation

import com.khazena.khazena.core.presentation.UiText

sealed interface ServicesEvent {
    data class ShowMessage(val message: UiText) : ServicesEvent
}
