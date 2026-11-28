package com.khazena.khazena.core.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaMotion
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.pressScale
import org.jetbrains.compose.resources.painterResource

private val SearchFieldShape = RoundedCornerShape(18.dp)

@Composable
fun KhazenaSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    clearContentDescription: String,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit = {},
    height: Dp = 52.dp,
) {
    val colors = KhazenaTheme.colors
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = KhazenaTheme.typography.labelMSemiBold.copy(color = colors.textPrimary),
        cursorBrush = SolidColor(colors.textPrimary),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = androidx.compose.foundation.text.KeyboardActions(onSearch = { onSearch() }),
        interactionSource = interaction,
        decorationBox = { innerField ->
            Row(
                modifier = Modifier
                    .height(height)
                    .clip(SearchFieldShape)
                    .background(colors.surfaceCard)
                    .border(
                        if (focused) 1.5.dp else 1.dp,
                        if (focused) colors.borderSelected else colors.borderDefault,
                        SearchFieldShape,
                    )
                    .padding(start = 18.dp, end = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(painterResource(KhazenaIcons.MagnifyingGlass), null, Modifier.size(20.dp), tint = colors.textPrimary)
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(placeholder, style = KhazenaTheme.typography.bodyM, color = colors.textPlaceholder, maxLines = 1)
                    }
                    innerField()
                }
                if (value.isNotEmpty()) {
                    Box(
                        Modifier
                            .minimumInteractiveComponentSize()
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(colors.borderStrong)
                            .clickable(role = Role.Button) { onValueChange("") },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(painterResource(KhazenaIcons.X), clearContentDescription, Modifier.size(12.dp), tint = colors.surfaceCard)
                    }
                }
            }
        },
    )
}

// iOS-style segmented control: one selection pill slides (with a soft spring) to the tapped segment, mirrored in
// RTL; labels cross-fade their colour as it passes, and a pressed label dims and shrinks a touch.
@Composable
fun KhazenaSegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = KhazenaTheme.colors
    val haptics = LocalHapticFeedback.current
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(KhazenaRadius.pill)
            .background(colors.surfaceTile)
            .padding(4.dp),
    ) {
        val segmentWidth = maxWidth / options.size.coerceAtLeast(1)
        val thumbOffset by animateDpAsState(
            targetValue = segmentWidth * selectedIndex,
            animationSpec = spring(dampingRatio = 0.82f, stiffness = Spring.StiffnessMediumLow),
            label = "segment-thumb",
        )
        Box(
            Modifier
                .offset(x = thumbOffset)
                .width(segmentWidth)
                .fillMaxHeight()
                .shadow(2.dp, KhazenaRadius.pill, clip = false)
                .clip(KhazenaRadius.pill)
                .background(colors.actionSelected),
        )
        Row(Modifier.fillMaxSize().selectableGroup()) {
            options.forEachIndexed { index, label ->
                val selected = index == selectedIndex
                val interaction = remember { MutableInteractionSource() }
                val pressed by interaction.collectIsPressedAsState()
                val textColor by animateColorAsState(
                    if (selected) colors.actionOnSelected else colors.textSecondary,
                    tween(KhazenaMotion.MEDIUM),
                    label = "segment-label",
                )
                val pressAlpha by animateFloatAsState(if (pressed && !selected) 0.55f else 1f, tween(KhazenaMotion.SHORT), label = "segment-press")
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .selectable(selected = selected, interactionSource = interaction, indication = null, role = Role.Tab) {
                            if (!selected) haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                            onSelect(index)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    // Figma: 13pt, semibold on the selected segment, medium on the others.
                    Text(
                        label,
                        style = KhazenaTheme.typography.labelSMedium.copy(fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium),
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .pressScale(interaction, pressedScale = 0.94f)
                            .graphicsLayer { alpha = pressAlpha },
                    )
                }
            }
        }
    }
}

@Composable
fun KhazenaToggle(checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val track by animateColorAsState(if (checked) colors.actionSelected else colors.borderStrong)
    val knobX: Dp by animateDpAsState(if (checked) 22.dp else 2.dp)
    val haptics = LocalHapticFeedback.current
    Box(
        modifier
            .minimumInteractiveComponentSize()
            .size(width = 51.dp, height = 31.dp)
            .clip(KhazenaRadius.pill)
            .background(track)
            .toggleable(value = checked, role = Role.Switch) {
                haptics.performHapticFeedback(if (it) HapticFeedbackType.ToggleOn else HapticFeedbackType.ToggleOff)
                onCheckedChange(it)
            },
    ) {
        Box(Modifier.offset(x = knobX, y = 2.dp).size(27.dp).background(colors.surfaceCard, CircleShape))
    }
}

/** 28pt radio: Ink disc with a check when on, 2pt Strong ring when off. */
// Round press feedback around the mark instead of a square filling the 48dp touch target.
private val MarkRipple = ripple(bounded = false, radius = 20.dp)

