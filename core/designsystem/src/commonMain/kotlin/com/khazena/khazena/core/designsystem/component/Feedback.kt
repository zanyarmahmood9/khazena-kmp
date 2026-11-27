package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/** Sun offer block: kicker, big headline, body and an Ink "%" badge. */
@Composable
fun KhazenaOfferBlock(kicker: String, headline: String, body: String, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    Row(
        modifier.fillMaxWidth().clip(KhazenaRadius.card).background(colors.blockSun).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(kicker.uppercase(), style = KhazenaTheme.typography.monoKicker, color = colors.textPrimary)
            Text(headline, style = KhazenaTheme.typography.headingH2Tight, color = colors.textPrimary)
            Text(body, style = KhazenaTheme.typography.labelXSMedium, color = colors.textPrimary)
        }
        Box(Modifier.size(48.dp).background(colors.blockInk, CircleShape), contentAlignment = Alignment.Center) {
            Text("%", style = KhazenaTheme.typography.headingH3, color = colors.blockSun)
        }
    }
}

/** "khazena" wordmark text, as set next to the K mark. Brand name: not translated. */
@Composable
internal fun Wordmark(style: androidx.compose.ui.text.TextStyle, color: androidx.compose.ui.graphics.Color) {
    Text("khazena", style = style, color = color, maxLines = 1)
}

/**
 * Membership card, 196 tall: Violet when active, Ink when expired. All copy is passed in so it can be
 * localised: e.g. memberLabel "Member", planLabel "Plan", dateLabel "Valid until" / "Expired on".
 */
@Composable
fun KhazenaMembershipCard(
    active: Boolean,
    planBadge: String,
    statusLabel: String,
    memberLabel: String,
    memberName: String,
    planLabel: String,
    planName: String,
    dateLabel: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    val colors = KhazenaTheme.colors
    val content = if (active) colors.textOnViolet else colors.textOnDark
    Box(
        modifier
            .fillMaxWidth()
            .height(196.dp)
            .clip(KhazenaRadius.block)
            .background(if (active) colors.blockViolet else colors.blockInk),
    ) {
        Image(
            painterResource(KhazenaLogo.Watermark),
            null,
            Modifier.align(Alignment.BottomEnd).offset(x = 10.dp, y = (-2).dp).size(width = 90.dp, height = 102.dp),
        )
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painterResource(if (active) KhazenaLogo.MarkOnViolet else KhazenaLogo.MarkOnInk),
                    null,
                    Modifier.size(width = 16.8.dp, height = 19.04.dp),
                )
                Spacer(Modifier.size(6.dp))
                Wordmark(KhazenaTheme.typography.wordmarkS, content)
                Spacer(Modifier.size(6.dp))
                KhazenaTag(planBadge)
                Spacer(Modifier.weight(1f))
                KhazenaTag(statusLabel, type = if (active) TagType.Live else TagType.Expired)
            }
            Spacer(Modifier.weight(1f))
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(memberLabel.uppercase(), style = KhazenaTheme.typography.monoKicker, color = content)
                Text(memberName, style = KhazenaTheme.typography.headingH4, color = content, maxLines = 1)
            }
            Spacer(Modifier.weight(1f))
            Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
                CardFact(planLabel, planName, content)
                CardFact(dateLabel, date, content)
            }
        }
    }
}

@Composable
private fun CardFact(label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(label.uppercase(), style = KhazenaTheme.typography.monoKicker, color = color)
        Text(value, style = KhazenaTheme.typography.labelMSemiBold, color = color)
    }
}

/** White info banner with a Sun icon tile (bell by default). */
@Composable
fun KhazenaInfoBanner(message: String, modifier: Modifier = Modifier, icon: DrawableResource = KhazenaIcons.Bell) {
    val colors = KhazenaTheme.colors
    Row(
        modifier.fillMaxWidth().clip(KhazenaRadius.card).background(colors.surfaceCard).padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(34.dp).background(colors.blockSun, RoundedCornerShape(11.dp)), contentAlignment = Alignment.Center) {
            Icon(painterResource(icon), null, Modifier.size(16.dp), tint = colors.textPrimary)
        }
        Text(message, style = KhazenaTheme.typography.bodyS, color = colors.textSecondary, modifier = Modifier.weight(1f))
    }
}

/** Ink pill toast with title, detail and an optional action (e.g. Undo). Show it from a SnackbarHost. */
@Composable
fun KhazenaToast(
    title: String,
    modifier: Modifier = Modifier,
    detail: String? = null,
    actionLabel: String? = null,
    onAction: () -> Unit = {},
) {
    val colors = KhazenaTheme.colors
    Row(
        modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(KhazenaRadius.pill)
            .background(colors.surfaceInverse)
            .padding(start = 18.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, style = KhazenaTheme.typography.labelMSemiBold, color = colors.textOnDark, maxLines = 1)
            if (detail != null) Text(detail, style = KhazenaTheme.typography.labelXSMedium, color = colors.textOnDark, maxLines = 1)
        }
        if (actionLabel != null) {
            KhazenaButton(actionLabel, onAction, variant = ButtonVariant.Secondary, size = ButtonSize.M)
        }
    }
}

/** Home header: K mark + wordmark, member avatar and a white bell button. */
@Composable
fun KhazenaAppHeader(
    initials: String,
    notificationsLabel: String,
    onAvatarClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.fillMaxWidth().height(44.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(painterResource(KhazenaLogo.MarkOnPaper), null, Modifier.size(width = 22.8.dp, height = 25.84.dp))
        Box(Modifier.semantics { heading() }) {
            Wordmark(KhazenaTheme.typography.headingH2Tight, KhazenaTheme.colors.textPrimary)
        }
        Spacer(Modifier.weight(1f))
        KhazenaAvatar(
            initials,
            Modifier.clip(CircleShape).clickable(role = Role.Button, onClick = onAvatarClick),
        )
        KhazenaIconButton(KhazenaIcons.Bell, notificationsLabel, onNotificationsClick, style = IconButtonStyle.White)
    }
}
