package com.khazena.khazena.core.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class KhazenaColors(
    // Brand blocks
    val blockEmber: Color = Color(0xFFFF481F),
    val blockSun: Color = Color(0xFFFFC933),
    val blockViolet: Color = Color(0xFF6A4CFF),
    val blockInk: Color = Color(0xFF16121F),

    // Surfaces & borders
    val surfaceBackground: Color = Color(0xFFF8F2E7),
    val surfaceCard: Color = Color(0xFFFFFFFF),
    val surfaceTile: Color = Color(0xFFF2ECE1),
    val borderDefault: Color = Color(0xFFE7E0D2),
    val borderStrong: Color = Color(0xFFD8CFBF),
    val borderSelected: Color = Color(0xFF16121F),
    val surfaceInverse: Color = Color(0xFF16121F),

    // Text
    val textPrimary: Color = Color(0xFF16121F),
    val textSecondary: Color = Color(0xFF6B6577),
    val textPlaceholder: Color = Color(0xFF7A7486),
    val textOnDark: Color = Color(0xFFF8F2E7),
    val textOnViolet: Color = Color(0xFFFFFFFF),
    val textWarning: Color = Color(0xFFC2410C),
    val textDanger: Color = Color(0xFFDC2626),

    // Actions
    val actionPrimary: Color = Color(0xFFFF481F),
    val actionOnPrimary: Color = Color(0xFF16121F),
    val actionSecondary: Color = Color(0xFFF8F2E7),
    val actionSelected: Color = Color(0xFF16121F),
    val actionDisabled: Color = Color(0xFFE7E0D2),
    val actionOnSelected: Color = Color(0xFFF8F2E7),

    // Tags
    val tagDiscount: Color = Color(0xFFFFC933),
    val tagStatus: Color = Color(0xFFF8F2E7),
    val tagHighlight: Color = Color(0xFF16121F),

    // Status (customer app)
    val statusLive: Color = Color(0xFF1F7A3A),
    val statusVerified: Color = Color(0xFF6A4CFF),
    val statusExpired: Color = Color(0xFFDC2626),
    val statusExpiredOnDark: Color = Color(0xFFFF8A70),

    val statusSuccess: Color = Color(0xFF1F7A3A),
    val statusSuccessTint: Color = Color(0xFFDDF1E2),
    val statusDanger: Color = Color(0xFFC2260C),
    val statusDangerTint: Color = Color(0xFFFFE4DB),
    val statusPending: Color = Color(0xFF7A5A00),
    val statusPendingTint: Color = Color(0xFFFFF1C7),

    // Icons
    val iconStar: Color = Color(0xFFFFA51F),
    val iconHeart: Color = Color(0xFFFF481F),
    val iconMuted: Color = Color(0xFF6B6577),
)
