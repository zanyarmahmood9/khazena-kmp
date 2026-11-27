package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun KhazenaComponentGallery(modifier: Modifier = Modifier) {
    val photo = ColorPainter(KhazenaTheme.colors.borderStrong)
    var search by remember { mutableStateOf("kebab") }
    var chip by remember { mutableIntStateOf(1) }
    var segment by remember { mutableIntStateOf(1) }
    var toggle by remember { mutableStateOf(true) }
    var check by remember { mutableStateOf(true) }
    var stars by remember { mutableIntStateOf(4) }
    var tab by remember { mutableIntStateOf(0) }

    Box(modifier.fillMaxSize().background(KhazenaTheme.colors.surfaceBackground)) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = KhazenaSpacing.screenMargin)
                .padding(top = 8.dp, bottom = 140.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            KhazenaAppHeader("BK", "Notifications", {}, {})
            KhazenaSectionHeader("Highest discounts.", linkLabel = "See all")
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KhazenaButton("Scan to save", {}, icon = KhazenaIcons.QrCode)
                KhazenaButton("Scan to save", {}, variant = ButtonVariant.Dark, icon = KhazenaIcons.QrCode)
                KhazenaButton("Scan to save", {}, enabled = false, icon = KhazenaIcons.QrCode)
            }
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                KhazenaButton("Scan to save", {}, size = ButtonSize.M, icon = KhazenaIcons.QrCode)
                KhazenaButton("Scan to save", {}, variant = ButtonVariant.Secondary, size = ButtonSize.M, icon = KhazenaIcons.QrCode)
                KhazenaButton("Scan to save", {}, variant = ButtonVariant.Ghost, size = ButtonSize.M, icon = KhazenaIcons.QrCode)
            }
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                KhazenaButton("Export", {}, variant = ButtonVariant.Secondary, size = ButtonSize.Compact, icon = KhazenaIcons.Export)
                KhazenaButton("Add service", {}, size = ButtonSize.Compact)
                KhazenaButton("See all 126", {}, variant = ButtonVariant.Secondary, size = ButtonSize.S)
            }
            KhazenaButton("Close business account", {}, Modifier.fillMaxWidth(), variant = ButtonVariant.Danger)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButtonStyle.entries.forEach { KhazenaIconButton(KhazenaIcons.ArrowLeft, "Back", {}, style = it) }
            }
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KhazenaTag("Save 30%")
                KhazenaTag("Open now", type = TagType.Live)
                KhazenaTag("Verified visit", type = TagType.Verified)
                KhazenaTag("Expired", type = TagType.Expired)
                KhazenaTag("Fastest", type = TagType.Highlight)
                KhazenaTag("Save 30%", type = TagType.OnColourBlock)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("All", "Grills", "Cafés").forEachIndexed { i, label ->
                    KhazenaChip(label, selected = chip == i, onClick = { chip = i }, count = if (i == 1) 9 else null)
                }
            }
            KhazenaSearchField(search, { search = it }, "Search restaurants, salons, services…", "Clear")
            KhazenaSegmentedControl(listOf("Menu", "Reviews · 126", "Info"), segment, { segment = it })
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                KhazenaToggle(toggle, { toggle = it })
                KhazenaRadio(toggle, { toggle = !toggle })
                KhazenaCheckbox(check, { check = it })
                KhazenaAvatar("BK")
                KhazenaAvatar("BK", style = AvatarStyle.OnColour, large = true)
                KhazenaAvatar("BK", style = AvatarStyle.Expired)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                (1..5).forEach { KhazenaStarTile(it <= stars, "$it stars", { stars = it }) }
            }
            KhazenaPlaceRow(photo, "Naz Café", "Café · 0.6 km", discountLabel = "Save 10%")
            KhazenaMenuItem(photo, "Chicken Kebab Platter", "Two skewers, rice, grilled veg and fresh bread", "8,400 IQD", originalPrice = "12,000", savingLabel = "−3,600")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                KhazenaCategoryTile(photo, "Food & Cafes")
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    KhazenaFactChip(FactKind.Rating, "4.8 (126)")
                    KhazenaFactChip(FactKind.Distance, "0.6 km")
                    KhazenaFactChip(FactKind.Hours, "Open · 11pm")
                }
            }
            KhazenaDealCard(photo, "Azmar Grill", "Grill · Salim St", "30% off", "★ 4.8", "Redeem in store →")
            KhazenaOfferBlock("Member offer · every item", "30% off\neverything.", "Member price is applied to every item")
            KhazenaMembershipCard(true, "Premium", "Active", "Member", "Bandy Kamal", "Plan", "1-Year Premium", "Valid until", "12 Oct 2027")
            KhazenaMembershipCard(false, "Premium", "Expired", "Member", "Bandy Kamal", "Plan", "1-Year Premium", "Expired on", "12 Oct 2026")
            KhazenaInfoBanner("Show this live code to the cashier. Screenshots won’t work.")
            KhazenaToast("Removed from saved", detail = "Shine Car Wash", actionLabel = "Undo")
        }
        KhazenaTabBar(
            items = listOf(
                TabBarItem("Home", KhazenaIcons.House, KhazenaIcons.HouseFill),
                TabBarItem("Search", KhazenaIcons.MagnifyingGlass, KhazenaIcons.MagnifyingGlassFill),
                TabBarItem("Wallet", KhazenaIcons.Wallet, KhazenaIcons.WalletFill),
                TabBarItem("Profile", KhazenaIcons.User, KhazenaIcons.UserFill),
            ),
            selectedIndex = tab,
            onSelect = { tab = it },
            actionIcon = KhazenaIcons.QrCode,
            actionLabel = "Scan",
            onAction = {},
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
internal fun PreviewSurface(content: @Composable ColumnScope.() -> Unit) = KhazenaTheme {
    Column(
        Modifier.background(KhazenaTheme.colors.surfaceBackground).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = content,
    )
}

@Preview(name = "Component gallery", widthDp = 390, heightDp = 2400)
@Composable
private fun ComponentGalleryPreview() = KhazenaTheme { KhazenaComponentGallery() }