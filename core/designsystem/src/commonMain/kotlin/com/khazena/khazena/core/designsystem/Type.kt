package com.khazena.khazena.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import khazena.core.designsystem.generated.resources.Res
import khazena.core.designsystem.generated.resources.bricolage_grotesque
import khazena.core.designsystem.generated.resources.dm_mono_medium
import khazena.core.designsystem.generated.resources.instrument_sans_bold
import khazena.core.designsystem.generated.resources.instrument_sans_medium
import khazena.core.designsystem.generated.resources.instrument_sans_regular
import khazena.core.designsystem.generated.resources.instrument_sans_semibold
import khazena.core.designsystem.generated.resources.readex_pro_bold
import khazena.core.designsystem.generated.resources.readex_pro_medium
import khazena.core.designsystem.generated.resources.readex_pro_regular
import khazena.core.designsystem.generated.resources.readex_pro_semibold
import khazena.core.designsystem.generated.resources.vazirmatn_bold
import khazena.core.designsystem.generated.resources.vazirmatn_extrabold
import khazena.core.designsystem.generated.resources.vazirmatn_medium
import khazena.core.designsystem.generated.resources.vazirmatn_regular
import khazena.core.designsystem.generated.resources.vazirmatn_semibold
import org.jetbrains.compose.resources.Font

@Immutable
data class KhazenaTypography(
    val displayXL: TextStyle,
    val displayL: TextStyle,
    val headingH1: TextStyle,
    val headingH2: TextStyle,
    val headingH3: TextStyle,
    val headingH2Tight: TextStyle,
    val headingH4: TextStyle,
    val wordmarkS: TextStyle,
    val bodyL: TextStyle,
    val bodyM: TextStyle,
    val bodyS: TextStyle,
    val labelXLBold: TextStyle,
    val labelLBold: TextStyle,
    val labelPrice: TextStyle,
    val labelMSemiBold: TextStyle,
    val labelSMedium: TextStyle,
    val labelXSMedium: TextStyle,
    val labelAvatarM: TextStyle,
    val labelAvatarL: TextStyle,
    val buttonL: TextStyle,
    val buttonM: TextStyle,
    val monoKicker: TextStyle,
    val monoTag: TextStyle,
    val monoTagS: TextStyle,
    val monoCode: TextStyle,
)

private val figmaLineHeight = LineHeightStyle(LineHeightStyle.Alignment.Center, LineHeightStyle.Trim.None)

private const val ARABIC_SCRIPT_MIN_LINE_HEIGHT = 1.5

@Composable
fun languageFontFamily(languageCode: String): FontFamily = when (languageCode) {
    "ckb" -> FontFamily(
        Font(Res.font.vazirmatn_regular, FontWeight.Normal),
        Font(Res.font.vazirmatn_medium, FontWeight.Medium),
        Font(Res.font.vazirmatn_semibold, FontWeight.SemiBold),
        Font(Res.font.vazirmatn_bold, FontWeight.Bold),
        Font(Res.font.vazirmatn_extrabold, FontWeight.ExtraBold),
    )
    "ar" -> FontFamily(
        Font(Res.font.readex_pro_regular, FontWeight.Normal),
        Font(Res.font.readex_pro_medium, FontWeight.Medium),
        Font(Res.font.readex_pro_semibold, FontWeight.SemiBold),
        Font(Res.font.readex_pro_bold, FontWeight.Bold),
    )
    else -> FontFamily(
        Font(Res.font.instrument_sans_regular, FontWeight.Normal),
        Font(Res.font.instrument_sans_medium, FontWeight.Medium),
        Font(Res.font.instrument_sans_semibold, FontWeight.SemiBold),
        Font(Res.font.instrument_sans_bold, FontWeight.Bold),
    )
}

