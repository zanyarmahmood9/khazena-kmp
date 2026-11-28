package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaTheme
import khazena.core.designsystem.generated.resources.Res
import khazena.core.designsystem.generated.resources.ic_arrow_left
import khazena.core.designsystem.generated.resources.ic_arrow_left_regular
import khazena.core.designsystem.generated.resources.ic_arrow_right
import khazena.core.designsystem.generated.resources.ic_arrow_right_line
import khazena.core.designsystem.generated.resources.ic_bell
import khazena.core.designsystem.generated.resources.ic_bookmark
import khazena.core.designsystem.generated.resources.ic_caret_circle_right
import khazena.core.designsystem.generated.resources.ic_caret_down
import khazena.core.designsystem.generated.resources.ic_caret_down_small
import khazena.core.designsystem.generated.resources.ic_caret_right_line
import khazena.core.designsystem.generated.resources.ic_chart_bar
import khazena.core.designsystem.generated.resources.ic_check
import khazena.core.designsystem.generated.resources.ic_check_line
import khazena.core.designsystem.generated.resources.ic_clock
import khazena.core.designsystem.generated.resources.ic_copy
import khazena.core.designsystem.generated.resources.ic_crosshair
import khazena.core.designsystem.generated.resources.ic_export
import khazena.core.designsystem.generated.resources.ic_facebook
import khazena.core.designsystem.generated.resources.ic_fire
import khazena.core.designsystem.generated.resources.ic_heart_fill
import khazena.core.designsystem.generated.resources.ic_house
import khazena.core.designsystem.generated.resources.ic_house_fill
import khazena.core.designsystem.generated.resources.ic_instagram
import khazena.core.designsystem.generated.resources.ic_list
import khazena.core.designsystem.generated.resources.ic_lock
import khazena.core.designsystem.generated.resources.ic_magnifying_glass
import khazena.core.designsystem.generated.resources.ic_magnifying_glass_fill
import khazena.core.designsystem.generated.resources.ic_magnifying_glass_regular
import khazena.core.designsystem.generated.resources.ic_map_pin
import khazena.core.designsystem.generated.resources.ic_map_pin_line
import khazena.core.designsystem.generated.resources.ic_navigation
import khazena.core.designsystem.generated.resources.ic_phone
import khazena.core.designsystem.generated.resources.ic_qr_code
import khazena.core.designsystem.generated.resources.ic_qr_code_line
import khazena.core.designsystem.generated.resources.ic_scan
import khazena.core.designsystem.generated.resources.ic_shield_check
import khazena.core.designsystem.generated.resources.ic_shopping_bag
import khazena.core.designsystem.generated.resources.ic_star_fill
import khazena.core.designsystem.generated.resources.ic_star_small
import khazena.core.designsystem.generated.resources.ic_storefront
import khazena.core.designsystem.generated.resources.ic_tag
import khazena.core.designsystem.generated.resources.ic_tiktok
import khazena.core.designsystem.generated.resources.ic_user
import khazena.core.designsystem.generated.resources.ic_user_fill
import khazena.core.designsystem.generated.resources.ic_wallet
import khazena.core.designsystem.generated.resources.ic_wallet_fill
import khazena.core.designsystem.generated.resources.ic_x
import khazena.core.designsystem.generated.resources.kmark_header
import khazena.core.designsystem.generated.resources.kmark_on_ember
import khazena.core.designsystem.generated.resources.kmark_on_ink
import khazena.core.designsystem.generated.resources.kmark_on_violet
import khazena.core.designsystem.generated.resources.kmark_watermark
import org.jetbrains.compose.resources.painterResource

