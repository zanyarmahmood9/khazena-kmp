package com.khazena.khazena.feature.business.home.presentation

sealed interface BusinessHomeAction {
    data object OnRetry : BusinessHomeAction
    data object OnRefresh : BusinessHomeAction
    data object OnSwitchAccountClick : BusinessHomeAction
    data object OnScanMemberClick : BusinessHomeAction
}
