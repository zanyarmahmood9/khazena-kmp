package com.khazena.khazena.feature.store.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.bleedHorizontally
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaMenuItem
import com.khazena.khazena.core.designsystem.component.KhazenaReviewCard
import com.khazena.khazena.core.designsystem.component.KhazenaSearchField
import com.khazena.khazena.core.designsystem.component.KhazenaStars
import com.khazena.khazena.core.designsystem.revealOnAppear
import com.khazena.khazena.core.presentation.formatDistance
import com.khazena.khazena.core.presentation.formatGrouped
import com.khazena.khazena.core.presentation.formatIqd
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.feature.store.domain.AvatarTone
import com.khazena.khazena.feature.store.domain.ContactKind
import com.khazena.khazena.feature.store.domain.MenuItem
import com.khazena.khazena.feature.store.domain.Store
import com.khazena.khazena.feature.store.domain.Weekday
import khazena.feature.store.presentation.generated.resources.Res
import khazena.feature.store.presentation.generated.resources.ill_blank_list
import khazena.feature.store.presentation.generated.resources.ill_map_roads
import khazena.feature.store.presentation.generated.resources.store_about
import khazena.feature.store.presentation.generated.resources.store_contact_call
import khazena.feature.store.presentation.generated.resources.store_contact_facebook
import khazena.feature.store.presentation.generated.resources.store_contact_instagram
import khazena.feature.store.presentation.generated.resources.store_contact_tiktok
import khazena.feature.store.presentation.generated.resources.store_copy
import khazena.feature.store.presentation.generated.resources.store_day_friday
import khazena.feature.store.presentation.generated.resources.store_day_monday
import khazena.feature.store.presentation.generated.resources.store_day_saturday
import khazena.feature.store.presentation.generated.resources.store_day_sunday
import khazena.feature.store.presentation.generated.resources.store_day_thursday
import khazena.feature.store.presentation.generated.resources.store_day_tuesday
import khazena.feature.store.presentation.generated.resources.store_day_wednesday
import khazena.feature.store.presentation.generated.resources.store_directions
import khazena.feature.store.presentation.generated.resources.store_distance_away
import khazena.feature.store.presentation.generated.resources.store_empty_action
import khazena.feature.store.presentation.generated.resources.store_empty_body
import khazena.feature.store.presentation.generated.resources.store_empty_tag
import khazena.feature.store.presentation.generated.resources.store_empty_title
import khazena.feature.store.presentation.generated.resources.store_filter_all
import khazena.feature.store.presentation.generated.resources.store_filter_popular
import khazena.feature.store.presentation.generated.resources.store_hours
import khazena.feature.store.presentation.generated.resources.store_member_offer
import khazena.feature.store.presentation.generated.resources.store_menu_clear
import khazena.feature.store.presentation.generated.resources.store_menu_no_match
import khazena.feature.store.presentation.generated.resources.store_menu_search
import khazena.feature.store.presentation.generated.resources.store_no_reviews
import khazena.feature.store.presentation.generated.resources.store_prices_note
import khazena.feature.store.presentation.generated.resources.store_rating_of_five
import khazena.feature.store.presentation.generated.resources.store_review_saved
import khazena.feature.store.presentation.generated.resources.store_reviews_count
import khazena.feature.store.presentation.generated.resources.store_saving
import khazena.feature.store.presentation.generated.resources.store_show_all_reviews
import khazena.feature.store.presentation.generated.resources.store_sort_highest
import khazena.feature.store.presentation.generated.resources.store_sort_lowest
import khazena.feature.store.presentation.generated.resources.store_sort_photos
import khazena.feature.store.presentation.generated.resources.store_sort_recent
import khazena.feature.store.presentation.generated.resources.store_today
import khazena.feature.store.presentation.generated.resources.store_verified_note
import khazena.feature.store.presentation.generated.resources.store_verified_visit
import khazena.feature.store.presentation.generated.resources.store_write_review
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock

// ---------- Services (Figma 02c, 02c·B) ----------

