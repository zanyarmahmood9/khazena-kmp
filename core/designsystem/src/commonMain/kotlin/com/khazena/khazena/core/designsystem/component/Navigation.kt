package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun KhazenaSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    linkLabel: String? = null,
    onLinkClick: () -> Unit = {},
) {
    val colors = KhazenaTheme.colors
    Row(
        modifier.fillMaxWidth().height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, style = KhazenaTheme.typography.headingH3, color = colors.textPrimary, maxLines = 1)
        if (linkLabel != null) {
            Text(
                linkLabel,
                style = KhazenaTheme.typography.labelMSemiBold.copy(textDecoration = TextDecoration.Underline),
                color = colors.textPrimary,
                modifier = Modifier.clickable(role = Role.Button, onClick = onLinkClick),
            )
        }
    }
}

@Immutable
data class TabBarItem(val label: String, val icon: DrawableResource, val selectedIcon: DrawableResource)

@Composable
fun KhazenaTabBar(
    items: List<TabBarItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    actionIcon: DrawableResource,
    actionLabel: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = KhazenaTheme.colors
    val paper = colors.surfaceBackground
    Row(
        modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    0f to paper.copy(alpha = 0f),
                    0.7f to paper.copy(alpha = 0.85f),
                    1f to paper.copy(alpha = 0.95f),
                ),
            )
            .navigationBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            Modifier
                .weight(1f)
                .height(62.dp)
                .dropShadow(KhazenaRadius.pill, Shadow(radius = 30.dp, offset = DpOffset(0.dp, 10.dp), color = TabBarShadow))
                .clip(KhazenaRadius.pill)
                .background(colors.surfaceCard)
                .innerShadow(KhazenaRadius.pill, Shadow(radius = 2.dp, offset = DpOffset(0.dp, (-1).dp), color = TabBarBevel))
                .innerShadow(KhazenaRadius.pill, Shadow(radius = 1.dp, offset = DpOffset(0.dp, 1.5.dp), color = Color.White.copy(alpha = 0.9f)))
                .border(1.dp, Color.White.copy(alpha = 0.85f), KhazenaRadius.pill)
                .padding(6.dp)
                .selectableGroup(),
        ) {
            items.forEachIndexed { index, item ->
                val selected = index == selectedIndex
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(KhazenaRadius.pill)
                        .background(if (selected) colors.blockSun else Color.Transparent)
                        .selectable(selected = selected, role = Role.Tab) { onSelect(index) },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painterResource(if (selected) item.selectedIcon else item.icon),
                        item.label,
                        Modifier.size(24.dp),
                        tint = if (selected) colors.textPrimary else colors.iconMuted,
                    )
                }
            }
        }
        Spacer(Modifier.width(10.dp))
        Box(
            Modifier
                .size(62.dp)
                .clip(KhazenaRadius.pill)
                .background(colors.actionPrimary)
                .clickable(role = Role.Button, onClick = onAction),
            contentAlignment = Alignment.Center,
        ) {
            Icon(painterResource(actionIcon), actionLabel, Modifier.size(26.dp), tint = colors.actionOnPrimary)
        }
    }
}

private val TabBarShadow = Color(0x240D0D1A)
private val TabBarBevel = Color(0x0F0D0D1A)

@Preview(name = "Section header", widthDp = 390)
@Composable
private fun SectionHeaderPreview() = PreviewSurface {
    KhazenaSectionHeader("Highest discounts.", linkLabel = "See all")
    KhazenaSectionHeader("Near you.")
}

@Preview(name = "Tab bar", widthDp = 390)
@Composable
private fun TabBarPreview() = PreviewSurface {
    Box(Modifier.fillMaxWidth().height(120.dp)) {
        KhazenaTabBar(
            items = listOf(
                TabBarItem("Home", KhazenaIcons.House, KhazenaIcons.HouseFill),
                TabBarItem("Search", KhazenaIcons.MagnifyingGlass, KhazenaIcons.MagnifyingGlassFill),
                TabBarItem("Wallet", KhazenaIcons.Wallet, KhazenaIcons.WalletFill),
                TabBarItem("Profile", KhazenaIcons.User, KhazenaIcons.UserFill),
            ),
            selectedIndex = 0,
            onSelect = {},
            actionIcon = KhazenaIcons.QrCode,
            actionLabel = "Scan",
            onAction = {},
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}
@Preview(name = "Navigation · RTL", widthDp = 390)
@Composable
private fun NavigationRtlPreview() = RtlPreviewSurface {
    KhazenaSectionHeader("زۆرترین داشکان.", linkLabel = "هەمووی ببینە")
    Box(Modifier.fillMaxWidth().height(120.dp)) {
        KhazenaTabBar(
            items = listOf(
                TabBarItem("سەرەکی", KhazenaIcons.House, KhazenaIcons.HouseFill),
                TabBarItem("گەڕان", KhazenaIcons.MagnifyingGlass, KhazenaIcons.MagnifyingGlassFill),
                TabBarItem("جزدان", KhazenaIcons.Wallet, KhazenaIcons.WalletFill),
                TabBarItem("پرۆفایل", KhazenaIcons.User, KhazenaIcons.UserFill),
            ),
            selectedIndex = 0,
            onSelect = {},
            actionIcon = KhazenaIcons.Scan,
            actionLabel = "سکان",
            onAction = {},
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}
