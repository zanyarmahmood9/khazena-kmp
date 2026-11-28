package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme

// One highlight sweeps across the whole screen and every skeleton block lights up as it passes, instead of each
// block running its own gradient. The phase comes from the shared frame clock, so separate skeleton groups on a
// screen (a hero and a list) stay in step, and the sweep rests briefly between passes for a calmer rhythm.
private const val SWEEP_MS = 1150
private const val REST_MS = 450

@Stable
private class ShimmerSweep(val phase: State<Float>, val base: Color, val highlight: Color, val rtl: Boolean)

private val LocalShimmer = staticCompositionLocalOf<ShimmerSweep?> { null }

@Composable
fun KhazenaShimmer(
    loadingLabel: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = KhazenaTheme.colors
    val phase = produceState(0f) {
        while (true) {
            withFrameMillis { ms -> value = ((ms % (SWEEP_MS + REST_MS)).toFloat() / SWEEP_MS).coerceAtMost(1f) }
        }
    }
    val rtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    // A glint, not a white-out: on white cards and the white hero a pure-white peak made the bars vanish mid-sweep.
    val sweep = remember(colors, rtl) { ShimmerSweep(phase, colors.surfaceTile, lerp(colors.surfaceTile, colors.surfaceCard, 0.65f), rtl) }
    Box(modifier.clearAndSetSemantics { contentDescription = loadingLabel }) {
        CompositionLocalProvider(LocalShimmer provides sweep, content = content)
    }
}

@Composable
fun KhazenaSkeleton(modifier: Modifier = Modifier, shape: Shape = KhazenaRadius.xs) {
    val sweep = LocalShimmer.current
    if (sweep == null) {
        Box(modifier.clip(shape).background(KhazenaTheme.colors.surfaceTile))
        return
    }
    // Where this block sits on screen; read only while drawing, so the sweep redraws without recomposing.
    val frame = remember { FloatArray(2) }
    Box(
        modifier
            .clip(shape)
            .onGloballyPositioned {
                frame[0] = it.positionInRoot().x
                frame[1] = it.findRootCoordinates().size.width.toFloat()
            }
            .drawBehind {
                val screen = frame[1].coerceAtLeast(size.width)
                val band = screen * 0.45f
                val travel = screen + band * 2
                val progress = if (sweep.rtl) 1f - sweep.phase.value else sweep.phase.value
                val bandStart = -band + progress * travel - frame[0]
                drawRect(
                    Brush.horizontalGradient(
                        listOf(sweep.base, sweep.highlight, sweep.base),
                        startX = bandStart,
                        endX = bandStart + band,
                    ),
                )
            },
    )
}

// Placeholder lines that take exactly the height the real text will: one line box per entry in [widths],
// using [style]'s line height (so it follows the user's text size), with a bar the size of the type inside.
// Content then fades in over the skeleton without anything below it shifting.
@Composable
fun KhazenaSkeletonText(style: TextStyle, widths: List<Dp>, modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val lineHeight = with(density) {
        val line = if (style.lineHeight.isEm) style.fontSize.value * style.lineHeight.value else style.lineHeight.value
        line.sp.toDp()
    }
    val barHeight = with(density) { (style.fontSize.value * 0.8f).sp.toDp() }
    Column(modifier) {
        widths.forEach { width ->
            Box(Modifier.height(lineHeight), contentAlignment = Alignment.CenterStart) {
                KhazenaSkeleton(Modifier.size(width = width, height = barHeight))
            }
        }
    }
}

// Same frame as KhazenaPlaceRow (11dp padding, 64dp photo, 14dp gap) so content lands where the skeleton was.
@Composable
fun KhazenaSkeletonRow(modifier: Modifier = Modifier, thumb: Dp = 64.dp, rating: Boolean = false, tag: Boolean = false) {
    Row(
        modifier
            .fillMaxWidth()
            .clip(KhazenaRadius.card)
            .background(KhazenaTheme.colors.surfaceCard)
            .padding(11.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        KhazenaSkeleton(Modifier.size(thumb), KhazenaRadius.sm)
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            KhazenaSkeleton(Modifier.fillMaxWidth(0.7f).height(14.dp))
            KhazenaSkeleton(Modifier.fillMaxWidth(0.45f).height(12.dp))
            if (rating) KhazenaSkeleton(Modifier.size(width = 56.dp, height = 10.dp))
        }
        if (tag) KhazenaSkeleton(Modifier.size(width = 84.dp, height = 28.dp), KhazenaRadius.pill)
    }
}

@Composable
fun KhazenaSkeletonList(loadingLabel: String, modifier: Modifier = Modifier, rows: Int = 5, header: Boolean = true) {
    KhazenaShimmer(loadingLabel, modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (header) KhazenaSkeleton(Modifier.fillMaxWidth().height(140.dp), KhazenaRadius.block)
            repeat(rows) { KhazenaSkeletonRow() }
        }
    }
}

@Preview(name = "Skeleton list", widthDp = 390)
@Composable
private fun SkeletonPreview() = PreviewSurface { KhazenaSkeletonList("Loading", rows = 3) }
