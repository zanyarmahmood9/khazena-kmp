package com.khazena.khazena.feature.scanme.presentation

import com.khazena.khazena.core.presentation.UiText

sealed interface ScanMeEvent {
    data class ShowMessage(val message: UiText) : ScanMeEvent
}
