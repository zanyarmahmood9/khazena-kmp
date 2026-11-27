package com.khazena.khazena.feature.search.presentation

import com.khazena.khazena.core.presentation.UiText

sealed interface SearchEvent {
    data class ShowMessage(val message: UiText) : SearchEvent
}
