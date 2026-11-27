package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
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
