package com.khazena.khazena.feature.search.presentation

sealed interface SearchAction {
    data class OnQueryChange(val query: String) : SearchAction
    data object OnClearQuery : SearchAction
    data object OnRetry : SearchAction
    data object OnRefresh : SearchAction
}
