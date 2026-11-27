package com.khazena.khazena.feature.scanme.presentation

sealed interface ScanMeAction {
    data object OnRetry : ScanMeAction
    data object OnRefresh : ScanMeAction
    data object OnBackClick : ScanMeAction
    data object OnCodeExpired : ScanMeAction
}
