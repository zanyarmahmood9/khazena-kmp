package com.khazena.khazena.feature.home.presentation.categories

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.khazena.khazena.core.designsystem.KhazenaMotion
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaRtlPreview
import com.khazena.khazena.core.designsystem.KhazenaSize
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.clearFocusOnTap
import com.khazena.khazena.core.designsystem.component.IconButtonStyle
import com.khazena.khazena.core.designsystem.component.KhazenaBottomSheet
import com.khazena.khazena.core.designsystem.component.KhazenaChip
import com.khazena.khazena.core.designsystem.component.KhazenaIconButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaPlaceRow
import com.khazena.khazena.core.designsystem.component.KhazenaRadio
import com.khazena.khazena.core.designsystem.component.KhazenaSearchField
import com.khazena.khazena.core.designsystem.component.KhazenaSheetTitle
import com.khazena.khazena.core.designsystem.component.KhazenaShimmer
import com.khazena.khazena.core.designsystem.component.KhazenaSkeleton
import com.khazena.khazena.core.designsystem.component.KhazenaSkeletonRow
import com.khazena.khazena.core.designsystem.component.KhazenaSkeletonText
import com.khazena.khazena.core.designsystem.component.KhazenaTopBar
import com.khazena.khazena.core.designsystem.component.multiplySafe
import com.khazena.khazena.core.designsystem.softReveal
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
import khazena.feature.home.presentation.generated.resources.category_no_match
import khazena.feature.home.presentation.generated.resources.category_no_places
import khazena.feature.home.presentation.generated.resources.category_rating
import khazena.feature.home.presentation.generated.resources.category_search
import khazena.feature.home.presentation.generated.resources.category_search_cancel
import khazena.feature.home.presentation.generated.resources.category_search_clear
import khazena.feature.home.presentation.generated.resources.category_search_hint
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
fun CategoryRoot(onBack: () -> Unit, onOpenStore: (String) -> Unit, viewModel: CategoryViewModel = koinViewModel()) {
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
            is CategoryAction.OnPlaceClick -> onOpenStore(action.id)
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
    // Where the chips start inside the scrolling column, so opening search can lift the places up under the bar.
    var bodyTop by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(state.isSearchOpen) {
        if (state.isSearchOpen) scroll.animateScrollTo((bodyTop - barBottom).toInt().coerceAtLeast(0))
    }
    // Back closes the search first, as the Cancel button does.
    NavigationBackHandler(
        state = rememberNavigationEventState(NavigationEventInfo.None),
        isBackEnabled = state.isSearchOpen,
        onBackCompleted = { onAction(CategoryAction.OnCloseSearch) },
    )
    Box(Modifier.fillMaxSize().background(colors.surfaceBackground).clearFocusOnTap()) {
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
                // One cross-fade per state change, in step with the hero's. (A loading container nested in here would
                // fade the skeleton out twice, so it vanished before the content arrived.)
                AnimatedContent(
                    targetState = state.detail.content,
                    modifier = Modifier.onGloballyPositioned { bodyTop = it.positionInParent().y },
                    contentKey = { it::class },
                    transitionSpec = { softReveal() },
                    contentAlignment = Alignment.TopCenter,
                    label = "category-body",
                ) { content ->
                    when (content) {
                        is LoadState.Data -> Column(Modifier.fillMaxWidth()) { CategoryBody(content.value, state, onAction) }
                        LoadState.Loading -> BodySkeleton(Modifier.padding(horizontal = KhazenaSpacing.screenMargin).padding(top = 18.dp))
                        else -> LoadStateContent(
                            state = content,
                            onRetry = { onAction(CategoryAction.OnRetry) },
                            empty = {},
                            modifier = Modifier.fillMaxWidth().padding(horizontal = KhazenaSpacing.screenMargin).padding(top = 18.dp),
                        ) {}
                    }
                }
            }
        }
        // White like the hero, so at rest the bar and hero read as one surface. The search icon swaps the bar
        // for a field that filters this category's places, iOS-style, with Cancel to return.
        AnimatedContent(
            targetState = state.isSearchOpen,
            modifier = Modifier
                .onGloballyPositioned { barBottom = it.boundsInRoot().bottom }
                .background(colors.surfaceCard)
                .statusBarsPadding(),
            transitionSpec = { fadeIn(tween(KhazenaMotion.MEDIUM)) togetherWith fadeOut(tween(KhazenaMotion.SHORT)) },
            label = "category-bar",
        ) { searching ->
            if (searching) {
                SearchBar(detail?.name?.localized().orEmpty(), state.query, onAction)
            } else {
                KhazenaTopBar(
                    title = detail?.name?.localized().orEmpty(),
                    backLabel = stringResource(CoreRes.string.back),
                    onBack = { onAction(CategoryAction.OnBackClick) },
                    scrolled = titleUnderBar,
                    titleVisible = titleUnderBar,
                    backStyle = IconButtonStyle.Paper,
                    action = {
                        KhazenaIconButton(KhazenaIcons.MagnifyingGlassRegular, stringResource(Res.string.category_search), { onAction(CategoryAction.OnSearchClick) })
                    },
                )
            }
        }
        MessageToastHost(toastState, Modifier.align(Alignment.BottomCenter).navigationBarsPadding().padding(bottom = 24.dp))
    }
    if (state.isSortSheetOpen) SortSheet(state.sort, onAction)
}

