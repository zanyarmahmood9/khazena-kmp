package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSize
import com.khazena.khazena.core.designsystem.KhazenaTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/** Primary = Ember with Ink label, one per screen. Dark = Ink, for actions on Sun/Ember blocks. Secondary = white with hairline. Ghost = text link. */
enum class ButtonVariant { Primary, Dark, Secondary, Ghost }

/** L = 56 tall, M = 44 tall. */
enum class ButtonSize { L, M }

@Composable
fun KhazenaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Primary,
    size: ButtonSize = ButtonSize.L,
    icon: DrawableResource? = null,
    enabled: Boolean = true,
) {
    val colors = KhazenaTheme.colors
    val (container, content) = when {
        !enabled -> colors.actionDisabled to colors.textSecondary
        variant == ButtonVariant.Primary -> colors.actionPrimary to colors.actionOnPrimary
        variant == ButtonVariant.Dark -> colors.blockInk to colors.textOnDark
        variant == ButtonVariant.Secondary -> colors.surfaceCard to colors.textPrimary
        else -> Color.Transparent to colors.textPrimary
    }
    val large = size == ButtonSize.L
    Row(
        modifier = modifier
            .height(if (large) KhazenaSize.button else KhazenaSize.buttonSm)
            .clip(KhazenaRadius.pill)
            .background(container)
            .then(
                if (variant == ButtonVariant.Secondary && enabled) {
                    Modifier.border(1.dp, colors.borderDefault, KhazenaRadius.pill)
                } else Modifier,
            )
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(PaddingValues(horizontal = if (large) 28.dp else 20.dp)),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(painterResource(icon), null, Modifier.size(if (large) 20.dp else 18.dp), tint = content)
        }
        Text(
            text = text,
            style = if (large) KhazenaTheme.typography.buttonL else KhazenaTheme.typography.buttonM,
            color = content,
            maxLines = 1,
        )
    }
}

enum class IconButtonStyle { Paper, White, Ink, Ember }

/** 44pt circular header button (back, bell, close, share). Flat: no blur or shadow. */
@Composable
fun KhazenaIconButton(
    icon: DrawableResource,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: IconButtonStyle = IconButtonStyle.Paper,
) {
    val colors = KhazenaTheme.colors
    val (container, content) = when (style) {
        IconButtonStyle.Paper -> colors.actionSecondary to colors.textPrimary
        IconButtonStyle.White -> colors.surfaceCard to colors.textPrimary
        IconButtonStyle.Ink -> colors.blockInk to colors.textOnDark
        IconButtonStyle.Ember -> colors.actionPrimary to colors.actionOnPrimary
    }
    Box(
        modifier = modifier
            .size(KhazenaSize.tapMin)
            .clip(KhazenaRadius.pill)
            .background(container)
            .clickable(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(painterResource(icon), contentDescription, Modifier.size(20.dp), tint = content)
    }
}
