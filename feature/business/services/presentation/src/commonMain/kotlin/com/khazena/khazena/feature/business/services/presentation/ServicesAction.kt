package com.khazena.khazena.feature.business.services.presentation

sealed interface ServicesAction {
    data object OnRetry : ServicesAction
    data object OnRefresh : ServicesAction
}
