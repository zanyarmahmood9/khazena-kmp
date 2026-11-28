package com.khazena.khazena.feature.business.reports.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ReportsRoute

fun NavGraphBuilder.reportsGraph() {
    composable<ReportsRoute> { ReportsRoot() }
}
