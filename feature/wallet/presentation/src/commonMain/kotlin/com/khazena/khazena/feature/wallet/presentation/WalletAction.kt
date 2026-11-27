package com.khazena.khazena.feature.wallet.presentation

sealed interface WalletAction {
    data object OnRetry : WalletAction
    data object OnRefresh : WalletAction
}
