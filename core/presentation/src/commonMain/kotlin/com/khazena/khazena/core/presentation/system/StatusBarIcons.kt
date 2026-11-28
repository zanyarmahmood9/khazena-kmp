package com.khazena.khazena.core.presentation.system

import androidx.compose.runtime.Composable

/** White status-bar icons while [enabled] (e.g. over a dark cover photo); the previous style returns when it leaves. */
@Composable
expect fun LightStatusBarIcons(enabled: Boolean)
