package com.khazena.khazena.core.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import org.jetbrains.compose.resources.painterResource

private val SearchFieldShape = RoundedCornerShape(18.dp)

/** Search field: 52 tall, white, radius 18, hairline border; Ink 1.5 border when focused. Clear button once filled. */
@Composable
fun KhazenaSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    clearContentDescription: String,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit = {},
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
                    .height(52.dp)
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

/** Pill segmented control, 48 tall; selected segment = Ink. */
@Composable
fun KhazenaSegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = KhazenaTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(KhazenaRadius.pill)
            .background(colors.surfaceTile)
            .padding(4.dp)
            .selectableGroup(),
    ) {
        options.forEachIndexed { index, label ->
            val selected = index == selectedIndex
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(KhazenaRadius.pill)
                    .background(if (selected) colors.actionSelected else colors.surfaceTile)
                    .selectable(selected = selected, role = Role.Tab) { onSelect(index) },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    label,
                    style = KhazenaTheme.typography.labelMSemiBold,
                    color = if (selected) colors.actionOnSelected else colors.textSecondary,
                    maxLines = 1,
                )
            }
        }
    }
}

/** 51×31 switch: Ink track when on, Strong border colour when off, white 27pt knob. */
@Composable
fun KhazenaToggle(checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val track by animateColorAsState(if (checked) colors.actionSelected else colors.borderStrong)
    val knobX: Dp by animateDpAsState(if (checked) 22.dp else 2.dp)
    Box(
        modifier
            .minimumInteractiveComponentSize()
            .size(width = 51.dp, height = 31.dp)
            .clip(KhazenaRadius.pill)
            .background(track)
            .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange),
    ) {
        Box(Modifier.offset(x = knobX, y = 2.dp).size(27.dp).background(colors.surfaceCard, CircleShape))
    }
}

/** 28pt radio: Ink disc with a check when on, 2pt Strong ring when off. */
@Composable
fun KhazenaRadio(selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    SelectionMark(
        on = selected,
        size = 28.dp,
        shape = CircleShape,
        offBackground = null,
        modifier = modifier.selectable(selected = selected, role = Role.RadioButton, onClick = onClick),
    )
}

/** 24pt checkbox, radius 7: Ink with a check when on, white with 2pt Strong border when off. */
@Composable
fun KhazenaCheckbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    SelectionMark(
        on = checked,
        size = 24.dp,
        shape = RoundedCornerShape(7.dp),
        offBackground = KhazenaTheme.colors.surfaceCard,
        modifier = modifier.toggleable(value = checked, role = Role.Checkbox, onValueChange = onCheckedChange),
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

/** Rating input tile, 48pt; Sun when selected. Use five in a row. */
@Composable
fun KhazenaStarTile(selected: Boolean, contentDescription: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    Box(
        modifier
            .size(48.dp)
            .clip(KhazenaRadius.sm)
            .background(if (selected) colors.blockSun else colors.surfaceBackground)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick),
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

/** Member = Violet; OnColour = white with Violet initials (on colour blocks); Expired = Paper with Ink initials. */
enum class AvatarStyle { Member, OnColour, Expired }

/** Initials avatar, 44 or 52. */
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