// Figma sets Bricolage's optical size to the font size; match it per style.
@Composable
internal fun brandFontFamily(opticalSize: Float): FontFamily = FontFamily(
    Font(
        Res.font.bricolage_grotesque,
        FontWeight.ExtraBold,
        FontStyle.Normal,
        FontVariation.Settings(
            FontVariation.weight(FontWeight.ExtraBold.weight),
            FontVariation.width(100f),
            FontVariation.Setting("opsz", opticalSize.coerceIn(12f, 96f)),
        ),
    ),
)

@Composable
internal fun khazenaTypography(languageCode: String): KhazenaTypography {
    val arabicScript = languageCode == "ckb" || languageCode == "ar"
    val instrument = languageFontFamily(languageCode)
    @Composable
    fun bricolage(size: Int) = if (arabicScript) instrument else brandFontFamily(size.toFloat())
    val mono = if (arabicScript) instrument else FontFamily(Font(Res.font.dm_mono_medium, FontWeight.Medium))

    // Tracking breaks Arabic-script letter joins and tight line heights clip its marks.
    fun style(family: FontFamily, weight: FontWeight, size: Int, lineHeight: Double, tracking: Double) = TextStyle(
        fontFamily = family,
        fontWeight = weight,
        fontSize = size.sp,
        lineHeight = (if (arabicScript) maxOf(lineHeight, ARABIC_SCRIPT_MIN_LINE_HEIGHT) else lineHeight).em,
        letterSpacing = (if (arabicScript) 0.0 else tracking / 100).em,
        lineHeightStyle = figmaLineHeight,
    )

    return KhazenaTypography(
        displayXL = style(bricolage(48), FontWeight.ExtraBold, 48, 1.0, -5.0),
        displayL = style(bricolage(40), FontWeight.ExtraBold, 40, 0.98, -5.0),
        headingH1 = style(bricolage(32), FontWeight.ExtraBold, 32, 1.04, -4.0),
        headingH2 = style(bricolage(28), FontWeight.ExtraBold, 28, 1.04, -4.0),
        headingH3 = style(bricolage(22), FontWeight.ExtraBold, 22, 1.10, -2.0),
        headingH2Tight = style(bricolage(26), FontWeight.ExtraBold, 26, 1.0, -3.0),
        headingH4 = style(bricolage(19), FontWeight.ExtraBold, 19, 1.15, -2.0),
        wordmarkS = style(bricolage(18), FontWeight.ExtraBold, 18, 1.1, -4.0),
        bodyL = style(instrument, FontWeight.Normal, 17, 1.35, 0.0),
        bodyM = style(instrument, FontWeight.Normal, 15, 1.35, 0.0),
        bodyS = style(instrument, FontWeight.Normal, 13, 1.30, 0.0),
        labelXLBold = style(instrument, FontWeight.Bold, 18, 1.25, 0.0),
        labelLBold = style(instrument, FontWeight.Bold, 16, 1.25, 0.0),
        labelPrice = style(instrument, FontWeight.Bold, 15, 1.25, 0.0),
        labelMSemiBold = style(instrument, FontWeight.SemiBold, 14, 1.25, 0.0),
        labelSMedium = style(instrument, FontWeight.Medium, 13, 1.25, 0.0),
        labelXSMedium = style(instrument, FontWeight.Medium, 12, 1.25, 0.0),
        labelAvatarM = style(instrument, FontWeight.SemiBold, 15, 1.0, 0.0),
        labelAvatarL = style(instrument, FontWeight.SemiBold, 18, 1.0, 0.0),
        buttonL = style(instrument, FontWeight.Bold, 16, 1.0, 0.0),
        buttonM = style(instrument, FontWeight.Bold, 14, 1.0, 0.0),
        monoKicker = style(mono, FontWeight.Medium, 11, 1.2, 6.0),
        monoTag = style(mono, FontWeight.Medium, 12, 1.2, 2.0),
        monoTagS = style(mono, FontWeight.Medium, 11, 1.2, 4.0),
        monoCode = style(mono, FontWeight.Medium, 15, 1.2, 2.0),
    )
}