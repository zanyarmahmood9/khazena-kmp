package com.khazena.khazena.feature.store.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.khazena.khazena.core.designsystem.pushEnter
import com.khazena.khazena.core.designsystem.pushExit
import com.khazena.khazena.core.designsystem.pushPopEnter
import com.khazena.khazena.core.designsystem.pushPopExit
import kotlinx.serialization.Serializable

@Serializable
data class StoreDetailRoute(val id: String)

// Lives outside the tab shell: the store screen has its own sticky checkout bar instead (Figma 02c).
fun NavGraphBuilder.storeDetailGraph(onBack: () -> Unit, onScanMe: () -> Unit) {
    composable<StoreDetailRoute>(
        enterTransition = { pushEnter() },
        exitTransition = { pushExit() },
        popEnterTransition = { pushPopEnter() },
        popExitTransition = { pushPopExit() },
    ) { StoreRoot(onBack, onScanMe) }
}
