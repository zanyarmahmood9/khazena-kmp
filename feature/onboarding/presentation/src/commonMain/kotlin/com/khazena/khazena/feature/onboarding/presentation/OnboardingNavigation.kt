package com.khazena.khazena.feature.onboarding.presentation

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

@Serializable
data object ChooseSideRoute

fun NavGraphBuilder.onboardingGraph(
    navController: NavController,
    onContinueAsCustomer: () -> Unit,
    onContinueAsBusiness: () -> Unit,
    onSignIn: () -> Unit,
) {
    navigation<OnboardingGraphRoute>(startDestination = SplashRoute) {
        composable<SplashRoute> {
            SplashRoot(
                onFinished = {
                    navController.navigate(IntroRoute) { popUpTo(SplashRoute) { inclusive = true } }
                },
            )
        }
        composable<IntroRoute> {
            IntroRoot(onDone = { navController.navigate(ChooseSideRoute) { launchSingleTop = true } })
        }
        composable<ChooseSideRoute> {
            ChooseSideRoot(
                onContinueAsCustomer = onContinueAsCustomer,
                onContinueAsBusiness = onContinueAsBusiness,
                onSignIn = onSignIn,
            )
        }
    }
}