@Composable
internal fun ServicesTab(store: Store, state: StoreState, onAction: (StoreAction) -> Unit) {
    if (store.menu.isEmpty()) {
        EmptyServicesCard(onSeeContact = { onAction(StoreAction.OnSeeContactClick) })
        return
    }
    val colors = KhazenaTheme.colors
    KhazenaSearchField(
        value = state.menuQuery,
        onValueChange = { onAction(StoreAction.OnMenuQueryChange(it)) },
        placeholder = stringResource(Res.string.store_menu_search, store.name.localized()),
        clearContentDescription = stringResource(Res.string.store_menu_clear),
    )
    MenuFilters(store, state.menuFilter, onAction, Modifier.padding(top = 12.dp))
    Text(
        stringResource(Res.string.store_prices_note, store.discountPercent),
        style = KhazenaTheme.typography.bodyS.copy(fontSize = 12.sp),
        color = colors.textSecondary,
        modifier = Modifier.padding(top = 17.dp),
    )
    val query = state.menuQuery.trim()
    val items = store.itemsFor(state.menuFilter).matching(query)
    Column(Modifier.padding(top = 14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (items.isEmpty()) {
            Text(
                stringResource(Res.string.store_menu_no_match, query),
                style = KhazenaTheme.typography.bodyM,
                color = colors.textSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            )
        }
        items.forEachIndexed { index, item ->
            KhazenaMenuItem(
                photo = painterResource(storeImage(item.imageKey)),
                title = item.name.localized(),
                description = item.description.localized(),
                price = formatIqd(item.memberPriceIqd),
                originalPrice = formatGrouped(item.priceIqd),
                savingLabel = stringResource(Res.string.store_saving, formatGrouped(item.priceIqd - item.memberPriceIqd)),
                onClick = { onAction(StoreAction.OnMenuItemClick(item.id)) },
                modifier = Modifier.revealOnAppear(index),
            )
        }
    }
}

@Composable
private fun List<MenuItem>.matching(query: String): List<MenuItem> =
    if (query.isEmpty()) {
        this
    } else {
        filter { item -> item.name.localized().contains(query, ignoreCase = true) || item.description.localized().contains(query, ignoreCase = true) }
    }

@Composable
private fun MenuFilters(store: Store, selected: MenuFilter, onAction: (StoreAction) -> Unit, modifier: Modifier = Modifier) {
    val popularCount = store.menu.count { it.popular }
    LazyRow(
        modifier.bleedHorizontally(KhazenaSpacing.screenMargin).selectableGroup(),
        contentPadding = PaddingValues(horizontal = KhazenaSpacing.screenMargin),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(key = "all") {
            FilterChip(stringResource(Res.string.store_filter_all), selected == MenuFilter.All, count = store.menu.size) {
                onAction(StoreAction.OnMenuFilterClick(MenuFilter.All))
            }
        }
        if (popularCount > 0) {
            item(key = "popular") {
                FilterChip(stringResource(Res.string.store_filter_popular), selected == MenuFilter.Popular, fire = true) {
                    onAction(StoreAction.OnMenuFilterClick(MenuFilter.Popular))
                }
            }
        }
        items(store.menuSections.filter { section -> store.menu.any { it.sectionId == section.id } }, key = { it.id }) { section ->
            val filter = MenuFilter.Section(section.id)
            FilterChip(section.name.localized(), selected == filter, count = store.menu.count { it.sectionId == section.id }) {
                onAction(StoreAction.OnMenuFilterClick(filter))
            }
        }
    }
}

// Figma "Menu filters": 13pt semibold with an optional count bubble; the selected chip is ink, the rest outlined.
@Composable
private fun FilterChip(label: String, selected: Boolean, count: Int? = null, fire: Boolean = false, onClick: () -> Unit) {
    val colors = KhazenaTheme.colors
    val haptics = LocalHapticFeedback.current
    Row(
        Modifier
            .heightIn(min = 34.dp)
            .clip(KhazenaRadius.pill)
            .background(if (selected) colors.actionSelected else colors.surfaceCard)
            .then(if (selected) Modifier else Modifier.border(1.dp, colors.borderDefault, KhazenaRadius.pill))
            .selectable(selected = selected, role = Role.Tab) {
                if (!selected) haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                onClick()
            }
            .padding(start = 14.dp, end = if (count != null) 10.dp else 14.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (fire) Image(painterResource(KhazenaIcons.Fire), null, Modifier.size(14.dp))
        Text(label, style = KhazenaTheme.typography.labelSMedium.copy(fontWeight = FontWeight.SemiBold), color = if (selected) Color.White else colors.textPrimary, maxLines = 1)
        if (count != null) {
            Text(
                count.toString(),
                style = KhazenaTheme.typography.labelXSMedium.copy(fontSize = 11.sp, fontWeight = FontWeight.SemiBold),
                color = if (selected) Color.White else colors.textSecondary,
                modifier = Modifier
                    .clip(KhazenaRadius.pill)
                    .background(if (selected) Color.White.copy(alpha = 0.2f) else colors.surfaceTile)
                    .padding(horizontal = 6.dp, vertical = 1.dp),
            )
        }
    }
}

// Figma 02c·B "Empty · No service list": a yellow scene of a blank list, then why and where to look instead.
@Composable
private fun EmptyServicesCard(onSeeContact: () -> Unit) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Column(
        Modifier.fillMaxWidth().clip(KhazenaRadius.card).background(colors.surfaceCard).padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 18.dp),
    ) {
        Box(Modifier.fillMaxWidth().height(160.dp).clip(RoundedCornerShape(16.dp)).background(colors.blockSun), contentAlignment = Alignment.TopCenter) {
            // The scene is laid out on Figma's 330dp frame and kept centred on wider or narrower cards.
            Box(Modifier.requiredWidth(330.dp).height(160.dp)) {
                // Positions are each bar's own top-left before rotation (Figma reports the rotated bounds).
                Confetti(colors.blockEmber, 22, 27, 35, 30f)
                Confetti(colors.blockViolet, 18, 285, 30, -25f)
                Confetti(colors.surfaceBackground, 22, 290, 131, 20f)
                Confetti(colors.surfaceBackground, 14, 59, 67, -20f)
                Image(
                    painterResource(Res.drawable.ill_blank_list),
                    null,
                    Modifier.offset(x = 84.dp, y = 26.dp).requiredSize(150.dp, 128.dp).rotate(5f),
                )
                Box(
                    Modifier
                        .offset(x = 206.dp, y = 84.dp)
                        .size(56.dp)
                        .background(colors.blockViolet, CircleShape)
                        .border(4.dp, colors.blockSun, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("?", style = type.headingH2.copy(fontSize = 30.sp), color = colors.surfaceBackground)
                }
                Text(
                    stringResource(Res.string.store_empty_tag).uppercase(),
                    style = type.monoKicker,
                    color = colors.surfaceBackground,
                    maxLines = 1,
                    modifier = Modifier
                        .offset(x = 18.dp, y = 106.dp)
                        .rotate(-6f)
                        .clip(KhazenaRadius.pill)
                        .background(colors.blockInk)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                )
            }
        }
        Column(Modifier.padding(start = 8.dp, end = 8.dp, top = 18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(stringResource(Res.string.store_empty_title), style = type.headingH3.copy(letterSpacing = (-0.03).em), color = colors.textPrimary)
            Text(stringResource(Res.string.store_empty_body), style = type.bodyM.copy(fontSize = 14.sp, lineHeight = 1.4.em), color = colors.textSecondary)
            Spacer(Modifier.height(10.dp))
            PillButton(
                label = stringResource(Res.string.store_empty_action),
                icon = KhazenaIcons.ArrowRightLine,
                container = colors.blockInk,
                content = colors.textOnDark,
                onClick = onSeeContact,
                modifier = Modifier.fillMaxWidth(),
                height = 52.dp,
                textStyle = type.labelPrice,
                iconSize = 18.dp,
                trailingIcon = true,
            )
        }
    }
}

@Composable
private fun Confetti(color: Color, width: Int, x: Int, y: Int, angle: Float) {
    Box(Modifier.offset(x = x.dp, y = y.dp).size(width = width.dp, height = 8.dp).rotate(angle).background(color, RoundedCornerShape(4.dp)))
}

// ---------- Reviews (Figma 02d) ----------

@Composable
internal fun ReviewsTab(store: Store, sort: ReviewSort, onAction: (StoreAction) -> Unit) {
    val colors = KhazenaTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        RatingSummary(store)
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(KhazenaIcons.ShieldCheck), null, Modifier.size(14.dp))
            Text(stringResource(Res.string.store_verified_note), style = KhazenaTheme.typography.bodyS.copy(fontSize = 12.sp), color = colors.textSecondary)
        }
        // Locked until the member has a verified visit here (Figma 07a explains why).
        PillButton(
            label = stringResource(Res.string.store_write_review),
            icon = KhazenaIcons.Lock,
            container = colors.surfaceTile,
            content = colors.textSecondary,
            onClick = { onAction(StoreAction.OnWriteReviewClick) },
            modifier = Modifier.fillMaxWidth(),
            height = 50.dp,
            textStyle = KhazenaTheme.typography.labelMSemiBold,
        )
        LazyRow(
            Modifier.bleedHorizontally(KhazenaSpacing.screenMargin).selectableGroup(),
            contentPadding = PaddingValues(horizontal = KhazenaSpacing.screenMargin),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(ReviewSort.entries) { option ->
                SortChip(stringResource(option.label), option == sort) { onAction(StoreAction.OnReviewSortClick(option)) }
            }
        }
        val reviews = store.reviewsSortedBy(sort)
        if (reviews.isEmpty()) {
            Text(
                stringResource(Res.string.store_no_reviews),
                style = KhazenaTheme.typography.bodyM,
                color = colors.textSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            )
        }
        reviews.forEachIndexed { index, review ->
            KhazenaReviewCard(
                initials = review.initials,
                avatarColor = review.tone.color(),
                name = review.author,
                verifiedLine = stringResource(Res.string.store_verified_visit, review.age.localized()),
                rating = review.rating,
                ratingDescription = stringResource(Res.string.store_rating_of_five, review.rating.toString()),
                text = review.text.localized(),
                savedLabel = stringResource(Res.string.store_review_saved, formatIqd(review.savedIqd)),
                modifier = Modifier.revealOnAppear(index),
            )
        }
        if (store.reviewCount > reviews.size) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(KhazenaRadius.pill)
                    .background(colors.surfaceCard)
                    .border(1.dp, colors.borderDefault, KhazenaRadius.pill)
                    .clickable(role = Role.Button) { onAction(StoreAction.OnShowAllReviewsClick) }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    stringResource(Res.string.store_show_all_reviews, store.reviewCount),
                    style = KhazenaTheme.typography.labelSMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = colors.textPrimary,
                )
            }
        }
    }
}

