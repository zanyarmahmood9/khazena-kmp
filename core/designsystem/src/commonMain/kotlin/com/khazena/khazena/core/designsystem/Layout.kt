package com.khazena.khazena.core.designsystem

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp

// Lets a row (e.g. a carousel) run edge to edge inside a screen that pads its content by [margin].
fun Modifier.bleedHorizontally(margin: Dp): Modifier = layout { measurable, constraints ->
    val bleed = margin.roundToPx()
    val placeable = measurable.measure(
        constraints.copy(minWidth = constraints.minWidth + bleed * 2, maxWidth = constraints.maxWidth + bleed * 2),
    )
    layout(placeable.width - bleed * 2, placeable.height) { placeable.place(-bleed, 0) }
}
