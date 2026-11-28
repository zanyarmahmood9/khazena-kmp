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
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.component.KhazenaComponentGallery
import com.khazena.khazena.core.designsystem.component.KhazenaIconButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaPlaceholder
import com.khazena.khazena.core.designsystem.pushExit
import com.khazena.khazena.core.designsystem.pushPopEnter
import com.khazena.khazena.core.domain.onboarding.AppSide
import com.khazena.khazena.core.domain.onboarding.OnboardingProgress
import com.khazena.khazena.feature.business.scan.presentation.ScanMemberRoute
import com.khazena.khazena.feature.business.scan.presentation.scanMemberGraph
import com.khazena.khazena.feature.chooseside.presentation.ChooseSideRoute
import com.khazena.khazena.feature.chooseside.presentation.chooseSideGraph
import com.khazena.khazena.feature.home.presentation.CategoriesRoute
import com.khazena.khazena.feature.home.presentation.CategoryRoute
import com.khazena.khazena.feature.home.presentation.categoryGraph
import com.khazena.khazena.feature.home.presentation.isCategoryScreen
import com.khazena.khazena.feature.onboarding.presentation.OnboardingGraphRoute
import com.khazena.khazena.feature.onboarding.presentation.onboardingGraph
import com.khazena.khazena.feature.scanme.presentation.ScanMeRoute
import com.khazena.khazena.feature.scanme.presentation.scanMeGraph
import com.khazena.khazena.feature.store.presentation.StoreDetailRoute
import com.khazena.khazena.feature.store.presentation.storeDetailGraph
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
internal fun AppNavHost(
    navController: NavHostController,
    isSignedIn: Boolean,
    isDebug: Boolean,
    onboarding: () -> OnboardingProgress?,
    onIntroCompleted: () -> Unit,
    onSideEntered: (AppSide) -> Unit,
) {
    var showAccountSheet by rememberSaveable { mutableStateOf(false) }
    val requireAccount: (() -> Unit) -> Unit = { action -> if (isSignedIn) action() else showAccountSheet = true }

    NavHost(navController, startDestination = OnboardingGraphRoute) {
        onboardingGraph(
            navController = navController,
            resumeRoute = { onboarding().resumeRoute() },
            onFinished = {
                onIntroCompleted()
                navController.navigateOnce(ChooseSideRoute) { popUpTo<OnboardingGraphRoute> { inclusive = true } }
            },
        )
        chooseSideGraph(
            onContinueAsCustomer = {
                onSideEntered(AppSide.Customer)
                navController.navigateOnce(CustomerRoute) { popUpTo<ChooseSideRoute> { inclusive = true } }
            },
            onContinueAsBusiness = {
                // Saved only once the account check passes, so a blocked business choice isn't remembered.
                requireAccount {
                    onSideEntered(AppSide.Business)
                    navController.navigateOnce(BusinessRoute) { popUpTo<ChooseSideRoute> { inclusive = true } }
                }
            },
            onSignIn = { navController.navigateOnce(SignInRoute) },
        )
        composable<CustomerRoute>(
            exitTransition = { if (targetState.destination.isPushedOverTabs()) pushExit() else null },
            popEnterTransition = { if (initialState.destination.isPushedOverTabs()) pushPopEnter() else null },
        ) { entry ->
            CustomerShell(
                savedStateHandle = entry.savedStateHandle,
                onScanMe = { requireAccount { navController.navigateOnce(ScanMeRoute) } },
                onSwitchToBusiness = {
                    requireAccount {
                        onSideEntered(AppSide.Business)
                        navController.navigateOnce(BusinessRoute) { popUpTo<CustomerRoute> { inclusive = true } }
                    }
                },
                onSignIn = { navController.navigateOnce(SignInRoute) },
                onOpenGallery = if (isDebug) {
                    { navController.navigateOnce(GalleryRoute) }
                } else {
                    null
                },
                onOpenCategories = { navController.navigateOnce(CategoriesRoute) },
                onOpenCategory = { navController.navigateOnce(CategoryRoute(it)) },
                onOpenStore = { navController.navigateOnce(StoreDetailRoute(it)) },
            )
        }
        composable<BusinessRoute> { entry ->
            BusinessShell(
                savedStateHandle = entry.savedStateHandle,
                onScanMember = { navController.navigateOnce(ScanMemberRoute) },
                onSwitchToPersonal = {
                    onSideEntered(AppSide.Customer)
                    navController.navigateOnce(CustomerRoute) { popUpTo<BusinessRoute> { inclusive = true } }
                },
            )
        }
        scanMeGraph(onBack = navController::navigateUpOnce)
        categoryGraph(
            onBack = navController::navigateUpOnce,
            onOpenCategory = { navController.navigateOnce(CategoryRoute(it)) },
            onOpenStore = { navController.navigateOnce(StoreDetailRoute(it)) },
        )
        storeDetailGraph(
            onBack = navController::navigateUpOnce,
            onScanMe = { requireAccount { navController.navigateOnce(ScanMeRoute) } },
        )
        scanMemberGraph(onBack = navController::navigateUpOnce)
        composable<SignInRoute> { SignInStub(onBack = navController::navigateUpOnce) }
        if (isDebug) {
            composable<GalleryRoute> { KhazenaComponentGallery() }
        }
    }

    if (showAccountSheet) {
        AccountRequiredSheet(
            onDismiss = { showAccountSheet = false },
            onSignIn = {
                showAccountSheet = false
                navController.navigateOnce(SignInRoute)
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

// Screens pushed over the customer tabs slide in iOS-style, and Home drifts underneath them.
private fun NavDestination.isPushedOverTabs(): Boolean = isCategoryScreen() || hasRoute<StoreDetailRoute>()