@Composable
private fun AvatarTone.color(): Color = when (this) {
    AvatarTone.Violet -> KhazenaTheme.colors.blockViolet
    AvatarTone.Sun -> KhazenaTheme.colors.blockSun
    AvatarTone.Green -> KhazenaTheme.colors.statusLive
}

private val ReviewSort.label: StringResource
    get() = when (this) {
        ReviewSort.Recent -> Res.string.store_sort_recent
        ReviewSort.Highest -> Res.string.store_sort_highest
        ReviewSort.Lowest -> Res.string.store_sort_lowest
        ReviewSort.WithPhotos -> Res.string.store_sort_photos
    }

@Composable
private fun SortChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val colors = KhazenaTheme.colors
    val haptics = LocalHapticFeedback.current
    Text(
        label,
        style = KhazenaTheme.typography.labelXSMedium,
        color = if (selected) Color.White else colors.textPrimary,
        maxLines = 1,
        modifier = Modifier
            .clip(KhazenaRadius.pill)
            .background(if (selected) colors.actionSelected else colors.surfaceCard)
            .then(if (selected) Modifier else Modifier.border(1.dp, colors.borderDefault, KhazenaRadius.pill))
            .selectable(selected = selected, role = Role.Tab) {
                if (!selected) haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                onClick()
            }
            .padding(horizontal = 14.dp, vertical = 8.dp),
    )
}

