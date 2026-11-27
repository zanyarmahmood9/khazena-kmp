package com.khazena.khazena.feature.business.services.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ServicesRoute

fun NavGraphBuilder.servicesGraph() {
    composable<ServicesRoute> { ServicesRoot() }
}
