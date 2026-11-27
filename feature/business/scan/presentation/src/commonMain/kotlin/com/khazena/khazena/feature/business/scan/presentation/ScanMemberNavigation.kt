package com.khazena.khazena.feature.business.scan.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ScanMemberRoute

fun NavGraphBuilder.scanMemberGraph(onBack: () -> Unit) {
    composable<ScanMemberRoute> { ScanMemberRoot(onBack) }
}
