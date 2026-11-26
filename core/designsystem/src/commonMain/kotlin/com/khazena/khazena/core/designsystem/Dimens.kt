package com.khazena.khazena.core.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * Figma › 03 · Spacing & radius. Everything sits on a 2/4pt grid: screen margin 20,
 * card padding 11–16, gaps of 10–12 in lists, 32 between sections.
 */
object KhazenaSpacing {
    val s2 = 2.dp
    val s4 = 4.dp
    val s6 = 6.dp
    val s8 = 8.dp
    val s10 = 10.dp
    val s12 = 12.dp
    val s14 = 14.dp
    val s16 = 16.dp
    val s20 = 20.dp
    val s24 = 24.dp
    val s32 = 32.dp
    val s40 = 40.dp

    val screenMargin = s20
    val sectionGap = s32
}

object KhazenaRadius {
    val xs = RoundedCornerShape(8.dp) // small marks
    val sm = RoundedCornerShape(14.dp) // thumbnails, star tiles
    val card = RoundedCornerShape(20.dp) // all white cards
    val tile = RoundedCornerShape(22.dp) // category tiles
    val block = RoundedCornerShape(24.dp) // colour blocks
    val hero = RoundedCornerShape(28.dp) // hero & onboarding blocks
    val pill = RoundedCornerShape(percent = 50) // buttons, tags, chips
}

object KhazenaSize {
    val tapMin = 44.dp
    val button = 56.dp
    val buttonSm = 44.dp
    val chip = 36.dp
    val tag = 24.dp
    val thumb = 64.dp
    val icon = 24.dp
}
