package com.khazena.khazena.feature.business.reports.presentation

sealed interface ReportsAction {
    data object OnRetry : ReportsAction
    data object OnRefresh : ReportsAction
}
