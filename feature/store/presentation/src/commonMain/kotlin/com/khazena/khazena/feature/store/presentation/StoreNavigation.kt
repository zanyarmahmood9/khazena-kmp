package com.khazena.khazena.feature.store.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.khazena.khazena.core.designsystem.pushEnter
import com.khazena.khazena.core.designsystem.pushExit
import com.khazena.khazena.core.designsystem.pushPopEnter
import com.khazena.khazena.core.designsystem.pushPopExit
import kotlinx.serialization.Serializable

@Serializable
data class StoreDetailRoute(val id: String)

@Serializable
data class ProductRoute(val storeId: String, val itemId: String)

// Both live outside the tab shell: they carry their own sticky checkout bar instead (Figma 02c, 02f).
fun NavGraphBuilder.storeDetailGraph(
    onBack: () -> Unit,
    onScanMe: () -> Unit,
    onOpenStore: (storeId: String) -> Unit,
    onOpenProduct: (storeId: String, itemId: String) -> Unit,
) {
    composable<StoreDetailRoute>(
        enterTransition = { pushEnter() },
        exitTransition = { pushExit() },
        popEnterTransition = { pushPopEnter() },
        popExitTransition = { pushPopExit() },
    ) { entry ->
        val storeId = entry.toRoute<StoreDetailRoute>().id
        StoreRoot(onBack, onScanMe, onOpenItem = { itemId -> onOpenProduct(storeId, itemId) })
    }
    composable<ProductRoute>(
        enterTransition = { pushEnter() },
        exitTransition = { pushExit() },
        popEnterTransition = { pushPopEnter() },
        popExitTransition = { pushPopExit() },
    ) { entry ->
        val storeId = entry.toRoute<ProductRoute>().storeId
        ProductRoot(
            onBack = onBack,
            onOpenStore = { onOpenStore(storeId) },
            onOpenProduct = { itemId -> onOpenProduct(storeId, itemId) },
            onScanMe = onScanMe,
        )
    }
}
