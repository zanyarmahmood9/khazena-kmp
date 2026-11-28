package com.khazena.khazena.feature.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import khazena.feature.home.presentation.generated.resources.Res
import khazena.feature.home.presentation.generated.resources.home_location_off_body
import khazena.feature.home.presentation.generated.resources.home_location_off_tag
import khazena.feature.home.presentation.generated.resources.home_location_off_title
import khazena.feature.home.presentation.generated.resources.home_pin_off
import khazena.feature.home.presentation.generated.resources.home_turn_on_location
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// Figma "Near you · Location off" (504:520): shown in place of the nearby list while location isn't allowed.
@Composable
internal fun LocationOffCard(onTurnOn: () -> Unit, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Column(
        modifier
            .fillMaxWidth()
            .clip(KhazenaRadius.card)
            .background(colors.surfaceCard)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 18.dp),
    ) {
        LocationOffGraphic()
        Column(Modifier.padding(top = 18.dp, start = 8.dp, end = 8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                stringResource(Res.string.home_location_off_title),
                style = type.headingH3.copy(lineHeight = TextUnit.Unspecified, letterSpacing = if (type.headingH3.letterSpacing.value == 0f) 0.sp else (-0.03).em),
                color = colors.textPrimary,
            )
            Text(
                stringResource(Res.string.home_location_off_body),
                style = type.bodyM.copy(fontSize = 14.sp, lineHeight = 1.4.em),
                color = colors.textSecondary,
            )
            Spacer(Modifier.height(10.dp))
            KhazenaButton(
                stringResource(Res.string.home_turn_on_location),
                onTurnOn,
                Modifier.fillMaxWidth(),
                icon = KhazenaIcons.Crosshair,
            )
        }
    }
}

// The art keeps Figma's 330×150 coordinates (element centres below) and centres in whatever width the card gets.
@Composable
private fun LocationOffGraphic() {
    val colors = KhazenaTheme.colors
    Box(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colors.blockViolet)
            .clearAndSetSemantics {},
    ) {
        Box(Modifier.align(Alignment.Center).requiredSize(330.dp, 150.dp)) {
            MapTile(Modifier.centredAt(159.88.dp, 88.2.dp, 170.dp, 110.dp).rotate(5f))
            OffPin(Modifier.centredAt(165.dp, 70.dp, 60.dp, 60.dp))
            Box(Modifier.centredAt(72.56.dp, 117.13.dp).rotate(-6f)) {
                Text(
                    stringResource(Res.string.home_location_off_tag).uppercase(),
                    style = KhazenaTheme.typography.monoKicker.copy(lineHeight = TextUnit.Unspecified),
                    color = colors.textPrimary,
                    maxLines = 1,
                    modifier = Modifier.clip(KhazenaRadius.pill).background(colors.blockSun).padding(horizontal = 12.dp, vertical = 6.dp),
                )
            }
            Accent(colors.blockSun, 22.dp, 33.53.dp, 38.96.dp, 30f)
            Accent(colors.blockEmber, 18.dp, 295.85.dp, 35.82.dp, -25f)
            Accent(colors.surfaceBackground, 24.dp, 287.9.dp, 123.86.dp, 20f)
        }
    }
}

@Composable
private fun MapTile(modifier: Modifier) {
    val colors = KhazenaTheme.colors
    Box(modifier.clip(RoundedCornerShape(20.dp)).background(colors.surfaceBackground)) {
        Shape(colors.borderDefault, -10.dp, 30.dp, 200.dp, 12.dp, 6.dp)
        Shape(colors.borderDefault, -10.dp, 74.dp, 200.dp, 12.dp, 6.dp)
        Shape(colors.borderDefault, 48.dp, -10.dp, 12.dp, 140.dp, 6.dp)
        Shape(colors.borderDefault, 116.dp, -10.dp, 12.dp, 140.dp, 6.dp)
        Shape(colors.blockSun, 14.dp, 48.dp, 26.dp, 20.dp, 6.dp)
        Shape(colors.blockViolet, 66.dp, 6.dp, 42.dp, 18.dp, 6.dp)
        Shape(colors.blockEmber, 134.dp, 48.dp, 24.dp, 20.dp, 6.dp)
        Shape(colors.blockSun, 66.dp, 90.dp, 42.dp, 14.dp, 5.dp)
    }
}

@Composable
private fun OffPin(modifier: Modifier) {
    val colors = KhazenaTheme.colors
    Box(modifier.clip(CircleShape).background(colors.blockInk).border(4.dp, colors.surfaceBackground, CircleShape)) {
        Image(painterResource(Res.drawable.home_pin_off), null, Modifier.align(Alignment.Center).size(30.dp))
        // The slash crosses the pin head, slightly above the circle's centre (Figma: centre 30.74, 24.8).
        Box(Modifier.centredAt(30.74.dp, 24.8.dp, 48.dp, 5.dp).rotate(-45f).background(colors.blockEmber, RoundedCornerShape(2.5.dp)))
    }
}

@Composable
private fun BoxScope.Shape(color: Color, x: Dp, y: Dp, width: Dp, height: Dp, radius: Dp) {
    Box(
        Modifier
            .wrapContentSize(Alignment.TopStart, unbounded = true)
            .offset(x, y)
            .requiredSize(width, height)
            .background(color, RoundedCornerShape(radius)),
    )
}

@Composable
private fun Accent(color: Color, width: Dp, centreX: Dp, centreY: Dp, rotation: Float) {
    Box(Modifier.centredAt(centreX, centreY, width, 8.dp).rotate(rotation).background(color, RoundedCornerShape(4.dp)))
}

// Places a child by its centre (Figma coordinates); without a size it measures itself and still centres there.
private fun Modifier.centredAt(x: Dp, y: Dp, width: Dp, height: Dp): Modifier =
    offset(x - width / 2, y - height / 2).requiredSize(width, height)

private fun Modifier.centredAt(x: Dp, y: Dp): Modifier = this.then(
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
        layout(0, 0) { placeable.placeRelative(x.roundToPx() - placeable.width / 2, y.roundToPx() - placeable.height / 2) }
    },
)