@Composable
fun KhazenaRadio(selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val haptics = LocalHapticFeedback.current
    SelectionMark(
        on = selected,
        size = 28.dp,
        shape = CircleShape,
        offBackground = null,
        modifier = modifier.selectable(selected = selected, interactionSource = null, indication = MarkRipple, role = Role.RadioButton) {
            if (!selected) haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
            onClick()
        },
    )
}

@Composable
fun KhazenaCheckbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    val haptics = LocalHapticFeedback.current
    SelectionMark(
        on = checked,
        size = 24.dp,
        shape = RoundedCornerShape(7.dp),
        offBackground = KhazenaTheme.colors.surfaceCard,
        modifier = modifier.toggleable(value = checked, interactionSource = null, indication = MarkRipple, role = Role.Checkbox) {
            haptics.performHapticFeedback(if (it) HapticFeedbackType.ToggleOn else HapticFeedbackType.ToggleOff)
            onCheckedChange(it)
        },
    )
}

@Composable
private fun SelectionMark(
    on: Boolean,
    size: Dp,
    shape: androidx.compose.ui.graphics.Shape,
    offBackground: androidx.compose.ui.graphics.Color?,
    modifier: Modifier,
) {
    val colors = KhazenaTheme.colors
    Box(modifier.minimumInteractiveComponentSize(), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(size)
                .clip(shape)
                .then(
                    when {
                        on -> Modifier.background(colors.actionSelected)
                        else -> Modifier
                            .then(if (offBackground != null) Modifier.background(offBackground) else Modifier)
                            .border(2.dp, colors.borderStrong, shape)
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (on) Icon(painterResource(KhazenaIcons.Check), null, Modifier.size(14.dp), tint = colors.actionOnSelected)
        }
    }
}

@Composable
fun KhazenaStarTile(selected: Boolean, contentDescription: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val haptics = LocalHapticFeedback.current
    Box(
        modifier
            .size(48.dp)
            .clip(KhazenaRadius.sm)
            .background(if (selected) colors.blockSun else colors.surfaceBackground)
            .selectable(selected = selected, role = Role.RadioButton) {
                haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painterResource(KhazenaIcons.StarFill),
            contentDescription,
            Modifier.size(24.dp),
            tint = if (selected) colors.textPrimary else colors.borderStrong,
        )
    }
}

enum class AvatarStyle { Member, OnColour, Expired }

@Composable
fun KhazenaAvatar(
    initials: String,
    modifier: Modifier = Modifier,
    style: AvatarStyle = AvatarStyle.Member,
    large: Boolean = false,
) {
    val colors = KhazenaTheme.colors
    val (container, content) = when (style) {
        AvatarStyle.Member -> colors.blockViolet to colors.textOnViolet
        AvatarStyle.OnColour -> colors.surfaceCard to colors.blockViolet
        AvatarStyle.Expired -> colors.surfaceBackground to colors.textPrimary
    }
    Box(
        modifier.size(if (large) 52.dp else 44.dp).background(container, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            initials,
            style = if (large) KhazenaTheme.typography.labelAvatarL else KhazenaTheme.typography.labelAvatarM,
            color = content,
        )
    }
}

@Preview(name = "Search field", widthDp = 390)
@Composable
private fun SearchFieldPreview() = PreviewSurface {
    KhazenaSearchField("", {}, "Search restaurants, salons, services…", "Clear")
    KhazenaSearchField("kebab", {}, "Search restaurants, salons, services…", "Clear")
}

@Preview(name = "Segmented control", widthDp = 390)
@Composable
private fun SegmentedControlPreview() = PreviewSurface {
    KhazenaSegmentedControl(listOf("Menu", "Reviews · 126", "Info"), 1, {})
}

@Preview(name = "Toggle · radio · checkbox", widthDp = 390)
@Composable
private fun SelectionControlsPreview() = PreviewSurface {
    listOf(true, false).forEach { on ->
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            KhazenaToggle(on, {})
            KhazenaRadio(on, {})
            KhazenaCheckbox(on, {})
        }
    }
}

@Preview(name = "Star tile", widthDp = 390)
@Composable
private fun StarTilePreview() = PreviewSurface {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        (1..5).forEach { KhazenaStarTile(it <= 4, "$it stars", {}) }
    }
}

@Preview(name = "Avatar", widthDp = 390)
@Composable
private fun AvatarPreview() = PreviewSurface {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        AvatarStyle.entries.forEach {
            KhazenaAvatar("BK", style = it)
            KhazenaAvatar("BK", style = it, large = true)
        }
    }
}
@Preview(name = "Inputs · RTL", widthDp = 390)
@Composable
private fun InputsRtlPreview() = RtlPreviewSurface {
    KhazenaSearchField("", {}, "گەڕان بۆ چێشتخانە، ساڵۆن، خزمەتگوزاری…", "سڕینەوە")
    KhazenaSearchField("کەباب", {}, "گەڕان", "سڕینەوە")
    KhazenaSegmentedControl(listOf("مینیو", "هەڵسەنگاندن · ١٢٦", "زانیاری"), 0, {})
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        KhazenaToggle(true, {})
        KhazenaToggle(false, {})
        KhazenaRadio(true, {})
        KhazenaCheckbox(true, {})
    }
}
