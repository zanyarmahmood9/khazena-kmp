package com.khazena.khazena.feature.business.home.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object BusinessHomeRoute

fun NavGraphBuilder.businessHomeGraph(onSwitchAccount: () -> Unit, onScanMember: () -> Unit) {
    composable<BusinessHomeRoute> { BusinessHomeRoot(onSwitchAccount, onScanMember) }
}
