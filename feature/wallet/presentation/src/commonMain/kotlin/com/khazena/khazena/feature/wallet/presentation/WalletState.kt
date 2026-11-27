package com.khazena.khazena.feature.wallet.presentation

import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.wallet.domain.Wallet

data class WalletState(
    val content: ContentState<Wallet> = ContentState(),
)
