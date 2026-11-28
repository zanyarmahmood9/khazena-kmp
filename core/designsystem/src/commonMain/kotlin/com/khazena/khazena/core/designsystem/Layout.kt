package com.khazena.khazena.core.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset

// Lets a row (e.g. a carousel) run edge to edge inside a screen that pads its content by [margin].
fun Modifier.bleedHorizontally(margin: Dp): Modifier = layout { measurable, constraints ->
    val bleed = margin.roundToPx()
    val placeable = measurable.measure(
        constraints.copy(minWidth = constraints.minWidth + bleed * 2, maxWidth = constraints.maxWidth + bleed * 2),
    )
    layout(placeable.width - bleed * 2, placeable.height) { placeable.place(-bleed, 0) }
}

private val TextPressOutsetX = 8.dp
private val TextPressOutsetY = 4.dp

// A tappable text link: the gray press highlight is a rounded pill reaching a little past the glyphs,
// while the text keeps its own footprint so the layout matches Figma.
fun Modifier.textClickable(role: Role = Role.Button, onClick: () -> Unit): Modifier = composed {
    val click = rememberThrottledClick(onClick)
    Modifier
        .layout { measurable, constraints ->
            val x = TextPressOutsetX.roundToPx()
            val y = TextPressOutsetY.roundToPx()
            val placeable = measurable.measure(constraints.offset(x * 2, y * 2))
            layout(placeable.width - x * 2, placeable.height - y * 2) { placeable.place(-x, -y) }
        }
        .clip(KhazenaRadius.xs)
        .clickable(role = role, onClick = click)
        .padding(horizontal = TextPressOutsetX, vertical = TextPressOutsetY)
}

// Tapping empty space drops focus (and the keyboard). Taps a child handles itself (a field, a card, a button)
// are consumed before they reach here, so they keep working as usual.
fun Modifier.clearFocusOnTap(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    pointerInput(focusManager) { detectTapGestures { focusManager.clearFocus() } }
}

// Scrolls a chip row so [index] sits in the middle, bringing its neighbours on either side into view.
suspend fun LazyListState.animateScrollToCenter(index: Int) {
    val info = layoutInfo
    val viewport = info.viewportEndOffset - info.viewportStartOffset
    val itemSize = info.visibleItemsInfo.firstOrNull { it.index == index }?.size ?: 0
    animateScrollToItem(index, -(viewport - itemSize) / 2)
}
