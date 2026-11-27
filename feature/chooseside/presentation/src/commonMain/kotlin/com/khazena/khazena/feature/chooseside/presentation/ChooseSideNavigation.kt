package com.khazena.khazena.feature.chooseside.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ChooseSideRoute

fun NavGraphBuilder.chooseSideGraph(
    onContinueAsCustomer: () -> Unit,
    onContinueAsBusiness: () -> Unit,
    onSignIn: () -> Unit,
) {
    composable<ChooseSideRoute> {
        ChooseSideRoot(onContinueAsCustomer, onContinueAsBusiness, onSignIn)
    }
}
