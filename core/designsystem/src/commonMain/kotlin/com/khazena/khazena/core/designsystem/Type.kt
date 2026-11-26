package com.khazena.khazena.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import khazena.core.designsystem.generated.resources.Res
import khazena.core.designsystem.generated.resources.bricolage_grotesque_extrabold
import khazena.core.designsystem.generated.resources.dm_mono_medium
import khazena.core.designsystem.generated.resources.instrument_sans_bold
import khazena.core.designsystem.generated.resources.instrument_sans_medium
import khazena.core.designsystem.generated.resources.instrument_sans_regular
import khazena.core.designsystem.generated.resources.instrument_sans_semibold
import org.jetbrains.compose.resources.Font

/** Type styles from Figma › 02 · Typography. Names mirror the Figma styles (`Heading/H1` -> `headingH1`). */
@Immutable
data class KhazenaTypography(
    val displayXL: TextStyle,
    val displayL: TextStyle,
    val headingH1: TextStyle,
    val headingH2: TextStyle,
    val headingH3: TextStyle,
    val headingH4: TextStyle,
    val bodyL: TextStyle,
    val bodyM: TextStyle,
    val bodyS: TextStyle,
    val labelLBold: TextStyle,
    val labelMSemiBold: TextStyle,
    val labelSMedium: TextStyle,
    val labelXSMedium: TextStyle,
    val buttonL: TextStyle,
    val buttonM: TextStyle,
    val monoKicker: TextStyle,
    val monoTag: TextStyle,
    val monoCode: TextStyle,
)

// Figma centres text in its line box and adds no font padding; this matches it.
private val figmaLineHeight = LineHeightStyle(LineHeightStyle.Alignment.Center, LineHeightStyle.Trim.None)

// Figma gives line height and letter spacing in %, which map to em.
private fun style(family: FontFamily, weight: FontWeight, size: Int, lineHeight: Double, tracking: Double) =
    TextStyle(
        fontFamily = family,
        fontWeight = weight,
        fontSize = size.sp,
        lineHeight = lineHeight.em,
        letterSpacing = (tracking / 100).em,
        lineHeightStyle = figmaLineHeight,
    )

@Composable
internal fun khazenaTypography(): KhazenaTypography {
    val bricolage = FontFamily(Font(Res.font.bricolage_grotesque_extrabold, FontWeight.ExtraBold))
    val instrument = FontFamily(
        Font(Res.font.instrument_sans_regular, FontWeight.Normal),
        Font(Res.font.instrument_sans_medium, FontWeight.Medium),
        Font(Res.font.instrument_sans_semibold, FontWeight.SemiBold),
        Font(Res.font.instrument_sans_bold, FontWeight.Bold),
    )
    val mono = FontFamily(Font(Res.font.dm_mono_medium, FontWeight.Medium))

    return KhazenaTypography(
        displayXL = style(bricolage, FontWeight.ExtraBold, 48, 1.0, -5.0),
        displayL = style(bricolage, FontWeight.ExtraBold, 40, 0.98, -5.0),
        headingH1 = style(bricolage, FontWeight.ExtraBold, 32, 1.04, -4.0),
        headingH2 = style(bricolage, FontWeight.ExtraBold, 28, 1.04, -4.0),
        headingH3 = style(bricolage, FontWeight.ExtraBold, 22, 1.10, -2.0),
        headingH4 = style(bricolage, FontWeight.ExtraBold, 19, 1.15, -2.0),
        bodyL = style(instrument, FontWeight.Normal, 17, 1.35, 0.0),
        bodyM = style(instrument, FontWeight.Normal, 15, 1.35, 0.0),
        bodyS = style(instrument, FontWeight.Normal, 13, 1.30, 0.0),
        labelLBold = style(instrument, FontWeight.Bold, 16, 1.25, 0.0),
        labelMSemiBold = style(instrument, FontWeight.SemiBold, 14, 1.25, 0.0),
        labelSMedium = style(instrument, FontWeight.Medium, 13, 1.25, 0.0),
        labelXSMedium = style(instrument, FontWeight.Medium, 12, 1.25, 0.0),
        buttonL = style(instrument, FontWeight.Bold, 16, 1.0, 0.0),
        buttonM = style(instrument, FontWeight.Bold, 14, 1.0, 0.0),
        monoKicker = style(mono, FontWeight.Medium, 11, 1.2, 6.0),
        monoTag = style(mono, FontWeight.Medium, 12, 1.2, 2.0),
        monoCode = style(mono, FontWeight.Medium, 15, 1.2, 2.0),
    )
}
