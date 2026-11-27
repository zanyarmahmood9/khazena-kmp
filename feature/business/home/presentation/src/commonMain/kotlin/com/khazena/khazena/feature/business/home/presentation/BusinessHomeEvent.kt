package com.khazena.khazena.feature.business.home.presentation

import com.khazena.khazena.core.presentation.UiText

sealed interface BusinessHomeEvent {
    data class ShowMessage(val message: UiText) : BusinessHomeEvent
}
