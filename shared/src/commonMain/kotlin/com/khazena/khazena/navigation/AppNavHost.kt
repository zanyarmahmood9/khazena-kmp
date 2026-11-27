package com.khazena.khazena.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.component.KhazenaComponentGallery
import com.khazena.khazena.core.designsystem.component.KhazenaIconButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaPlaceholder
import com.khazena.khazena.feature.business.scan.presentation.ScanMemberRoute
import com.khazena.khazena.feature.business.scan.presentation.scanMemberGraph
import com.khazena.khazena.feature.onboarding.presentation.OnboardingGraphRoute
import com.khazena.khazena.feature.onboarding.presentation.onboardingGraph
import com.khazena.khazena.feature.scanme.presentation.ScanMeRoute
import com.khazena.khazena.feature.scanme.presentation.scanMeGraph
import khazena.core.presentation.generated.resources.back
import khazena.core.presentation.generated.resources.coming_soon
import khazena.shared.generated.resources.Res
import khazena.shared.generated.resources.sign_in
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import khazena.core.presentation.generated.resources.Res as CoreRes

@Serializable
data object CustomerRoute

@Serializable
data object BusinessRoute

@Serializable
data object SignInRoute

@Serializable
data object GalleryRoute

@Composable
internal fun AppNavHost(navController: NavHostController, isSignedIn: Boolean, isDebug: Boolean) {
    var showAccountSheet by rememberSaveable { mutableStateOf(false) }
    val requireAccount: (() -> Unit) -> Unit = { action -> if (isSignedIn) action() else showAccountSheet = true }

    NavHost(navController, startDestination = OnboardingGraphRoute) {
        onboardingGraph(
            navController = navController,
            onContinueAsCustomer = {
                navController.navigate(CustomerRoute) { popUpTo<OnboardingGraphRoute> { inclusive = true } }
            },
            onContinueAsBusiness = {
                requireAccount {
                    navController.navigate(BusinessRoute) { popUpTo<OnboardingGraphRoute> { inclusive = true } }
                }
            },
            onSignIn = { navController.navigate(SignInRoute) },
        )
        composable<CustomerRoute> { entry ->
            CustomerShell(
                savedStateHandle = entry.savedStateHandle,
                onScanMe = { requireAccount { navController.navigate(ScanMeRoute) } },
                onSwitchToBusiness = {
                    requireAccount {
                        navController.navigate(BusinessRoute) { popUpTo<CustomerRoute> { inclusive = true } }
                    }
                },
                onSignIn = { navController.navigate(SignInRoute) },
                onOpenGallery = if (isDebug) {
                    { navController.navigate(GalleryRoute) }
                } else {
                    null
                },
            )
        }
        composable<BusinessRoute> { entry ->
            BusinessShell(
                savedStateHandle = entry.savedStateHandle,
                onScanMember = { navController.navigate(ScanMemberRoute) },
                onSwitchToPersonal = {
                    navController.navigate(CustomerRoute) { popUpTo<BusinessRoute> { inclusive = true } }
                },
            )
        }
        scanMeGraph(onBack = navController::navigateUp)
        scanMemberGraph(onBack = navController::navigateUp)
        composable<SignInRoute> { SignInStub(onBack = navController::navigateUp) }
        if (isDebug) {
            composable<GalleryRoute> { KhazenaComponentGallery() }
        }
    }

    if (showAccountSheet) {
        AccountRequiredSheet(
            onDismiss = { showAccountSheet = false },
            onSignIn = {
                showAccountSheet = false
                navController.navigate(SignInRoute)
            },
        )
    }
}

@Composable
private fun SignInStub(onBack: () -> Unit) = Box {
    KhazenaPlaceholder(stringResource(Res.string.sign_in), stringResource(CoreRes.string.coming_soon))
    KhazenaIconButton(
        KhazenaIcons.ArrowLeft,
        stringResource(CoreRes.string.back),
        onBack,
        Modifier.statusBarsPadding().padding(KhazenaSpacing.screenMargin),
    )
}
