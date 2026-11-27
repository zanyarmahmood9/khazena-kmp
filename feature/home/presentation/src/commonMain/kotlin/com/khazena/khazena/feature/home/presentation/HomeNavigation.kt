package com.khazena.khazena.feature.home.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeGraph(onSwitchAccount: () -> Unit) {
    composable<HomeRoute> { HomeRoot(onSwitchAccount) }
}
