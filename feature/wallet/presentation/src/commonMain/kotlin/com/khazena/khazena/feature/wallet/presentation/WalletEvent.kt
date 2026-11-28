package com.khazena.khazena.feature.wallet.presentation

import com.khazena.khazena.core.presentation.UiText

sealed interface WalletEvent {
    data class ShowMessage(val message: UiText) : WalletEvent
}
