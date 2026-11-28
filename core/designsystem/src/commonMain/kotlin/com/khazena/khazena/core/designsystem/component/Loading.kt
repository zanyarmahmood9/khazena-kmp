package com.khazena.khazena.core.designsystem.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme

private val LocalShimmer = staticCompositionLocalOf<Brush?> { null }

@Composable
fun KhazenaShimmer(
    loadingLabel: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = KhazenaTheme.colors
    val shift by rememberInfiniteTransition().animateFloat(
        initialValue = -600f,
        targetValue = 1400f,
        animationSpec = infiniteRepeatable(tween(1300, easing = LinearEasing), RepeatMode.Restart),
    )
    val brush = Brush.linearGradient(
        listOf(colors.surfaceTile, colors.surfaceCard, colors.surfaceTile),
        start = Offset(shift, 0f),
        end = Offset(shift + 600f, 0f),
    )
    Box(modifier.clearAndSetSemantics { contentDescription = loadingLabel }) {
        CompositionLocalProvider(LocalShimmer provides brush, content = content)
    }
}

@Composable
fun KhazenaSkeleton(modifier: Modifier = Modifier, shape: Shape = KhazenaRadius.xs) {
    val brush = LocalShimmer.current
    Box(
        modifier.clip(shape).then(
            if (brush != null) Modifier.background(brush) else Modifier.background(KhazenaTheme.colors.surfaceTile),
        ),
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
