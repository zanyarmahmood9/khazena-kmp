package com.khazena.khazena.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalKhazenaColors = staticCompositionLocalOf { KhazenaColors() }
private val LocalKhazenaTypography = staticCompositionLocalOf<KhazenaTypography> {
    error("KhazenaTypography not provided: wrap the UI in KhazenaTheme { }")
}

/**
 * Khazena theme. The design is light-only (Paper base), so there is no dark scheme.
 * Use `KhazenaTheme.colors` / `.typography` for exact Figma tokens. Material 3 components
 * get a matching colour scheme, type scale and shapes, so they look right without overrides.
 */
@Composable
fun KhazenaTheme(content: @Composable () -> Unit) {
    val colors = remember { KhazenaColors() }
    val typography = khazenaTypography()

    CompositionLocalProvider(
        LocalKhazenaColors provides colors,
        LocalKhazenaTypography provides typography,
    ) {
        MaterialTheme(
            colorScheme = colors.toMaterial(),
            typography = typography.toMaterial(),
            shapes = Shapes(
                extraSmall = KhazenaRadius.xs,
                small = KhazenaRadius.sm,
                medium = KhazenaRadius.card,
                large = KhazenaRadius.block,
                extraLarge = KhazenaRadius.hero,
            ),
            content = content,
        )
    }
}

object KhazenaTheme {
    val colors: KhazenaColors
        @Composable @ReadOnlyComposable get() = LocalKhazenaColors.current

    val typography: KhazenaTypography
        @Composable @ReadOnlyComposable get() = LocalKhazenaTypography.current
}

private fun KhazenaColors.toMaterial() = lightColorScheme(
    primary = actionPrimary,
    onPrimary = actionOnPrimary,
    primaryContainer = blockEmber,
    onPrimaryContainer = blockInk,
    secondary = blockViolet,
    onSecondary = textOnViolet,
    secondaryContainer = blockViolet,
    onSecondaryContainer = textOnViolet,
    tertiary = blockSun,
    onTertiary = blockInk,
    tertiaryContainer = blockSun,
    onTertiaryContainer = blockInk,
    background = surfaceBackground,
    onBackground = textPrimary,
    surface = surfaceBackground,
    onSurface = textPrimary,
    surfaceVariant = surfaceTile,
    onSurfaceVariant = textSecondary,
    // Flat design, no shadows: containers are plain Paper / Card / Tile, never tinted.
    surfaceTint = surfaceBackground,
    surfaceContainerLowest = surfaceCard,
    surfaceContainerLow = surfaceCard,
    surfaceContainer = surfaceCard,
    surfaceContainerHigh = surfaceCard,
    surfaceContainerHighest = surfaceTile,
    inverseSurface = blockInk,
    inverseOnSurface = textOnDark,
    inversePrimary = blockEmber,
    outline = borderStrong,
    outlineVariant = borderDefault,
    error = textDanger,
    onError = surfaceCard,
    errorContainer = statusDangerTint,
    onErrorContainer = statusDanger,
    scrim = blockInk,
)

private fun KhazenaTypography.toMaterial() = Typography(
    displayLarge = displayXL,
    displayMedium = displayL,
    displaySmall = headingH1,
    headlineLarge = headingH1,
    headlineMedium = headingH2,
    headlineSmall = headingH3,
    titleLarge = headingH4,
    titleMedium = labelLBold,
    titleSmall = labelMSemiBold,
    bodyLarge = bodyL,
    bodyMedium = bodyM,
    bodySmall = bodyS,
    labelLarge = buttonL,
    labelMedium = labelSMedium,
    labelSmall = labelXSMedium,
)
