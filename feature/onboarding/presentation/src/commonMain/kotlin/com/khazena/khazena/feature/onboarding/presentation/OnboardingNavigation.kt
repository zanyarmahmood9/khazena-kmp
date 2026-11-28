package com.khazena.khazena.feature.onboarding.presentation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
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
    resumeRoute: () -> Any?,
    onFinished: () -> Unit,
) {
    navigation<OnboardingGraphRoute>(startDestination = SplashRoute) {
        composable<SplashRoute>(
            // The splash has already faded its logo and text out (see SplashRoot), so only flat orange dissolves here.
            exitTransition = { fadeOut(tween(durationMillis = 420, easing = FastOutSlowInEasing)) },
        ) {
            SplashRoot(
                onFinished = {
                    // Returning users skip the intro and go to where they left off.
                    val resume = resumeRoute()
                    if (resume == null) {
                        navController.navigate(IntroRoute) { popUpTo(SplashRoute) { inclusive = true } }
                    } else {
                        navController.navigate(resume) { popUpTo<OnboardingGraphRoute> { inclusive = true } }
                    }
                },
            )
        }
        composable<IntroRoute>(
            enterTransition = {
                fadeIn(tween(durationMillis = 480, delayMillis = 60, easing = FastOutSlowInEasing)) +
                    scaleIn(tween(durationMillis = 620, easing = FastOutSlowInEasing), initialScale = 0.96f)
            },
            // Leaving onboarding continues the pager's motion: the last page pushes off towards the start.
            exitTransition = { slideOutOfContainer(SlideDirection.Start, tween(durationMillis = 450, easing = FastOutSlowInEasing)) },
        ) {
            IntroRoot(onFinished = onFinished)
        }
    }
}
