package com.khazena.khazena.core.designsystem.component

import khazena.core.designsystem.generated.resources.Res
import khazena.core.designsystem.generated.resources.ic_arrow_left
import khazena.core.designsystem.generated.resources.ic_arrow_right
import khazena.core.designsystem.generated.resources.ic_bell
import khazena.core.designsystem.generated.resources.ic_bookmark
import khazena.core.designsystem.generated.resources.ic_chart_bar
import khazena.core.designsystem.generated.resources.ic_check
import khazena.core.designsystem.generated.resources.ic_clock
import khazena.core.designsystem.generated.resources.ic_export
import khazena.core.designsystem.generated.resources.ic_heart_fill
import khazena.core.designsystem.generated.resources.ic_house
import khazena.core.designsystem.generated.resources.ic_house_fill
import khazena.core.designsystem.generated.resources.ic_list
import khazena.core.designsystem.generated.resources.ic_magnifying_glass
import khazena.core.designsystem.generated.resources.ic_magnifying_glass_fill
import khazena.core.designsystem.generated.resources.ic_map_pin
import khazena.core.designsystem.generated.resources.ic_qr_code
import khazena.core.designsystem.generated.resources.ic_scan
import khazena.core.designsystem.generated.resources.ic_shopping_bag
import khazena.core.designsystem.generated.resources.ic_star_fill
import khazena.core.designsystem.generated.resources.ic_storefront
import khazena.core.designsystem.generated.resources.ic_user
import khazena.core.designsystem.generated.resources.ic_user_fill
import khazena.core.designsystem.generated.resources.ic_wallet
import khazena.core.designsystem.generated.resources.ic_wallet_fill
import khazena.core.designsystem.generated.resources.ic_x
import khazena.core.designsystem.generated.resources.kmark_header
import khazena.core.designsystem.generated.resources.kmark_on_ink
import khazena.core.designsystem.generated.resources.kmark_on_violet
import khazena.core.designsystem.generated.resources.kmark_watermark
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaTheme
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
}

object KhazenaLogo {
    val MarkOnPaper = Res.drawable.kmark_header
    val MarkOnViolet = Res.drawable.kmark_on_violet
    val MarkOnInk = Res.drawable.kmark_on_ink
    internal val Watermark = Res.drawable.kmark_watermark
}

@Preview(name = "Icons", widthDp = 390)
@Composable
private fun IconsPreview() = PreviewSurface {
    val icons = with(KhazenaIcons) {
        listOf(
            House, HouseFill, MagnifyingGlass, MagnifyingGlassFill, Wallet, WalletFill, User, UserFill, QrCode, Bell, ArrowLeft,
            ArrowRight, Export, Bookmark, StarFill, MapPin, Clock, Check, X, HeartFill, Storefront, ShoppingBag, Scan, ListBullets, ChartBar,
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