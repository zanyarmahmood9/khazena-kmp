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
import com.khazena.khazena.core.designsystem.brandFontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview

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

@Composable
fun KhazenaWordmark(
    style: androidx.compose.ui.text.TextStyle,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
    letterSpacing: TextUnit = (-0.04).em,
) {
    Text("khazena", style = style.copy(fontFamily = brandFontFamily(style.fontSize.value), letterSpacing = letterSpacing), color = color, maxLines = 1, modifier = modifier)
}

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
                KhazenaWordmark(KhazenaTheme.typography.wordmarkS, content)
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
            KhazenaWordmark(KhazenaTheme.typography.headingH2Tight, KhazenaTheme.colors.textPrimary)
        }
        Spacer(Modifier.weight(1f))
        KhazenaAvatar(
            initials,
            Modifier.clip(CircleShape).clickable(role = Role.Button, onClick = onAvatarClick),
        )
        KhazenaIconButton(KhazenaIcons.Bell, notificationsLabel, onNotificationsClick, style = IconButtonStyle.White)
    }
}

@Preview(name = "Offer block", widthDp = 390)
@Composable
private fun OfferBlockPreview() = PreviewSurface {
    KhazenaOfferBlock("Member offer · every item", "30% off\neverything.", "Member price is applied to every item")
}

@Preview(name = "Membership card", widthDp = 390)
@Composable
private fun MembershipCardPreview() = PreviewSurface {
    KhazenaMembershipCard(true, "Premium", "Active", "Member", "Bandy Kamal", "Plan", "1-Year Premium", "Valid until", "12 Oct 2027")
    KhazenaMembershipCard(false, "Premium", "Expired", "Member", "Bandy Kamal", "Plan", "1-Year Premium", "Expired on", "12 Oct 2026")
}

@Preview(name = "Info banner", widthDp = 390)
@Composable
private fun InfoBannerPreview() = PreviewSurface {
    KhazenaInfoBanner("Show this live code to the cashier. Screenshots won’t work.")
}

@Preview(name = "Toast", widthDp = 390)
@Composable
private fun ToastPreview() = PreviewSurface {
    KhazenaToast("Removed from saved", detail = "Shine Car Wash", actionLabel = "Undo")
    KhazenaToast("Copied")
}

@Preview(name = "App header", widthDp = 390)
@Composable
private fun AppHeaderPreview() = PreviewSurface {
    KhazenaAppHeader("BK", "Notifications", {}, {})
}
@Preview(name = "Feedback · RTL", widthDp = 390)
@Composable
private fun FeedbackRtlPreview() = RtlPreviewSurface {
    KhazenaAppHeader("ب ک", "ئاگادارکردنەوەکان", {}, {})
    KhazenaOfferBlock("ئۆفەری ئەندام · هەموو بەرهەمێک", "٣٠٪ داشکان\nلە هەمووی.", "نرخی ئەندام بۆ هەموو بەرهەمێک دادەنرێت")
    KhazenaMembershipCard(true, "پریمیەم", "چالاک", "ئەندام", "باندی کەمال", "پلان", "پریمیەمی یەک ساڵ", "بەسەرچوون", "١٢ی ١٠ی ٢٠٢٧")
    KhazenaInfoBanner("ئەم کۆدە زیندووە پیشانی کاشێر بدە. وێنەی شاشە کار ناکات.")
    KhazenaToast("لە پاشەکەوتکراوەکان لابرا", detail = "شاین کار واش", actionLabel = "گەڕانەوە")
}
