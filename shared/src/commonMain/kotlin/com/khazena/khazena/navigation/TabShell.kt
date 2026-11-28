package com.khazena.khazena.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaTabBar
import com.khazena.khazena.core.designsystem.component.TabBarItem
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

internal class ShellTab(
    val route: Any,
    val label: StringResource,
    val icon: DrawableResource,
    val selectedIcon: DrawableResource = icon,
)

internal const val SELECTED_TAB = "selectedTab"

@Composable
internal fun TabShell(
    tabs: List<ShellTab>,
    savedStateHandle: SavedStateHandle,
    actionIcon: DrawableResource,
    actionLabel: String,
    onAction: () -> Unit,
    graph: NavGraphBuilder.(selectTab: (Int) -> Unit) -> Unit,
) {
    val tabNavController = rememberNavController()
    val selectTab: (Int) -> Unit = { index ->
        tabNavController.navigate(tabs[index].route) {
            popUpTo(tabNavController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
    // The language switch rebuilds the tree (and a screen above the shell can pick a tab); return to that tab.
    val restoredTab = remember { savedStateHandle.get<Int>(SELECTED_TAB) ?: 0 }
    LaunchedEffect(Unit) { if (restoredTab != 0) selectTab(restoredTab) }
    val entry by tabNavController.currentBackStackEntryAsState()
    val selected = tabs.indexOfFirst { tab -> entry?.destination?.hasRoute(tab.route::class) == true }.coerceAtLeast(0)
    LaunchedEffect(selected) { savedStateHandle[SELECTED_TAB] = selected }

    Box(Modifier.fillMaxSize().background(KhazenaTheme.colors.surfaceBackground)) {
        NavHost(
            navController = tabNavController,
            startDestination = tabs.first().route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            builder = { graph(selectTab) },
        )
        KhazenaTabBar(
            items = tabs.map { TabBarItem(stringResource(it.label), it.icon, it.selectedIcon) },
            selectedIndex = selected,
            onSelect = selectTab,
            actionIcon = actionIcon,
            actionLabel = actionLabel,
            onAction = onAction,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}
