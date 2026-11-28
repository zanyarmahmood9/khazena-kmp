package com.khazena.khazena.feature.scanme.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ScanMeRoute

fun NavGraphBuilder.scanMeGraph(onBack: () -> Unit) {
    composable<ScanMeRoute> { ScanMeRoot(onBack) }
}
