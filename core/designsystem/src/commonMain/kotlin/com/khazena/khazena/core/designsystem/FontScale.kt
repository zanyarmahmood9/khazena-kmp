package com.khazena.khazena.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

const val TOP_BAR_MAX_FONT_SCALE = 1.3f

// For tight chrome (top bars): text still grows with the system setting, but stops where it would collide, as iOS nav bars do.
@Composable
fun CapFontScale(max: Float, content: @Composable () -> Unit) {
    val density = LocalDensity.current
    CompositionLocalProvider(
        LocalDensity provides Density(density.density, density.fontScale.coerceAtMost(max)),
        content = content,
    )
}
