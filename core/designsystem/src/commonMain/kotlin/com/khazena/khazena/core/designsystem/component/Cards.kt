package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSize
import com.khazena.khazena.core.designsystem.KhazenaTheme
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun KhazenaPlaceRow(
    photo: Painter,
    title: String,
    meta: String,
    modifier: Modifier = Modifier,
    discountLabel: String? = null,
) {
    CardRow(photo, modifier) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = KhazenaTheme.typography.labelLBold, color = KhazenaTheme.colors.textPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(meta, style = KhazenaTheme.typography.labelSMedium, color = KhazenaTheme.colors.textSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        if (discountLabel != null) KhazenaTag(discountLabel)
    }
}

@Composable
fun KhazenaMenuItem(
    photo: Painter,
    title: String,
    description: String,
    price: String,
    modifier: Modifier = Modifier,
    originalPrice: String? = null,
    savingLabel: String? = null,
) {
    val colors = KhazenaTheme.colors
    CardRow(photo, modifier) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = KhazenaTheme.typography.labelLBold, color = colors.textPrimary)
            Text(description, style = KhazenaTheme.typography.bodyS, color = colors.textSecondary)
            Row(
                Modifier.fillMaxWidth().padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(price, style = KhazenaTheme.typography.labelPrice, color = colors.textPrimary)
                if (originalPrice != null) {
                    Text(originalPrice, style = KhazenaTheme.typography.labelXSMedium, color = colors.textPlaceholder)
                }
                Spacer(Modifier.weight(1f))
                if (savingLabel != null) KhazenaTag(savingLabel)
            }
        }
    }
}

@Composable
private fun CardRow(photo: Painter, modifier: Modifier, content: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .clip(KhazenaRadius.card)
            .background(KhazenaTheme.colors.surfaceCard)
            .padding(11.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(photo, null, Modifier.size(KhazenaSize.thumb).clip(KhazenaRadius.sm), contentScale = ContentScale.Crop)
        content()
    }
}

@Composable
fun KhazenaCategoryTile(art: Painter, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier.size(80.dp).clip(KhazenaRadius.tile).background(KhazenaTheme.colors.surfaceCard),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(art, null, Modifier.size(46.dp), contentScale = ContentScale.Crop)
        Text(label, style = KhazenaTheme.typography.labelXSMedium, color = KhazenaTheme.colors.textPrimary, textAlign = TextAlign.Center, maxLines = 1)
    }
}

@Composable
fun KhazenaDealCard(
    photo: Painter,
    title: String,
    meta: String,
    discountLabel: String,
    ratingLabel: String,
    actionLabel: String,
    modifier: Modifier = Modifier,
) {
    val colors = KhazenaTheme.colors
    Column(modifier.width(232.dp).clip(KhazenaRadius.block).background(colors.blockInk)) {
        Box(Modifier.fillMaxWidth().height(164.dp)) {
            Image(photo, null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Row(
                Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp, top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                KhazenaTag(discountLabel)
                KhazenaTag(ratingLabel, type = TagType.OnColourBlock)
            }
        }
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, style = KhazenaTheme.typography.labelXLBold, color = colors.textOnDark, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(meta.uppercase(), style = KhazenaTheme.typography.monoKicker, color = colors.textOnDark, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(actionLabel, style = KhazenaTheme.typography.labelMSemiBold, color = colors.blockSun)
        }
    }
}

enum class FactKind { Rating, Distance, Hours }

@Composable
fun KhazenaFactChip(kind: FactKind, label: String, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val (icon, tint) = when (kind) {
        FactKind.Rating -> KhazenaIcons.StarFill to colors.iconStar
        FactKind.Distance -> KhazenaIcons.MapPin to colors.textPrimary
        FactKind.Hours -> KhazenaIcons.Clock to colors.textPrimary
    }
    Row(
        modifier.height(28.dp).clip(KhazenaRadius.pill).background(colors.surfaceBackground).padding(start = 10.dp, end = 11.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(painterResource(icon), null, Modifier.size(14.dp), tint = tint)
        Text(label.uppercase(), style = KhazenaTheme.typography.monoTagS, color = colors.textPrimary, maxLines = 1)
    }
}

@Composable
private fun previewPhoto() = ColorPainter(KhazenaTheme.colors.borderStrong)

@Preview(name = "Place row", widthDp = 390)
@Composable
private fun PlaceRowPreview() = PreviewSurface {
    KhazenaPlaceRow(previewPhoto(), "Naz Café", "Café · 0.6 km", discountLabel = "Save 10%")
    KhazenaPlaceRow(previewPhoto(), "Shifa Pharmacy", "Pharmacy · 0.4 km")
}

@Preview(name = "Menu item", widthDp = 390)
@Composable
private fun MenuItemPreview() = PreviewSurface {
    KhazenaMenuItem(previewPhoto(), "Chicken Kebab Platter", "Two skewers, rice, grilled veg and fresh bread", "8,400 IQD", originalPrice = "12,000", savingLabel = "−3,600")
    KhazenaMenuItem(previewPhoto(), "Red Lentil Soup", "With lemon and warm bread", "4,000 IQD")
}

@Preview(name = "Category tile", widthDp = 390)
@Composable
private fun CategoryTilePreview() = PreviewSurface {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        KhazenaCategoryTile(previewPhoto(), "Food & Cafes")
        KhazenaCategoryTile(previewPhoto(), "Beauty")
    }
}

@Preview(name = "Deal card", widthDp = 390)
@Composable
private fun DealCardPreview() = PreviewSurface {
    KhazenaDealCard(previewPhoto(), "Azmar Grill", "Grill · Salim St", "30% off", "★ 4.8", "Redeem in store →")
}

@Preview(name = "Fact chip", widthDp = 390)
@Composable
private fun FactChipPreview() = PreviewSurface {
    KhazenaFactChip(FactKind.Rating, "4.8 (126)")
    KhazenaFactChip(FactKind.Distance, "0.6 km")
    KhazenaFactChip(FactKind.Hours, "Open · 11pm")
}