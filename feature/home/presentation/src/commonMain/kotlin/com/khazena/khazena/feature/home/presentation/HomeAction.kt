package com.khazena.khazena.feature.home.presentation

sealed interface HomeAction {
    data object OnRetry : HomeAction
    data object OnRefresh : HomeAction
    data object OnSwitchAccountClick : HomeAction
}
