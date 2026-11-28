package com.khazena.khazena.feature.onboarding.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingGraphRoute

@Serializable
data object SplashRoute

@Serializable
data object IntroRoute

fun NavGraphBuilder.onboardingGraph(
    navController: NavController,
    onFinished: () -> Unit,
) {
    navigation<OnboardingGraphRoute>(startDestination = SplashRoute) {
        composable<SplashRoute>(
            exitTransition = { fadeOut(tween(durationMillis = 450)) + scaleOut(tween(durationMillis = 450), targetScale = 1.04f) },
        ) {
            SplashRoot(
                onFinished = {
                    navController.navigate(IntroRoute) { popUpTo(SplashRoute) { inclusive = true } }
                },
            )
        }
        composable<IntroRoute>(
            enterTransition = {
                fadeIn(tween(durationMillis = 600, delayMillis = 150)) +
                    scaleIn(tween(durationMillis = 600, delayMillis = 150, easing = FastOutSlowInEasing), initialScale = 0.97f)
            },
        ) {
            IntroRoot(onFinished = onFinished)
        }
    }
}
