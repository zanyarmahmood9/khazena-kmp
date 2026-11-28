package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSize
import com.khazena.khazena.core.designsystem.KhazenaTheme

enum class TagType { Discount, Live, Verified, Expired, Highlight, OnColourBlock }

enum class TagSize(internal val height: Dp, internal val paddingX: Dp) {
    S(KhazenaSize.tag, 10.dp),
    M(28.dp, 12.dp),
}

@Composable
fun KhazenaTag(
    label: String,
    modifier: Modifier = Modifier,
    type: TagType = TagType.Discount,
    contentColor: Color? = null,
    size: TagSize = TagSize.S,
    paddingX: Dp = size.paddingX,
) {
    val colors = KhazenaTheme.colors
    val container = when (type) {
        TagType.Discount -> colors.tagDiscount
        TagType.Live, TagType.Verified, TagType.Expired -> colors.tagStatus
        TagType.Highlight -> colors.tagHighlight
        TagType.OnColourBlock -> colors.surfaceCard
    }
    val dot = when (type) {
        TagType.Live -> colors.statusLive
        TagType.Verified -> colors.statusVerified
        TagType.Expired -> colors.statusExpired
        else -> null
    }
    Row(
        modifier = modifier
            .height(size.height)
            .clip(KhazenaRadius.pill)
            .background(container)
            .padding(start = if (dot != null) paddingX - 1.dp else paddingX, end = paddingX),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (dot != null) Box(Modifier.size(6.dp).background(dot, CircleShape))
        Text(
            text = label.uppercase(),
            style = if (size == TagSize.M) KhazenaTheme.typography.monoTagS.copy(fontSize = 12.sp) else KhazenaTheme.typography.monoTagS,
            color = contentColor ?: if (type == TagType.Highlight) colors.textOnDark else colors.textPrimary,
            maxLines = 1,
        )
    }
}

@Composable
fun KhazenaChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    count: Int? = null,
) {
    val colors = KhazenaTheme.colors
    val haptics = LocalHapticFeedback.current
    Row(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .height(KhazenaSize.chip)
            .clip(KhazenaRadius.pill)
            .background(if (selected) colors.actionSelected else colors.surfaceCard)
            .selectable(selected = selected, role = Role.Tab) {
                haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                onClick()
            }
            .padding(start = 16.dp, end = if (count != null) 6.dp else 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = KhazenaTheme.typography.labelSMedium,
            color = if (selected) colors.actionOnSelected else colors.textPrimary,
            maxLines = 1,
        )
        if (count != null) {
            Box(
                Modifier
                    .height(22.dp)
                    .clip(KhazenaRadius.pill)
                    .background(if (selected) colors.textSecondary else colors.surfaceTile)
                    .padding(horizontal = 7.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = count.toString(),
                    style = KhazenaTheme.typography.labelXSMedium,
                    color = if (selected) colors.actionOnSelected else colors.textSecondary,
                )
            }
        }
    }
}

@Preview(name = "Tag", widthDp = 390)
@Composable
private fun TagPreview() = PreviewSurface {
    TagType.entries.forEach { KhazenaTag(it.name, type = it) }
}

@Preview(name = "Chip", widthDp = 390)
@Composable
private fun ChipPreview() = PreviewSurface {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        KhazenaChip("All", selected = false, onClick = {})
        KhazenaChip("Grills", selected = true, onClick = {}, count = 9)
        KhazenaChip("Cafés", selected = false, onClick = {})
    }
}
@Preview(name = "Tags · RTL", widthDp = 390)
@Composable
private fun TagsRtlPreview() = RtlPreviewSurface {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        KhazenaTag("چالاک", type = TagType.Live)
        KhazenaTag("٣٠٪ داشکان")
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        KhazenaChip("هەموو", selected = false, onClick = {})
        KhazenaChip("گریل", selected = true, onClick = {}, count = 9)
    }
}
