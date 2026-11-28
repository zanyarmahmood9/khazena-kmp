package com.khazena.khazena.feature.home.presentation.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaRtlPreview
import com.khazena.khazena.core.designsystem.KhazenaSize
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.IconButtonStyle
import com.khazena.khazena.core.designsystem.component.KhazenaBottomSheet
import com.khazena.khazena.core.designsystem.component.KhazenaChip
import com.khazena.khazena.core.designsystem.component.KhazenaIconButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaPlaceRow
import com.khazena.khazena.core.designsystem.component.KhazenaRadio
import com.khazena.khazena.core.designsystem.component.KhazenaSheetTitle
import com.khazena.khazena.core.designsystem.component.KhazenaShimmer
import com.khazena.khazena.core.designsystem.component.KhazenaSkeleton
import com.khazena.khazena.core.designsystem.component.KhazenaSkeletonRow
import com.khazena.khazena.core.designsystem.component.KhazenaTopBar
import com.khazena.khazena.core.designsystem.component.multiplySafe
import com.khazena.khazena.core.designsystem.revealOnAppear
import com.khazena.khazena.core.designsystem.textClickable
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.core.presentation.formatDistance
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.core.presentation.state.LoadState
import com.khazena.khazena.core.presentation.state.LoadStateContent
import com.khazena.khazena.core.presentation.state.MessageToastHost
import com.khazena.khazena.core.presentation.state.MessageToastState
import com.khazena.khazena.core.presentation.state.rememberMessageToastState
import com.khazena.khazena.feature.home.domain.CategoryDetail
import com.khazena.khazena.feature.home.domain.CategoryPlace
import com.khazena.khazena.feature.home.domain.Subcategory
import com.khazena.khazena.feature.home.presentation.homeImage
import khazena.core.presentation.generated.resources.back
import khazena.core.presentation.generated.resources.close
import khazena.core.presentation.generated.resources.loading
import khazena.feature.home.presentation.generated.resources.Res
import khazena.feature.home.presentation.generated.resources.category_all
import khazena.feature.home.presentation.generated.resources.category_kicker
import khazena.feature.home.presentation.generated.resources.category_no_places
import khazena.feature.home.presentation.generated.resources.category_rating
import khazena.feature.home.presentation.generated.resources.category_search
import khazena.feature.home.presentation.generated.resources.category_sort_change
import khazena.feature.home.presentation.generated.resources.category_sort_discount
import khazena.feature.home.presentation.generated.resources.category_sort_distance
import khazena.feature.home.presentation.generated.resources.category_sort_rating
import khazena.feature.home.presentation.generated.resources.category_sort_title
import khazena.feature.home.presentation.generated.resources.category_sorted_discount
import khazena.feature.home.presentation.generated.resources.category_sorted_distance
import khazena.feature.home.presentation.generated.resources.category_sorted_rating
import khazena.feature.home.presentation.generated.resources.category_stat_open_now
import khazena.feature.home.presentation.generated.resources.category_stat_places
import khazena.feature.home.presentation.generated.resources.category_stat_top_discount
import khazena.feature.home.presentation.generated.resources.home_save_percent
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import khazena.core.presentation.generated.resources.Res as CoreRes

@Composable
fun CategoryRoot(onBack: () -> Unit, onOpenSearch: () -> Unit, viewModel: CategoryViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is CategoryEvent.ShowMessage -> toast.show(event.message)
        }
    }
    CategoryScreen(state, toast) { action ->
        when (action) {
            CategoryAction.OnBackClick -> onBack()
            CategoryAction.OnSearchClick -> onOpenSearch()
            // shortcut: the store screen isn't built yet, so a place tap does nothing.
            else -> Unit
        }
        viewModel.onAction(action)
    }
}

// Figma 02b: no tab bar; the white hero runs under the status bar with 36dp bottom corners.
private val HeroShape = RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp)

