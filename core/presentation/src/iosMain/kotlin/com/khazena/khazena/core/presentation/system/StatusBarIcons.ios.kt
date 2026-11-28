package com.khazena.khazena.core.presentation.system

import androidx.compose.runtime.Composable

// shortcut: on iOS the hosting UIViewController owns the status-bar style; wire it through when the iOS app
// gets a custom root controller.
@Composable
actual fun LightStatusBarIcons(enabled: Boolean) = Unit