@Composable
private fun SearchBar(categoryName: String, query: String, onAction: (CategoryAction) -> Unit) {
    val focus = remember { FocusRequester() }
    LaunchedEffect(Unit) { focus.requestFocus() }
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        KhazenaSearchField(
            value = query,
            onValueChange = { onAction(CategoryAction.OnQueryChange(it)) },
            placeholder = stringResource(Res.string.category_search_hint, categoryName),
            clearContentDescription = stringResource(Res.string.category_search_clear),
            modifier = Modifier.weight(1f).focusRequester(focus),
            height = KhazenaSize.tapMin,
        )
        Text(
            stringResource(Res.string.category_search_cancel),
            style = KhazenaTheme.typography.labelMSemiBold,
            color = KhazenaTheme.colors.textPrimary,
            modifier = Modifier.textClickable(onClick = { onAction(CategoryAction.OnCloseSearch) }),
        )
    }
}

@Composable
private fun Hero(detail: CategoryDetail?, loading: Boolean, onTitleBottom: (Float) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .multiplySafe()
            .clip(HeroShape)
            .background(KhazenaTheme.colors.surfaceCard)
            .statusBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = TopBarHeight, bottom = 16.dp),
    ) {
        AnimatedContent(
            targetState = detail,
            contentKey = { it != null },
            transitionSpec = { softReveal() },
            contentAlignment = Alignment.TopCenter,
            label = "category-hero",
        ) { loaded ->
            if (loaded != null) HeroContent(loaded, onTitleBottom) else if (loading) HeroSkeleton()
        }
    }
}

