package com.khazena.khazena.feature.chooseside.presentation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
    composable<ChooseSideRoute>(
        enterTransition = { slideIntoContainer(SlideDirection.Start, tween(durationMillis = 450, easing = FastOutSlowInEasing)) },
        // Coming back (e.g. from sign-in) keeps the default fade instead of re-running the slide.
        popEnterTransition = { fadeIn(tween(durationMillis = 300)) },
    ) {
        ChooseSideRoot(onContinueAsCustomer, onContinueAsBusiness, onSignIn)
    }
}
