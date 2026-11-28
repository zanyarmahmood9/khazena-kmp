package com.khazena.khazena.feature.home.presentation

sealed interface HomeAction {
    data object OnRetry : HomeAction
    data object OnRefresh : HomeAction
    data object OnSwitchAccountClick : HomeAction
    data object OnSearchClick : HomeAction
    data object OnScanToSaveClick : HomeAction
    data object OnWalletClick : HomeAction
    data object OnTurnOnLocationClick : HomeAction
    data object OnSeeAllCategoriesClick : HomeAction
    data class OnCategoryClick(val id: String) : HomeAction
    data class OnStoreClick(val id: String) : HomeAction
}