@Composable
private fun RatingSummary(store: Store) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    val total = store.ratingBreakdown.sum().coerceAtLeast(1)
    Row(
        Modifier.fillMaxWidth().clip(KhazenaRadius.card).background(colors.surfaceCard).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(store.rating.toString(), style = type.displayL.copy(lineHeight = 44.sp, letterSpacing = (-0.04).em), color = colors.textPrimary)
            KhazenaStars(store.rating.roundToStars(), stringResource(Res.string.store_rating_of_five, store.rating.toString()), size = 13.dp)
            Text(stringResource(Res.string.store_reviews_count, store.reviewCount), style = type.bodyS.copy(fontSize = 11.sp), color = colors.textSecondary)
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            store.ratingBreakdown.forEachIndexed { index, count ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("${5 - index}", style = type.labelXSMedium.copy(fontSize = 11.sp, fontWeight = FontWeight.SemiBold), color = colors.textPrimary)
                    Box(Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(colors.surfaceTile)) {
                        Box(Modifier.fillMaxHeight().fillMaxWidth(count / total.toFloat()).clip(RoundedCornerShape(3.dp)).background(colors.iconStar))
                    }
                    Text(
                        count.toString(),
                        style = type.bodyS.copy(fontSize = 11.sp),
                        color = colors.textSecondary,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(22.dp),
                    )
                }
            }
        }
    }
}