object KhazenaIcons {
    val House = Res.drawable.ic_house
    val HouseFill = Res.drawable.ic_house_fill
    val MagnifyingGlass = Res.drawable.ic_magnifying_glass
    val MagnifyingGlassFill = Res.drawable.ic_magnifying_glass_fill
    val Wallet = Res.drawable.ic_wallet
    val WalletFill = Res.drawable.ic_wallet_fill
    val User = Res.drawable.ic_user
    val UserFill = Res.drawable.ic_user_fill
    val QrCode = Res.drawable.ic_qr_code
    val Bell = Res.drawable.ic_bell
    val ArrowLeft = Res.drawable.ic_arrow_left
    val ArrowLeftRegular = Res.drawable.ic_arrow_left_regular
    val CaretCircleRight = Res.drawable.ic_caret_circle_right
    val CaretDown = Res.drawable.ic_caret_down
    val MagnifyingGlassRegular = Res.drawable.ic_magnifying_glass_regular
    val StarSmall = Res.drawable.ic_star_small
    // Outline set used on the store screens (02c–02e).
    val QrCodeLine = Res.drawable.ic_qr_code_line
    val Fire = Res.drawable.ic_fire
    val ShieldCheck = Res.drawable.ic_shield_check
    val Lock = Res.drawable.ic_lock
    val CheckLine = Res.drawable.ic_check_line
    val Tag = Res.drawable.ic_tag
    val MapPinLine = Res.drawable.ic_map_pin_line
    val Navigation = Res.drawable.ic_navigation
    val Copy = Res.drawable.ic_copy
    val Phone = Res.drawable.ic_phone
    val CaretRightLine = Res.drawable.ic_caret_right_line
    val Instagram = Res.drawable.ic_instagram
    val Facebook = Res.drawable.ic_facebook
    val TikTok = Res.drawable.ic_tiktok
    val ArrowRightLine = Res.drawable.ic_arrow_right_line
    val ArrowRight = Res.drawable.ic_arrow_right
    val Export = Res.drawable.ic_export
    val Bookmark = Res.drawable.ic_bookmark
    val StarFill = Res.drawable.ic_star_fill
    val MapPin = Res.drawable.ic_map_pin
    val Clock = Res.drawable.ic_clock
    val Check = Res.drawable.ic_check
    val X = Res.drawable.ic_x
    val HeartFill = Res.drawable.ic_heart_fill
    val Storefront = Res.drawable.ic_storefront
    val ShoppingBag = Res.drawable.ic_shopping_bag
    val Scan = Res.drawable.ic_scan
    val ListBullets = Res.drawable.ic_list
    val ChartBar = Res.drawable.ic_chart_bar
    val Crosshair = Res.drawable.ic_crosshair
    val CaretDownSmall = Res.drawable.ic_caret_down_small
}

object KhazenaLogo {
    val MarkOnPaper = Res.drawable.kmark_header
    val MarkOnViolet = Res.drawable.kmark_on_violet
    val MarkOnInk = Res.drawable.kmark_on_ink
    val MarkOnEmber = Res.drawable.kmark_on_ember
    val Watermark = Res.drawable.kmark_watermark
}

// The mark as the native launch screens and the Lottie's first frame draw it, so launch → app has no visible jump.
@Composable
fun KhazenaLaunchMark(modifier: Modifier = Modifier) = Image(
    painterResource(KhazenaLogo.MarkOnEmber),
    contentDescription = null,
    modifier = modifier.size(102.dp, 115.6.dp),
    colorFilter = ColorFilter.tint(Color(0xFFDA3F1F)),
)

@Preview(name = "Icons", widthDp = 390)
@Composable
private fun IconsPreview() = PreviewSurface {
    val icons = with(KhazenaIcons) {
        listOf(
            House, HouseFill, MagnifyingGlass, MagnifyingGlassFill, Wallet, WalletFill, User, UserFill, QrCode, Bell, ArrowLeft,
            ArrowRight, Export, Bookmark, StarFill, MapPin, Clock, Check, X, HeartFill, Storefront, ShoppingBag, Scan, ListBullets, ChartBar, Crosshair,
        )
    }
    icons.chunked(8).forEach { row ->
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            row.forEach { Icon(painterResource(it), null, Modifier.size(24.dp), tint = KhazenaTheme.colors.textPrimary) }
        }
    }
}

@Preview(name = "Logo", widthDp = 390)
@Composable
private fun LogoPreview() = PreviewSurface {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
        listOf(KhazenaLogo.MarkOnPaper, KhazenaLogo.MarkOnViolet, KhazenaLogo.MarkOnInk).forEach {
            Image(painterResource(it), null, Modifier.size(64.dp))
        }
    }
    Text("K mark on Paper · Violet · Ink", style = KhazenaTheme.typography.bodyS, color = KhazenaTheme.colors.textSecondary)
}