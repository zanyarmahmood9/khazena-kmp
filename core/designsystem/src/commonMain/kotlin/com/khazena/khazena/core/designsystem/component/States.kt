package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

enum class StateTone { Violet, Sun, Ember }

@Composable
fun KhazenaStateCard(
    icon: DrawableResource,
    badge: String,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    tone: StateTone = StateTone.Violet,
    actionLabel: String? = null,
    actionVariant: ButtonVariant = ButtonVariant.Primary,
    onAction: () -> Unit = {},
) {
    val colors = KhazenaTheme.colors
    val block = when (tone) {
        StateTone.Violet -> colors.blockViolet
        StateTone.Sun -> colors.blockSun
        StateTone.Ember -> colors.blockEmber
    }
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(Modifier.fillMaxWidth().height(150.dp).background(block, RoundedCornerShape(16.dp))) {
            Confetti(Modifier.align(Alignment.TopStart).offset(24.dp, 26.dp), colors.blockSun.takeIf { tone != StateTone.Sun } ?: colors.blockViolet, -30f)
            Confetti(Modifier.align(Alignment.TopEnd).offset((-28).dp, 22.dp), colors.blockEmber.takeIf { tone != StateTone.Ember } ?: colors.blockSun, 30f)
            Confetti(Modifier.align(Alignment.BottomEnd).offset((-30).dp, (-30).dp), colors.surfaceCard, 20f)
            Box(
                Modifier.align(Alignment.Center).size(84.dp).background(colors.surfaceCard, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(painterResource(icon), null, Modifier.size(36.dp), tint = colors.textPrimary)
            }
            Text(
                badge.uppercase(),
                style = KhazenaTheme.typography.monoTagS,
                color = colors.textOnDark,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(18.dp, (-22).dp)
                    .rotate(-6f)
                    .background(colors.blockInk, RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            )
        }
        Text(
            title,
            style = KhazenaTheme.typography.headingH3,
            color = colors.textPrimary,
            modifier = Modifier.semantics { heading() }.padding(top = 4.dp),
        )
        Text(body, style = KhazenaTheme.typography.bodyM, color = colors.textSecondary)
        if (actionLabel != null) {
            KhazenaButton(actionLabel, onAction, Modifier.fillMaxWidth().padding(top = 4.dp), variant = actionVariant)
        }
    }
}

@Composable
private fun Confetti(modifier: Modifier, color: Color, angle: Float) = Box(
    modifier.rotate(angle).size(width = 20.dp, height = 7.dp).background(color, RoundedCornerShape(50)),
)

@Preview(name = "State card", widthDp = 390)
@Composable
private fun StateCardPreview() = PreviewSurface {
    KhazenaStateCard(KhazenaIcons.MagnifyingGlass, "No match", "No results for “sushi”.", "Check the spelling or try another word.", actionLabel = "Browse categories")
}

@Preview(name = "State card · RTL", widthDp = 390)
@Composable
private fun StateCardRtlPreview() = RtlPreviewSurface {
    KhazenaStateCard(KhazenaIcons.Bell, "هەڵە", "نەتوانرا بار بکرێت.", "پەیوەندییەکەت بپشکنە و دووبارە هەوڵ بدەرەوە.", tone = StateTone.Ember, actionLabel = "دووبارە هەوڵ بدەرەوە", actionVariant = ButtonVariant.Dark)
}