private fun Double.roundToStars(): Int = (this + 0.5).toInt().coerceIn(0, 5)

// ---------- Info (Figma 02e) ----------

@Composable
internal fun InfoTab(store: Store, onAction: (StoreAction) -> Unit) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Column(InfoCardModifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(stringResource(Res.string.store_about), style = type.labelPrice.copy(fontWeight = FontWeight.SemiBold), color = colors.textPrimary)
            Text(store.about.localized(), style = type.bodyS.copy(lineHeight = 19.sp), color = colors.textSecondary)
        }
        Column(
            Modifier.fillMaxWidth().clip(KhazenaRadius.tile).background(colors.blockSun).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(KhazenaIcons.Tag), null, Modifier.size(18.dp), tint = colors.textPrimary)
                Text(stringResource(Res.string.store_member_offer), style = type.labelPrice, color = colors.textPrimary)
            }
            store.offerRules.forEach { rule ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(KhazenaIcons.CheckLine), null, Modifier.size(14.dp), tint = colors.textPrimary)
                    Text(rule.localized(), style = type.bodyS, color = colors.textPrimary)
                }
            }
        }
        LocationCard(store, onAction)
        HoursCard(store)
        ContactCard(store, onAction)
    }
}

private val InfoCardModifier @Composable get() = Modifier.fillMaxWidth().clip(KhazenaRadius.card).background(KhazenaTheme.colors.surfaceCard)

// shortcut: a drawn street pattern with the store's pin stands in for a live map until a maps SDK is added.
private val MapPaper = Color(0xFFEADFCB)

@Composable
private fun LocationCard(store: Store, onAction: (StoreAction) -> Unit) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Column(InfoCardModifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(Modifier.fillMaxWidth().height(130.dp).clip(KhazenaRadius.card).background(MapPaper)) {
            Image(
                painterResource(Res.drawable.ill_map_roads),
                null,
                Modifier.wrapContentSize(Alignment.TopStart, unbounded = true).requiredSize(350.dp, 120.dp),
            )
            Box(Modifier.align(Alignment.TopCenter).padding(top = 34.dp)) {
                StoreLogo(store, Modifier.size(40.dp).border(3.dp, Color.White, RoundedCornerShape(14.dp)), RoundedCornerShape(14.dp))
            }
        }
        Row(Modifier.padding(horizontal = 4.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(KhazenaIcons.MapPinLine), null, Modifier.size(18.dp), tint = colors.textPrimary)
            Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(store.address.localized(), style = type.labelMSemiBold, color = colors.textPrimary)
                Text(
                    stringResource(Res.string.store_distance_away, store.area.localized(), formatDistance(store.distanceKm)),
                    style = type.bodyS.copy(fontSize = 12.sp),
                    color = colors.textSecondary,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PillButton(
                label = stringResource(Res.string.store_directions),
                icon = KhazenaIcons.Navigation,
                container = colors.actionPrimary,
                content = colors.actionOnPrimary,
                onClick = { onAction(StoreAction.OnDirectionsClick) },
                modifier = Modifier.weight(1f),
                height = 40.dp,
                textStyle = type.labelSMedium.copy(fontWeight = FontWeight.Bold),
            )
            PillButton(
                label = stringResource(Res.string.store_copy),
                icon = KhazenaIcons.Copy,
                container = colors.surfaceTile,
                content = colors.textPrimary,
                onClick = { onAction(StoreAction.OnCopyAddressClick) },
                height = 40.dp,
                textStyle = type.labelSMedium.copy(fontWeight = FontWeight.SemiBold),
            )
        }
    }
}

