package com.khazena.khazena.feature.business.reports.presentation

import com.khazena.khazena.core.presentation.UiText

sealed interface ReportsEvent {
    data class ShowMessage(val message: UiText) : ReportsEvent
}
