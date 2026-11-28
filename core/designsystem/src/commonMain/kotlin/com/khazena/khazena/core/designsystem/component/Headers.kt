package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.pressScale
import com.khazena.khazena.core.designsystem.rememberThrottledClick
import khazena.core.designsystem.generated.resources.Res
import khazena.core.designsystem.generated.resources.ring_arc_bottom
import khazena.core.designsystem.generated.resources.ring_arc_top
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import org.jetbrains.compose.resources.painterResource

enum class AccountMode { Personal, Business }

@Composable
fun KhazenaAccountSwitch(
    mode: AccountMode,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = KhazenaTheme.colors
    val business = mode == AccountMode.Business
    Box(
        modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(if (business) colors.blockInk else colors.surfaceCard)
            .border(2.dp, colors.blockInk, CircleShape)
            .clickable(role = Role.Switch, onClickLabel = contentDescription, onClick = onClick)
            .padding(6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier.fillMaxSize().background(if (business) colors.actionPrimary else colors.blockViolet, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painterResource(if (business) KhazenaIcons.Storefront else KhazenaIcons.User),
                contentDescription,
                Modifier.size(20.dp),
                tint = if (business) colors.actionOnPrimary else colors.textOnViolet,
            )
        }
    }
}

// Figma's account ring: the active side's arc is ink with cream lettering, the other is white with grey.
// Letters sit on the arc band's centre line, 13.5° apart, upright towards the centre.
@Composable
fun KhazenaAccountRing(
    mode: AccountMode,
    initials: String,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = KhazenaTheme.colors
    val personal = mode == AccountMode.Personal
    val textMeasurer = rememberTextMeasurer()
    val letterStyle = KhazenaTheme.typography.monoTagS.copy(fontSize = 8.sp, letterSpacing = 0.sp, lineHeight = 8.sp)
    val interaction = remember { MutableInteractionSource() }
    Box(
        modifier
            .pressScale(interaction, pressedScale = 0.92f)
            .size(72.dp)
            .clip(CircleShape)
            .clickable(interaction, LocalIndication.current, role = Role.Switch, onClickLabel = contentDescription, onClick = rememberThrottledClick(onClick))
            .semantics { this.contentDescription = contentDescription },
    ) {
        Image(
            painterResource(Res.drawable.ring_arc_top),
            null,
            Modifier.align(Alignment.TopCenter).size(71.8237.dp, 34.4613.dp),
            colorFilter = ColorFilter.tint(if (personal) colors.blockInk else colors.surfaceCard),
        )
        Image(
            painterResource(Res.drawable.ring_arc_bottom),
            null,
            Modifier.align(Alignment.BottomCenter).size(71.8237.dp, 34.4613.dp),
            colorFilter = ColorFilter.tint(if (personal) colors.surfaceCard else colors.blockInk),
        )
        Canvas(Modifier.matchParentSize()) {
            val radius = 29.dp.toPx()
            fun word(text: String, color: Color, top: Boolean) = text.forEachIndexed { index, char ->
                val degrees = if (top) -47.25f + 13.5f * index else 47.25f - 13.5f * index
                val radians = degrees * PI.toFloat() / 180f
                val anchor = if (top) {
                    Offset(center.x + radius * sin(radians), center.y - radius * cos(radians))
                } else {
                    Offset(center.x - radius * sin(radians), center.y + radius * cos(radians))
                }
                val glyph = textMeasurer.measure(char.toString(), letterStyle)
                rotate(degrees, pivot = anchor) {
                    drawText(glyph, color, topLeft = anchor - Offset(glyph.size.width / 2f, glyph.size.height / 2f))
                }
            }
            word(RING_PERSONAL, if (personal) colors.textOnDark else colors.textSecondary, top = true)
            word(RING_BUSINESS, if (personal) colors.textSecondary else colors.textOnDark, top = false)
        }
        Box(
            Modifier.align(Alignment.Center).size(38.dp).background(colors.blockViolet, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(initials, style = KhazenaTheme.typography.labelSMedium.copy(fontWeight = FontWeight.SemiBold), color = colors.textOnViolet, maxLines = 1)
        }
    }
}

// Brand lettering around the ring, the same in every language (the switch itself is announced by contentDescription).
private const val RING_PERSONAL = "PERSONAL"
private const val RING_BUSINESS = "BUSINESS"

@Composable
fun KhazenaHomeHeader(
    mode: AccountMode,
    greeting: String,
    switchLabel: String,
    notificationsLabel: String,
    onSwitchAccount: () -> Unit,
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            Modifier.fillMaxWidth().height(56.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painterResource(KhazenaLogo.MarkOnPaper), null, Modifier.size(width = 22.8.dp, height = 25.84.dp))
            KhazenaWordmark(KhazenaTheme.typography.headingH2Tight, KhazenaTheme.colors.textPrimary)
            Spacer(Modifier.weight(1f))
            KhazenaAccountSwitch(mode, switchLabel, onSwitchAccount)
            KhazenaIconButton(KhazenaIcons.Bell, notificationsLabel, onNotificationsClick, style = IconButtonStyle.White)
        }
        Text(
            greeting,
            style = KhazenaTheme.typography.headingH2,
            color = KhazenaTheme.colors.textPrimary,
            modifier = Modifier.semantics { heading() },
        )
    }
}

@Composable
fun KhazenaPlaceholder(title: String, message: String, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .background(KhazenaTheme.colors.surfaceBackground)
            .statusBarsPadding()
            .padding(horizontal = KhazenaSpacing.screenMargin),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            title,
            style = KhazenaTheme.typography.headingH2,
            color = KhazenaTheme.colors.textPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.semantics { heading() },
        )
        Text(message, style = KhazenaTheme.typography.bodyM, color = KhazenaTheme.colors.textSecondary, textAlign = TextAlign.Center)
    }
}

@Preview(name = "Account ring", widthDp = 200)
@Composable
private fun AccountRingPreview() = PreviewSurface {
    KhazenaAccountRing(AccountMode.Personal, "BK", "Switch to business", {})
    KhazenaAccountRing(AccountMode.Business, "BK", "Switch to personal", {})
}

@Preview(name = "Home header", widthDp = 390)
@Composable
private fun HomeHeaderPreview() = PreviewSurface {
    KhazenaHomeHeader(AccountMode.Personal, "Where to save today?", "Switch to business", "Notifications", {}, {})
    KhazenaHomeHeader(AccountMode.Business, "Ready to scan?", "Switch to personal", "Notifications", {}, {})
}

@Preview(name = "Home header · RTL", widthDp = 390)
@Composable
private fun HomeHeaderRtlPreview() = RtlPreviewSurface {
    KhazenaHomeHeader(AccountMode.Personal, "ئەمڕۆ لە کوێ پارە پاشەکەوت دەکەیت؟", "بگۆڕە بۆ بازرگانی", "ئاگادارکردنەوەکان", {}, {})
}

@Preview(name = "Placeholder", widthDp = 390, heightDp = 400)
@Composable
private fun PlaceholderPreview() = KhazenaTheme { KhazenaPlaceholder("Wallet", "Coming soon.") }
