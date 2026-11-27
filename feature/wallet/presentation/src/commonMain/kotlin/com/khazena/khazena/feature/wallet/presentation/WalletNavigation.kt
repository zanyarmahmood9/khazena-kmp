package com.khazena.khazena.feature.wallet.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object WalletRoute

fun NavGraphBuilder.walletGraph() {
    composable<WalletRoute> { WalletRoot() }
}
