package com.khazena.khazena.feature.business.scan.presentation

import com.khazena.khazena.core.presentation.UiText

sealed interface ScanMemberEvent {
    data class ShowMessage(val message: UiText) : ScanMemberEvent
}
