package com.khazena.khazena.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

// A destination is RESUMED only once its enter transition has settled, so a second tap mid-transition is dropped.
private val NavController.isIdle: Boolean
    get() = currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

internal fun <T : Any> NavController.navigateOnce(route: T, builder: NavOptionsBuilder.() -> Unit = {}) {
    if (isIdle) navigate(route, builder)
}

internal fun NavController.navigateUpOnce() {
    if (isIdle) navigateUp()
}