@Composable
private fun HeroContent(detail: CategoryDetail, onTitleBottom: (Float) -> Unit) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Column(Modifier.fillMaxWidth()) {
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
                val titleStyle = heroTitleStyle()
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
                    style = heroDescriptionStyle(),
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
                // Same text styles and paddings as HeroContent, so every line lands where its text will.
                Column(Modifier.padding(top = 21.dp, bottom = 8.dp)) {
                    KhazenaSkeletonText(KhazenaTheme.typography.monoKicker, listOf(64.dp))
                    KhazenaSkeletonText(heroTitleStyle(), listOf(140.dp, 100.dp), Modifier.padding(top = 2.dp))
                    KhazenaSkeletonText(heroDescriptionStyle(), listOf(180.dp, 140.dp), Modifier.padding(top = 6.dp))
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
private fun BodySkeleton(modifier: Modifier = Modifier) {
    KhazenaShimmer(stringResource(CoreRes.string.loading), modifier) {
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(49, 105, 67, 77, 88).forEach { width ->
                    KhazenaSkeleton(Modifier.size(width = width.dp, height = KhazenaSize.chip), KhazenaRadius.pill)
                }
            }
            // SortRow: kicker on the start, the sort label on the end, centred in one row.
            Row(
                Modifier.fillMaxWidth().padding(top = 26.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                KhazenaSkeletonText(KhazenaTheme.typography.monoTagS, listOf(200.dp))
                KhazenaSkeletonText(KhazenaTheme.typography.labelSMedium, listOf(110.dp))
            }
            Column(Modifier.padding(top = 18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                repeat(4) { KhazenaSkeletonRow(rating = true, tag = true) }
            }
        }
    }
}

@Composable
private fun heroTitleStyle() = KhazenaTheme.typography.headingH2.copy(fontSize = 30.sp, lineHeight = 33.sp)

@Composable
private fun heroDescriptionStyle() = KhazenaTheme.typography.bodyS.copy(lineHeight = 18.sp)

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
    // "All" first, then the business sub-categories that have places; the index drives scrolling and slide direction.
    val subcategories = remember(detail) { detail.subcategoriesWithPlaces }
    val order = remember(subcategories) { listOf<String?>(null) + subcategories.map { it.id } }
    // A saved pick whose chip is now hidden falls back to "All".
    val subcategoryId = state.subcategoryId?.takeIf { it in order }
    // Chips lay out at the 48dp touch target around their 36dp pill, so the Figma gaps (18 above, 26 below) lose 6dp each side.
    if (subcategories.isNotEmpty()) {
        val chips = rememberLazyListState()
        val selectedIndex = order.indexOf(subcategoryId).coerceAtLeast(0)
        // Centre the picked chip, so tapping one near either edge brings its neighbours into view.
        LaunchedEffect(selectedIndex) {
            val info = chips.layoutInfo
            val viewport = info.viewportEndOffset - info.viewportStartOffset
            val chipWidth = info.visibleItemsInfo.firstOrNull { it.index == selectedIndex }?.size ?: 0
            chips.animateScrollToItem(selectedIndex, -(viewport - chipWidth) / 2)
        }
        LazyRow(
            Modifier.fillMaxWidth().padding(top = 12.dp).selectableGroup(),
            state = chips,
            contentPadding = PaddingValues(horizontal = KhazenaSpacing.screenMargin),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item(key = "all") {
                KhazenaChip(stringResource(Res.string.category_all), subcategoryId == null, { onAction(CategoryAction.OnSubcategoryClick(null)) })
            }
            items(subcategories, key = { it.id }) { sub ->
                KhazenaChip(sub.name.localized(), subcategoryId == sub.id, { onAction(CategoryAction.OnSubcategoryClick(sub.id)) })
            }
        }
        Spacer(Modifier.height(20.dp))
    } else {
        Spacer(Modifier.height(26.dp))
    }
    val query = state.query.trim()
    val count = if (query.isEmpty()) detail.countFor(subcategoryId) else detail.placesFor(subcategoryId, state.sort).matching(query).size
    SortRow(count, state.sort, onAction)
    Spacer(Modifier.height(18.dp))
    val rtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    AnimatedContent(
        targetState = subcategoryId to state.sort,
        transitionSpec = {
            val from = order.indexOf(initialState.first)
            val to = order.indexOf(targetState.first)
            if (from == to) {
                // Same chip, new sort order: a quiet cross-fade.
                fadeIn(tween(KhazenaMotion.MEDIUM)) togetherWith fadeOut(tween(KhazenaMotion.SHORT))
            } else {
                // The list follows the chip: a later chip brings places in from the trailing side, an earlier one from the leading side.
                val fromEnd = (to > from) != rtl
                val slide = tween<IntOffset>(KhazenaMotion.LONG, easing = KhazenaMotion.EmphasizedDecelerate)
                (slideInHorizontally(slide) { if (fromEnd) it / 3 else -it / 3 } + fadeIn(tween(KhazenaMotion.MEDIUM))) togetherWith
                    (slideOutHorizontally(slide) { if (fromEnd) -it / 3 else it / 3 } + fadeOut(tween(KhazenaMotion.SHORT)))
            } using SizeTransform(clip = false)
        },
        contentAlignment = Alignment.TopCenter,
        label = "category-places",
    ) { (subcategoryId, sort) ->
        PlacesList(detail.placesFor(subcategoryId, sort).matching(query), query, onAction)
    }
}

// Search inside this category: the place's name, type or area, in the reader's language.
@Composable
private fun List<CategoryPlace>.matching(query: String): List<CategoryPlace> =
    if (query.isEmpty()) {
        this
    } else {
        filter { place ->
            listOf(place.name, place.kind, place.area).any { it.localized().contains(query, ignoreCase = true) }
        }
    }

@Composable
private fun PlacesList(places: List<CategoryPlace>, query: String, onAction: (CategoryAction) -> Unit) {
    Column(Modifier.fillMaxWidth().padding(horizontal = KhazenaSpacing.screenMargin), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (places.isEmpty()) {
            Text(
                if (query.isEmpty()) stringResource(Res.string.category_no_places) else stringResource(Res.string.category_no_match, query),
                style = KhazenaTheme.typography.bodyM,
                color = KhazenaTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            )
        }
        places.forEach { place -> PlaceRow(place, onAction) }
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
