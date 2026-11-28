package com.khazena.khazena.feature.business.profile.presentation

sealed interface StoreAction {
    data object OnRetry : StoreAction
    data object OnRefresh : StoreAction
}
