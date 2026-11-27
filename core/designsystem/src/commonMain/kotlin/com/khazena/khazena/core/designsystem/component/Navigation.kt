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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/** H3 section title with an optional "See all"-style link on the right. */
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
                style = KhazenaTheme.typography.labelMSemiBold,
                color = colors.textPrimary,
                modifier = Modifier.clickable(role = Role.Button, onClick = onLinkClick),
            )
        }
    }
}

/** One tab: outline icon, filled icon when active, and the accessibility label (tabs are icon-only). */
@Immutable
data class TabBarItem(val label: String, val icon: DrawableResource, val selectedIcon: DrawableResource)

/**
 * Floating icon-only tab bar: white pill of tabs (active = Sun pill + filled icon) and a round Ember
 * action button beside it (Scan). Sits over content with a Paper fade, so draw it at the bottom of a Box.
 */
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
                .border(1.dp, colors.surfaceCard, KhazenaRadius.pill)
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

private val TabBarShadow = Color(0x240D0D1A) // rgba(13,13,26,0.14)
