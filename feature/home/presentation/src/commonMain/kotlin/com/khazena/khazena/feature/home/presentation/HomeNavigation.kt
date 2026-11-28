package com.khazena.khazena.feature.home.presentation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.khazena.khazena.core.designsystem.pushEnter
import com.khazena.khazena.core.designsystem.pushExit
import com.khazena.khazena.core.designsystem.pushPopEnter
import com.khazena.khazena.core.designsystem.pushPopExit
import com.khazena.khazena.feature.home.presentation.categories.CategoriesRoot
import com.khazena.khazena.feature.home.presentation.categories.CategoryRoot
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object CategoriesRoute

@Serializable
data class CategoryRoute(val id: String)

fun NavGraphBuilder.homeGraph(
    onSwitchAccount: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenWallet: () -> Unit,
    onScanMe: () -> Unit,
    onOpenCategories: () -> Unit,
    onOpenCategory: (String) -> Unit,
) {
    composable<HomeRoute> { HomeRoot(onSwitchAccount, onOpenSearch, onOpenWallet, onScanMe, onOpenCategories, onOpenCategory) }
}

// Lives outside the tab shell: the category screens have no tab bar.
fun NavGraphBuilder.categoryGraph(onBack: () -> Unit, onOpenCategory: (String) -> Unit, onOpenSearch: () -> Unit) {
    composable<CategoriesRoute>(
        enterTransition = { pushEnter() },
        exitTransition = { pushExit() },
        popEnterTransition = { pushPopEnter() },
        popExitTransition = { pushPopExit() },
    ) { CategoriesRoot(onBack, onOpenCategory) }
    composable<CategoryRoute>(
        enterTransition = { pushEnter() },
        exitTransition = { pushExit() },
        popEnterTransition = { pushPopEnter() },
        popExitTransition = { pushPopExit() },
    ) { CategoryRoot(onBack, onOpenSearch) }
}

// The screen underneath (Home) drifts with the push so the pair moves like an iOS navigation stack.
fun NavDestination.isCategoryScreen(): Boolean = hasRoute<CategoriesRoute>() || hasRoute<CategoryRoute>()

