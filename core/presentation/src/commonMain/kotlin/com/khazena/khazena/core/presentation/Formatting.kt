package com.khazena.khazena.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.presentation.language.LocalAppLocale
import khazena.core.presentation.generated.resources.Res
import khazena.core.presentation.generated.resources.distance_km
import khazena.core.presentation.generated.resources.discount_label
import khazena.core.presentation.generated.resources.price_iqd
import org.jetbrains.compose.resources.stringResource

@Composable
fun LocalizedText.localized(): String = when (LocalAppLocale.current) {
    "ckb" -> ckb
    "ar" -> ar
    else -> en
}

fun formatGrouped(value: Long): String =
    value.toString().reversed().chunked(3).joinToString(",").reversed()

@Composable
fun formatIqd(amount: Long): String = stringResource(Res.string.price_iqd, formatGrouped(amount))

@Composable
fun formatDistance(km: Double): String {
    val tenths = (km * 10).toInt()
    return stringResource(Res.string.distance_km, "${tenths / 10}.${tenths % 10}")
}

@Composable
fun formatDiscount(percent: Int): String = stringResource(Res.string.discount_label, percent)

@Composable
fun placeholderPhoto(index: Int): Painter {
    val colors = KhazenaTheme.colors
    val palette = listOf(colors.blockSun, colors.blockViolet, colors.blockEmber, colors.borderStrong)
    return ColorPainter(palette[index % palette.size])
}
