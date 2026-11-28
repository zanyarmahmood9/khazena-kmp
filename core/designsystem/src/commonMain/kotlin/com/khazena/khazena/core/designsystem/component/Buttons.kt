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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSize
import com.khazena.khazena.core.designsystem.KhazenaTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.tooling.preview.Preview

enum class ButtonVariant { Primary, Dark, Secondary, Ghost, Danger }

enum class ButtonSize(internal val height: Dp, internal val paddingX: Dp, internal val iconSize: Dp, internal val gap: Dp) {
    L(KhazenaSize.button, 28.dp, 20.dp, 8.dp),
    M(KhazenaSize.buttonSm, 20.dp, 18.dp, 8.dp),
    Compact(42.dp, 16.dp, 18.dp, 6.dp),
    S(32.dp, 14.dp, 14.dp, 4.dp),
}

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
        variant == ButtonVariant.Danger -> colors.statusDanger to colors.surfaceCard
        else -> Color.Transparent to colors.textPrimary
    }
    val type = KhazenaTheme.typography
    val textStyle = when (size) {
        ButtonSize.L -> type.buttonL
        ButtonSize.M -> type.buttonM
        ButtonSize.Compact -> type.buttonM.copy(fontSize = 15.sp)
        ButtonSize.S -> type.buttonM.copy(fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
    Row(
        modifier = modifier
            .height(size.height)
            .clip(KhazenaRadius.pill)
            .background(container)
            .then(
                if (variant == ButtonVariant.Secondary && enabled) {
                    Modifier.border(1.dp, colors.borderDefault, KhazenaRadius.pill)
                } else Modifier,
            )
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(PaddingValues(horizontal = size.paddingX)),
        horizontalArrangement = Arrangement.spacedBy(size.gap, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = textStyle,
            color = content,
            maxLines = 1,
        )
        if (icon != null) {
            Icon(painterResource(icon), null, Modifier.size(size.iconSize), tint = content)
        }
    }
}

enum class IconButtonStyle { Paper, White, Ink, Ember }

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

@Preview(name = "Button · variants", widthDp = 390)
@Composable
private fun ButtonVariantsPreview() = PreviewSurface {
    ButtonVariant.entries.forEach {
        KhazenaButton(it.name, {}, Modifier.fillMaxWidth(), variant = it, icon = KhazenaIcons.QrCode)
    }
    KhazenaButton("Disabled", {}, Modifier.fillMaxWidth(), enabled = false)
}

@Preview(name = "Button · sizes", widthDp = 390)
@Composable
private fun ButtonSizesPreview() = PreviewSurface {
    ButtonSize.entries.forEach {
        KhazenaButton("${it.name} · ${it.height.value.toInt()}", {}, size = it, icon = KhazenaIcons.Export)
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        KhazenaButton("Export", {}, variant = ButtonVariant.Secondary, size = ButtonSize.Compact, icon = KhazenaIcons.Export)
        KhazenaButton("See all 126", {}, variant = ButtonVariant.Secondary, size = ButtonSize.S)
    }
}

@Preview(name = "Icon button", widthDp = 390)
@Composable
private fun IconButtonPreview() = PreviewSurface {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        IconButtonStyle.entries.forEach { KhazenaIconButton(KhazenaIcons.ArrowLeft, "Back", {}, style = it) }
    }
}
@Preview(name = "Buttons · RTL", widthDp = 390)
@Composable
private fun ButtonsRtlPreview() = RtlPreviewSurface {
    KhazenaButton("سکان بکە و پاشەکەوت بکە", {}, Modifier.fillMaxWidth(), icon = KhazenaIcons.QrCode)
    KhazenaButton("دواتر", {}, variant = ButtonVariant.Secondary, size = ButtonSize.M, icon = KhazenaIcons.ArrowRight)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        IconButtonStyle.entries.forEach { KhazenaIconButton(KhazenaIcons.ArrowLeft, "گەڕانەوە", {}, style = it) }
    }
}
