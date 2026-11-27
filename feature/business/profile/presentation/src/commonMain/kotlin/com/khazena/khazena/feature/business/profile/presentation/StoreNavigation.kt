package com.khazena.khazena.feature.business.profile.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object StoreRoute

fun NavGraphBuilder.storeGraph() {
    composable<StoreRoute> { StoreRoot() }
}