// KhazenaTopBar: 8 + 44 + 8. The hero starts under it so its kicker keeps the Figma offset.
private val TopBarHeight = 60.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(state: CategoryState, toastState: MessageToastState, onAction: (CategoryAction) -> Unit) {
    val detail = (state.detail.content as? LoadState.Data)?.value
    val colors = KhazenaTheme.colors
    val refreshState = rememberPullToRefreshState()
    val scroll = rememberScrollState()
    // Once the hero title has slid under the pinned bar, the bar shows the name instead, as iOS does.
    // Measured, not a fixed offset, so it holds for long names, larger text and Arabic/Kurdish line heights.
    var barBottom by remember { mutableFloatStateOf(0f) }
    var heroTitleBottom by remember { mutableFloatStateOf(Float.MAX_VALUE) }
    val titleUnderBar = heroTitleBottom <= barBottom
    Box(Modifier.fillMaxSize().background(colors.surfaceBackground)) {
        PullToRefreshBox(
            isRefreshing = state.detail.isRefreshing,
            onRefresh = { onAction(CategoryAction.OnRefresh) },
            state = refreshState,
            modifier = Modifier.fillMaxSize(),
            indicator = {
                PullToRefreshDefaults.Indicator(
                    state = refreshState,
                    isRefreshing = state.detail.isRefreshing,
                    modifier = Modifier.align(Alignment.TopCenter).statusBarsPadding().padding(top = TopBarHeight),
                )
            },
        ) {
            Column(Modifier.fillMaxSize().verticalScroll(scroll).navigationBarsPadding().padding(bottom = 32.dp)) {
                Hero(detail, loading = state.detail.content is LoadState.Loading, onTitleBottom = { heroTitleBottom = it })
                if (detail != null) {
                    CategoryBody(detail, state, onAction)
                } else {
                    LoadStateContent(
                        state = state.detail.content,
                        onRetry = { onAction(CategoryAction.OnRetry) },
                        empty = {},
                        modifier = Modifier.fillMaxWidth().padding(horizontal = KhazenaSpacing.screenMargin).padding(top = 18.dp),
                        loading = { BodySkeleton() },
                    ) {}
                }
            }
        }
        // White like the hero, so at rest the bar and hero read as one surface.
        KhazenaTopBar(
            title = detail?.name?.localized().orEmpty(),
            backLabel = stringResource(CoreRes.string.back),
            onBack = { onAction(CategoryAction.OnBackClick) },
            modifier = Modifier
                .onGloballyPositioned { barBottom = it.boundsInRoot().bottom }
                .background(colors.surfaceCard)
                .statusBarsPadding(),
            scrolled = titleUnderBar,
            titleVisible = titleUnderBar,
            backStyle = IconButtonStyle.Paper,
            action = {
                KhazenaIconButton(KhazenaIcons.MagnifyingGlassRegular, stringResource(Res.string.category_search), { onAction(CategoryAction.OnSearchClick) })
            },
        )
        MessageToastHost(toastState, Modifier.align(Alignment.BottomCenter).navigationBarsPadding().padding(bottom = 24.dp))
    }
    if (state.isSortSheetOpen) SortSheet(state.sort, onAction)
}