@Composable
private fun HoursCard(store: Store) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    val today = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).dayOfWeek.toWeekday() }
    Column(InfoCardModifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(Res.string.store_hours), style = type.labelPrice.copy(fontWeight = FontWeight.SemiBold), color = colors.textPrimary, modifier = Modifier.weight(1f))
            OpenTag(store.isOpenNow)
        }
        store.hours.forEach { hours ->
            val isToday = hours.day == today
            val day = stringResource(hours.day.label)
            val time = "${hours.opens} – ${hours.closes}"
            Row(
                Modifier
                    .fillMaxWidth()
                    .then(if (isToday) Modifier.clip(RoundedCornerShape(10.dp)).background(colors.surfaceBackground).padding(horizontal = 10.dp, vertical = 6.dp) else Modifier),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                if (isToday) {
                    val todayStyle = type.labelSMedium.copy(fontWeight = FontWeight.SemiBold)
                    Text(stringResource(Res.string.store_today, day), style = todayStyle, color = colors.textPrimary)
                    Text(time, style = todayStyle, color = colors.textPrimary)
                } else {
                    Text(day, style = type.bodyS, color = colors.textSecondary)
                    Text(time, style = type.labelSMedium, color = colors.textPrimary)
                }
            }
        }
    }
}

private fun DayOfWeek.toWeekday(): Weekday = when (this) {
    DayOfWeek.SATURDAY -> Weekday.Saturday
    DayOfWeek.SUNDAY -> Weekday.Sunday
    DayOfWeek.MONDAY -> Weekday.Monday
    DayOfWeek.TUESDAY -> Weekday.Tuesday
    DayOfWeek.WEDNESDAY -> Weekday.Wednesday
    DayOfWeek.THURSDAY -> Weekday.Thursday
    DayOfWeek.FRIDAY -> Weekday.Friday
}

private val Weekday.label: StringResource
    get() = when (this) {
        Weekday.Saturday -> Res.string.store_day_saturday
        Weekday.Sunday -> Res.string.store_day_sunday
        Weekday.Monday -> Res.string.store_day_monday
        Weekday.Tuesday -> Res.string.store_day_tuesday
        Weekday.Wednesday -> Res.string.store_day_wednesday
        Weekday.Thursday -> Res.string.store_day_thursday
        Weekday.Friday -> Res.string.store_day_friday
    }

@Composable
private fun ContactCard(store: Store, onAction: (StoreAction) -> Unit) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Column(InfoCardModifier.padding(8.dp)) {
        store.contacts.forEachIndexed { index, contact ->
            if (index > 0) HorizontalDivider(thickness = 1.dp, color = colors.surfaceTile)
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable(role = Role.Button) { onAction(StoreAction.OnContactClick(contact)) }
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(Modifier.size(38.dp).background(colors.surfaceTile, CircleShape), contentAlignment = Alignment.Center) {
                    Icon(painterResource(contact.kind.icon), null, Modifier.size(18.dp), tint = colors.textPrimary)
                }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                    Text(stringResource(contact.kind.label), style = type.bodyS.copy(fontSize = 12.sp), color = colors.textSecondary)
                    Text(contact.value, style = type.labelMSemiBold, color = colors.textPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Image(painterResource(KhazenaIcons.CaretRightLine), null, Modifier.size(18.dp))
            }
        }
    }
}

private val ContactKind.icon
    get() = when (this) {
        ContactKind.Phone -> KhazenaIcons.Phone
        ContactKind.Instagram -> KhazenaIcons.Instagram
        ContactKind.Facebook -> KhazenaIcons.Facebook
        ContactKind.TikTok -> KhazenaIcons.TikTok
    }

private val ContactKind.label: StringResource
    get() = when (this) {
        ContactKind.Phone -> Res.string.store_contact_call
        ContactKind.Instagram -> Res.string.store_contact_instagram
        ContactKind.Facebook -> Res.string.store_contact_facebook
        ContactKind.TikTok -> Res.string.store_contact_tiktok
    }
