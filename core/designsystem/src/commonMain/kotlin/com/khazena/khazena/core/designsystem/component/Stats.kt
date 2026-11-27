package com.khazena.khazena.core.designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaMotion
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import kotlinx.coroutines.delay

@Composable
fun KhazenaStatTile(value: String, label: String, modifier: Modifier = Modifier, tone: StateTone? = null) {
    val colors = KhazenaTheme.colors
    val (container, content) = when (tone) {
        StateTone.Violet -> colors.blockViolet to colors.textOnViolet
        StateTone.Sun -> colors.blockSun to colors.textPrimary
        StateTone.Ember -> colors.blockEmber to colors.textPrimary
        null -> colors.surfaceCard to colors.textPrimary
    }
    Column(
        modifier.background(container, KhazenaRadius.card).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(value, style = KhazenaTheme.typography.headingH3, color = content, maxLines = 1)
        Text(label, style = KhazenaTheme.typography.labelXSMedium, color = content, maxLines = 2)
    }
}

@Composable
fun KhazenaBarChart(
    values: List<Int>,
    labels: List<String>,
    description: String,
    modifier: Modifier = Modifier,
    highlightIndex: Int = values.lastIndex,
) {
    val colors = KhazenaTheme.colors
    val max = (values.maxOrNull() ?: 0).coerceAtLeast(1)
    Row(
        modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(colors.surfaceCard, KhazenaRadius.card)
            .padding(16.dp)
            .clearAndSetSemantics { contentDescription = description },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        values.forEachIndexed { index, value ->
            val grow = remember { Animatable(0f) }
            LaunchedEffect(value) {
                delay((index * KhazenaMotion.STAGGER).toLong())
                grow.animateTo(value.toFloat() / max, tween(KhazenaMotion.LONG + 150, easing = KhazenaMotion.EmphasizedDecelerate))
            }
            Column(Modifier.weight(1f).fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    value.toString(),
                    style = KhazenaTheme.typography.labelXSMedium,
                    color = colors.textSecondary,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
                Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(grow.value.coerceAtLeast(0.02f))
                            .background(if (index == highlightIndex) colors.blockEmber else colors.blockViolet, KhazenaRadius.xs),
                    )
                }
                Text(
                    labels.getOrElse(index) { "" },
                    style = KhazenaTheme.typography.labelXSMedium,
                    color = colors.textSecondary,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 6.dp),
                )
            }
        }
    }
}

@Preview(name = "Stats", widthDp = 390)
@Composable
private fun StatsPreview() = PreviewSurface {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        KhazenaStatTile("42", "Scans today", Modifier.weight(1f), StateTone.Sun)
        KhazenaStatTile("128,000 IQD", "Saved by members", Modifier.weight(1f))
    }
    KhazenaBarChart(listOf(12, 18, 9, 22, 30, 26, 14), listOf("Sa", "Su", "Mo", "Tu", "We", "Th", "Fr"), "Visits per day")
}

@Composable
fun KhazenaPersonRow(initials: String, title: String, meta: String, trailing: String, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    Row(
        modifier
            .fillMaxWidth()
            .background(colors.surfaceCard, KhazenaRadius.card)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        KhazenaAvatar(initials)
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, style = KhazenaTheme.typography.labelLBold, color = colors.textPrimary, maxLines = 1)
            Text(meta, style = KhazenaTheme.typography.bodyS, color = colors.textSecondary, maxLines = 1)
        }
        KhazenaTag(trailing)
    }
}