@Composable
private fun Hero(detail: CategoryDetail?, loading: Boolean, onTitleBottom: (Float) -> Unit) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Column(
        Modifier
            .fillMaxWidth()
            .multiplySafe()
            .clip(HeroShape)
            .background(colors.surfaceCard)
            .statusBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = TopBarHeight, bottom = 16.dp),
    ) {
        if (detail == null) {
            if (loading) HeroSkeleton()
            return@Column
        }
        Box(Modifier.fillMaxWidth()) {
            Image(
                painterResource(homeImage(detail.imageKey)),
                null,
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(y = (-4).dp)
                    .size(140.dp)
                    .graphicsLayer { blendMode = BlendMode.Multiply },
                contentScale = ContentScale.Crop,
            )
            Column(Modifier.padding(top = 21.dp, bottom = 8.dp)) {
                Text(stringResource(Res.string.category_kicker).uppercase(), style = type.monoKicker, color = colors.textSecondary)
                // Figma sets the name in a narrow column beside the art ("Food &" / "Cafes"); a single long word
                // ("Entertainment") shrinks to one line instead of breaking mid-word.
                val name = detail.name.localized()
                val singleWord = ' ' !in name.trim()
                val titleStyle = type.headingH2.copy(fontSize = 30.sp, lineHeight = 33.sp)
                Text(
                    name,
                    style = titleStyle,
                    color = colors.textPrimary,
                    maxLines = if (singleWord) 1 else 3,
                    autoSize = if (singleWord) TextAutoSize.StepBased(minFontSize = 20.sp, maxFontSize = titleStyle.fontSize) else null,
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .widthIn(max = 150.dp)
                        .onGloballyPositioned { onTitleBottom(it.positionInRoot().y + it.size.height) }
                        .semantics { heading() },
                )
                Text(
                    detail.description.localized(),
                    style = type.bodyS.copy(lineHeight = 18.sp),
                    color = colors.textSecondary,
                    modifier = Modifier.padding(top = 6.dp).widthIn(max = 190.dp),
                )
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            HeroStat(detail.placeCount.toString(), stringResource(Res.string.category_stat_places), Modifier.weight(1f))
            HeroStat("${detail.topDiscountPercent}%", stringResource(Res.string.category_stat_top_discount), Modifier.weight(1f))
            HeroStat(detail.openNow.toString(), stringResource(Res.string.category_stat_open_now), Modifier.weight(1f), live = true)
        }
    }
}

