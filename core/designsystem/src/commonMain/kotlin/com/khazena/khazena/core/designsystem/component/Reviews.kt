package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import org.jetbrains.compose.resources.painterResource

// A row of five stars, [rating] of them filled; read out as one label.
@Composable
fun KhazenaStars(rating: Int, description: String, modifier: Modifier = Modifier, size: Dp = 12.dp) {
    val colors = KhazenaTheme.colors
    Row(modifier.clearAndSetSemantics { contentDescription = description }, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(5) { index ->
            Icon(
                painterResource(KhazenaIcons.StarSmall),
                null,
                Modifier.size(size),
                tint = if (index < rating) colors.iconStar else colors.borderDefault,
            )
        }
    }
}

// Figma "Review card": initials avatar, name, verified-visit line, stars, the review and what the visit saved.
// Reviews only come from verified (scanned) visits, so the verified line is always shown.
@Composable
fun KhazenaReviewCard(
    initials: String,
    avatarColor: Color,
    name: String,
    verifiedLine: String,
    rating: Int,
    ratingDescription: String,
    text: String,
    savedLabel: String,
    modifier: Modifier = Modifier,
) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Column(
        modifier.fillMaxWidth().clip(KhazenaRadius.card).background(colors.surfaceCard).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(38.dp).background(avatarColor, CircleShape), contentAlignment = Alignment.Center) {
                Text(initials, style = type.labelMSemiBold, color = colors.textPrimary, maxLines = 1)
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(name, style = type.labelMSemiBold, color = colors.textPrimary, maxLines = 1)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(KhazenaIcons.CheckLine), null, Modifier.size(11.dp), tint = colors.statusLive)
                    Text(verifiedLine, style = type.bodyS.copy(fontSize = 11.sp), color = colors.textSecondary, maxLines = 1)
                }
            }
            KhazenaStars(rating, ratingDescription)
        }
        Text(text, style = type.bodyS.copy(lineHeight = 19.sp), color = colors.textPrimary)
        Row(Modifier.fillMaxWidth()) {
            Spacer(Modifier.weight(1f))
            Text(
                savedLabel,
                style = type.monoTagS.copy(letterSpacing = 0.sp),
                color = colors.textPrimary,
                maxLines = 1,
                modifier = Modifier.clip(KhazenaRadius.pill).background(colors.tagDiscount).padding(horizontal = 10.dp, vertical = 4.dp),
            )
        }
    }
}

@Preview(name = "Review card", widthDp = 390)
@Composable
private fun ReviewCardPreview() = PreviewSurface {
    KhazenaReviewCard(
        initials = "RA",
        avatarColor = KhazenaTheme.colors.blockViolet,
        name = "Roam A.",
        verifiedLine = "Verified visit · 2 days ago",
        rating = 5,
        ratingDescription = "5 out of 5",
        text = "Best chicken kebab in Slemani. The 30% was applied the second the cashier scanned my code — no questions asked.",
        savedLabel = "Saved 7,200 IQD",
    )
}

@Preview(name = "Stars", widthDp = 390)
@Composable
private fun StarsPreview() = PreviewSurface { KhazenaStars(4, "4 out of 5", size = 13.dp) }
