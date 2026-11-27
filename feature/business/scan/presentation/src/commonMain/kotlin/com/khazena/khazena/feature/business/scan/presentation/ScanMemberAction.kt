package com.khazena.khazena.feature.business.scan.presentation

sealed interface ScanMemberAction {
    data object OnRetry : ScanMemberAction
    data object OnRefresh : ScanMemberAction
    data object OnBackClick : ScanMemberAction
}