// Same blocks as the loaded hero: kicker, two-line title, two-line description beside the 140dp art, three stats.
@Composable
private fun HeroSkeleton() {
    KhazenaShimmer(stringResource(CoreRes.string.loading)) {
        Column {
            Box(Modifier.fillMaxWidth()) {
                KhazenaSkeleton(Modifier.align(Alignment.TopEnd).offset(y = (-4).dp).size(140.dp), KhazenaRadius.hero)
                Column(Modifier.padding(top = 21.dp, bottom = 8.dp)) {
                    KhazenaSkeleton(Modifier.size(width = 64.dp, height = 11.dp))
                    KhazenaSkeleton(Modifier.padding(top = 8.dp).size(width = 140.dp, height = 26.dp))
                    KhazenaSkeleton(Modifier.padding(top = 7.dp).size(width = 100.dp, height = 26.dp))
                    KhazenaSkeleton(Modifier.padding(top = 12.dp).size(width = 180.dp, height = 12.dp))
                    KhazenaSkeleton(Modifier.padding(top = 6.dp).size(width = 140.dp, height = 12.dp))
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                repeat(3) { KhazenaSkeleton(Modifier.weight(1f).height(50.dp), RoundedCornerShape(16.dp)) }
            }
        }
    }
}

// Chips, sort line and rated place rows, at the loaded body's spacing.
@Composable
private fun BodySkeleton() {
    KhazenaShimmer(stringResource(CoreRes.string.loading)) {
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(49, 105, 67, 77, 88).forEach { width ->
                    KhazenaSkeleton(Modifier.size(width = width.dp, height = KhazenaSize.chip), KhazenaRadius.pill)
                }
            }
            Row(Modifier.fillMaxWidth().padding(top = 26.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                KhazenaSkeleton(Modifier.size(width = 200.dp, height = 11.dp))
                KhazenaSkeleton(Modifier.size(width = 110.dp, height = 13.dp))
            }
            Column(Modifier.padding(top = 18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                repeat(4) { KhazenaSkeletonRow(rating = true, tag = true) }
            }
        }
    }
}

@Composable
private fun HeroStat(value: String, label: String, modifier: Modifier = Modifier, live: Boolean = false) {
    val colors = KhazenaTheme.colors
    Column(
        modifier
            .heightIn(min = 50.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surfaceBackground)
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 7.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
    ) {
        Text(
            value,
            style = KhazenaTheme.typography.labelLBold.copy(fontSize = 17.sp, lineHeight = 21.sp),
            color = if (live) colors.statusLive else colors.textPrimary,
            maxLines = 1,
        )
        Text(
            label,
            style = KhazenaTheme.typography.bodyS.copy(fontSize = 11.sp, lineHeight = 13.sp),
            color = colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun CategoryBody(detail: CategoryDetail, state: CategoryState, onAction: (CategoryAction) -> Unit) {
    val colors = KhazenaTheme.colors
    val places = detail.placesFor(state.subcategoryId, state.sort)
    // Chips lay out at the 48dp touch target around their 36dp pill, so the Figma gaps (18 above, 26 below) lose 6dp each side.
    if (detail.subcategories.isNotEmpty()) {
        LazyRow(
            Modifier.fillMaxWidth().padding(top = 12.dp).selectableGroup(),
            contentPadding = PaddingValues(horizontal = KhazenaSpacing.screenMargin),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item(key = "all") {
                KhazenaChip(stringResource(Res.string.category_all), state.subcategoryId == null, { onAction(CategoryAction.OnSubcategoryClick(null)) })
            }
            items(detail.subcategories, key = { it.id }) { sub ->
                KhazenaChip(sub.name.localized(), state.subcategoryId == sub.id, { onAction(CategoryAction.OnSubcategoryClick(sub.id)) })
            }
        }
        Spacer(Modifier.height(20.dp))
    } else {
        Spacer(Modifier.height(26.dp))
    }
    SortRow(detail.countFor(state.subcategoryId), state.sort, onAction)
    Spacer(Modifier.height(18.dp))
    Column(Modifier.padding(horizontal = KhazenaSpacing.screenMargin), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (places.isEmpty()) {
            Text(
                stringResource(Res.string.category_no_places),
                style = KhazenaTheme.typography.bodyM,
                color = colors.textSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            )
        }
        places.forEachIndexed { index, place ->
            PlaceRow(place, onAction, Modifier.revealOnAppear(index))
        }
    }
}

@Composable
private fun SortRow(count: Int, sort: PlaceSort, onAction: (CategoryAction) -> Unit) {
    val colors = KhazenaTheme.colors
    val sortLabel = stringResource(sort.label)
    val changeLabel = stringResource(Res.string.category_sort_change, sortLabel)
    Row(
        Modifier.fillMaxWidth().padding(horizontal = KhazenaSpacing.screenMargin),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            stringResource(sort.summary, count).uppercase(),
            style = KhazenaTheme.typography.monoTagS,
            color = colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        Row(
            Modifier
                .textClickable(onClick = { onAction(CategoryAction.OnSortClick) })
                .semantics { contentDescription = changeLabel },
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(sortLabel, style = KhazenaTheme.typography.labelSMedium.copy(fontWeight = FontWeight.SemiBold), color = colors.textPrimary, maxLines = 1)
            Icon(painterResource(KhazenaIcons.CaretDown), null, Modifier.size(14.dp), tint = colors.textPrimary)
        }
    }
}

@Composable
private fun PlaceRow(place: CategoryPlace, onAction: (CategoryAction) -> Unit, modifier: Modifier = Modifier) {
    KhazenaPlaceRow(
        photo = painterResource(homeImage(place.imageKey)),
        title = place.name.localized(),
        meta = "${place.kind.localized()} · ${place.area.localized()} · ${formatDistance(place.distanceKm)}",
        discountLabel = stringResource(Res.string.home_save_percent, place.discountPercent),
        rating = stringResource(Res.string.category_rating, place.rating.toString(), place.reviewCount),
        onClick = { onAction(CategoryAction.OnPlaceClick(place.id)) },
        modifier = modifier,
    )
}

@Composable
private fun SortSheet(current: PlaceSort, onAction: (CategoryAction) -> Unit) {
    val colors = KhazenaTheme.colors
    KhazenaBottomSheet(onDismiss = { onAction(CategoryAction.OnDismissSort) }) {
        KhazenaSheetTitle(
            title = stringResource(Res.string.category_sort_title),
            closeLabel = stringResource(CoreRes.string.close),
            onClose = { onAction(CategoryAction.OnDismissSort) },
        )
        Column(Modifier.selectableGroup()) {
            PlaceSort.entries.forEach { sort ->
                val selected = sort == current
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(KhazenaRadius.card)
                        .selectable(selected = selected, role = Role.RadioButton) { onAction(CategoryAction.OnPickSort(sort)) }
                        .padding(horizontal = 4.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(stringResource(sort.label), style = KhazenaTheme.typography.labelLBold, color = colors.textPrimary, modifier = Modifier.weight(1f))
                    KhazenaRadio(selected = selected, onClick = { onAction(CategoryAction.OnPickSort(sort)) })
                }
            }
        }
    }
}

private val PlaceSort.label: StringResource
    get() = when (this) {
        PlaceSort.Discount -> Res.string.category_sort_discount
        PlaceSort.Distance -> Res.string.category_sort_distance
        PlaceSort.Rating -> Res.string.category_sort_rating
    }

private val PlaceSort.summary: StringResource
    get() = when (this) {
        PlaceSort.Discount -> Res.string.category_sorted_discount
        PlaceSort.Distance -> Res.string.category_sorted_distance
        PlaceSort.Rating -> Res.string.category_sorted_rating
    }

private val previewStreet = LocalizedText("شەقامی سالم", "Salim St", "شارع سالم")
private val previewState = CategoryState(
    detail = ContentState(
        LoadState.Data(
            CategoryDetail(
                id = "food",
                name = LocalizedText("خواردن و کافێ", "Food & Cafes", "مطاعم ومقاهٍ"),
                description = LocalizedText(
                    "چێشتخانە، کافێ، شیرینی و زیاتر بە نرخی ئەندامان.",
                    "Restaurants, cafés, sweets and more at member prices.",
                    "مطاعم ومقاهٍ وحلويات والمزيد بأسعار الأعضاء.",
                ),
                imageKey = "cat_food",
                placeCount = 128,
                topDiscountPercent = 50,
                openNow = 86,
                subcategories = listOf(
                    Subcategory("restaurants", LocalizedText("چێشتخانە", "Restaurants", "مطاعم"), 52),
                    Subcategory("cafes", LocalizedText("کافێ", "Cafés", "مقاهٍ"), 31),
                    Subcategory("sweets", LocalizedText("شیرینی", "Sweets", "حلويات"), 18),
                ),
                places = listOf(
                    CategoryPlace("azmar", LocalizedText("ئازمەڕ گریل", "Azmar Grill", "أزمر غريل"), LocalizedText("گریل", "Grill", "مشاوي"), previewStreet, 1.4, 4.8, 126, 30, "photo_azmar", "restaurants"),
                    CategoryPlace("naz", LocalizedText("ناز کافێ", "Naz Café", "ناز كافيه"), LocalizedText("کافێ", "Café", "مقهى"), previewStreet, 0.6, 4.5, 61, 10, "photo_naz", "cafes"),
                ),
            ),
        ),
    ),
)

@Preview(name = "02b Category", widthDp = 390, heightDp = 1012)
@Composable
private fun CategoryPreview() = KhazenaTheme { CategoryScreen(previewState, rememberMessageToastState()) {} }

@Preview(name = "02b Category · RTL", widthDp = 390, heightDp = 1012)
@Composable
private fun CategoryRtlPreview() = KhazenaRtlPreview { CategoryScreen(previewState, rememberMessageToastState()) {} }

@Preview(name = "02b Category · loading", widthDp = 390, heightDp = 1012)
@Composable
private fun CategoryLoadingPreview() = KhazenaTheme { CategoryScreen(CategoryState(), rememberMessageToastState()) {} }
