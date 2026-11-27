package com.khazena.khazena.core.designsystem.component

import khazena.core.designsystem.generated.resources.Res
import khazena.core.designsystem.generated.resources.ic_arrow_left
import khazena.core.designsystem.generated.resources.ic_arrow_right
import khazena.core.designsystem.generated.resources.ic_bell
import khazena.core.designsystem.generated.resources.ic_bookmark
import khazena.core.designsystem.generated.resources.ic_check
import khazena.core.designsystem.generated.resources.ic_clock
import khazena.core.designsystem.generated.resources.ic_export
import khazena.core.designsystem.generated.resources.ic_heart_fill
import khazena.core.designsystem.generated.resources.ic_house
import khazena.core.designsystem.generated.resources.ic_house_fill
import khazena.core.designsystem.generated.resources.ic_magnifying_glass
import khazena.core.designsystem.generated.resources.ic_magnifying_glass_fill
import khazena.core.designsystem.generated.resources.ic_map_pin
import khazena.core.designsystem.generated.resources.ic_qr_code
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

/** Figma › 04 · Components › Icons (Phosphor Bold, 24pt). Tint them; they are single-colour. */
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
}

/** Khazena "K" mark, multi-colour: draw with Image, never tint. */
object KhazenaLogo {
    val MarkOnPaper = Res.drawable.kmark_header
    val MarkOnViolet = Res.drawable.kmark_on_violet
    val MarkOnInk = Res.drawable.kmark_on_ink
    internal val Watermark = Res.drawable.kmark_watermark
}
