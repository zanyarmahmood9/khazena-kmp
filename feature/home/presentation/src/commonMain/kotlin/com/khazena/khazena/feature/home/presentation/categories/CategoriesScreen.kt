package com.khazena.khazena.feature.home.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaRtlPreview
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.IconButtonStyle
import com.khazena.khazena.core.designsystem.component.KhazenaCategoryCard
import com.khazena.khazena.core.designsystem.component.KhazenaIconButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaSearchField
import com.khazena.khazena.core.designsystem.component.KhazenaShimmer
import com.khazena.khazena.core.designsystem.component.KhazenaSkeleton
import com.khazena.khazena.core.designsystem.component.KhazenaStateCard
import com.khazena.khazena.core.designsystem.component.StateTone
import com.khazena.khazena.core.designsystem.revealOnAppear
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.core.presentation.state.LoadState
import com.khazena.khazena.core.presentation.state.MessageToastState
import com.khazena.khazena.core.presentation.state.StateScreen
import com.khazena.khazena.core.presentation.state.rememberMessageToastState
import com.khazena.khazena.feature.home.domain.CategoryDirectory
import com.khazena.khazena.feature.home.domain.HomeCategory
import com.khazena.khazena.feature.home.presentation.homeImage
import com.khazena.khazena.feature.home.presentation.previewFeed
import khazena.core.presentation.generated.resources.back
import khazena.core.presentation.generated.resources.loading
import khazena.feature.home.presentation.generated.resources.Res
import khazena.feature.home.presentation.generated.resources.categories_empty_badge
import khazena.feature.home.presentation.generated.resources.categories_empty_body
import khazena.feature.home.presentation.generated.resources.categories_empty_title
import khazena.feature.home.presentation.generated.resources.categories_no_match
import khazena.feature.home.presentation.generated.resources.categories_places
import khazena.feature.home.presentation.generated.resources.categories_search_clear
import khazena.feature.home.presentation.generated.resources.categories_search_hint
import khazena.feature.home.presentation.generated.resources.categories_summary
import khazena.feature.home.presentation.generated.resources.categories_title
import khazena.feature.home.presentation.generated.resources.home_category_badge
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import khazena.core.presentation.generated.resources.Res as CoreRes

@Composable
fun CategoriesRoot(onBack: () -> Unit, onOpenCategory: (String) -> Unit, viewModel: CategoriesViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is CategoriesEvent.ShowMessage -> toast.show(event.message)
        }
    }
    CategoriesScreen(state, toast) { action ->
        when (action) {
            CategoriesAction.OnBackClick -> onBack()
            is CategoriesAction.OnCategoryClick -> onOpenCategory(action.id)
            else -> Unit
        }
        viewModel.onAction(action)
    }
}

@Composable
fun CategoriesScreen(state: CategoriesState, toastState: MessageToastState, onAction: (CategoriesAction) -> Unit) {
    StateScreen(
        state = state.directory,
        onRefresh = { onAction(CategoriesAction.OnRefresh) },
        onRetry = { onAction(CategoriesAction.OnRetry) },
        toastState = toastState,
        bottomClearance = 48.dp,
        header = {
            // Figma 02a: 44dp top bar, 16dp to the search field.
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    KhazenaIconButton(
                        KhazenaIcons.ArrowLeftRegular,
                        stringResource(CoreRes.string.back),
                        { onAction(CategoriesAction.OnBackClick) },
                        Modifier.align(Alignment.CenterStart),
                        style = IconButtonStyle.Outlined,
                    )
                    Text(
                        stringResource(Res.string.categories_title),
                        style = KhazenaTheme.typography.headingH4.copy(fontSize = 20.sp),
                        color = KhazenaTheme.colors.textPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 56.dp).semantics { heading() },
                    )
                }
                KhazenaSearchField(
                    value = state.query,
                    onValueChange = { onAction(CategoriesAction.OnQueryChange(it)) },
                    placeholder = stringResource(Res.string.categories_search_hint),
                    clearContentDescription = stringResource(Res.string.categories_search_clear),
                )
            }
        },
        loading = { CategoriesSkeleton() },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.Storefront,
                badge = stringResource(Res.string.categories_empty_badge),
                title = stringResource(Res.string.categories_empty_title),
                body = stringResource(Res.string.categories_empty_body),
                tone = StateTone.Sun,
            )
        },
    ) { directory -> CategoriesContent(directory, state.query.trim(), onAction) }
}

@Composable
private fun CategoriesContent(directory: CategoryDirectory, query: String, onAction: (CategoriesAction) -> Unit) {
    val colors = KhazenaTheme.colors
    val visible = directory.categories.filter { query.isEmpty() || it.name.localized().contains(query, ignoreCase = true) }
    Column(Modifier.fillMaxWidth()) {
        // 22dp below the search field in Figma; the screen already spaces header and content by 20dp.
        Text(
            stringResource(Res.string.categories_summary, directory.categories.size, directory.totalPlaces, directory.city.localized()).uppercase(),
            style = KhazenaTheme.typography.monoTagS,
            color = colors.textSecondary,
            modifier = Modifier.padding(top = 2.dp),
        )
        Spacer(Modifier.height(14.dp))
        if (visible.isEmpty()) {
            Text(
                stringResource(Res.string.categories_no_match, query),
                style = KhazenaTheme.typography.bodyM,
                color = colors.textSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            visible.chunked(2).forEachIndexed { rowIndex, row ->
                Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    row.forEach { category ->
                        CategoryCard(category, onAction, Modifier.weight(1f).fillMaxHeight().revealOnAppear(rowIndex))
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

// Mirrors CategoriesContent + KhazenaCategoryCard geometry (summary line, 2-column 176dp cards).
@Composable
private fun CategoriesSkeleton() {
    KhazenaShimmer(stringResource(CoreRes.string.loading)) {
        Column(Modifier.fillMaxWidth()) {
            KhazenaSkeleton(Modifier.padding(top = 2.dp).size(width = 240.dp, height = 11.dp))
            Spacer(Modifier.height(17.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                repeat(4) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        repeat(2) { CategoryCardSkeleton(Modifier.weight(1f)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryCardSkeleton(modifier: Modifier = Modifier) {
    Box(modifier.height(176.dp).clip(KhazenaRadius.card).background(KhazenaTheme.colors.surfaceCard)) {
        KhazenaSkeleton(Modifier.padding(10.dp).size(width = 78.dp, height = 22.dp), KhazenaRadius.pill)
        KhazenaSkeleton(Modifier.align(Alignment.TopEnd).padding(10.dp).size(28.dp), KhazenaRadius.pill)
        Column(Modifier.fillMaxWidth().padding(top = 26.dp)) {
            KhazenaSkeleton(Modifier.align(Alignment.CenterHorizontally).size(84.dp), KhazenaRadius.card)
            KhazenaSkeleton(Modifier.padding(start = 14.dp, top = 14.dp).size(width = 96.dp, height = 14.dp))
            KhazenaSkeleton(Modifier.padding(start = 14.dp, top = 7.dp).size(width = 60.dp, height = 11.dp))
        }
    }
}

@Composable
private fun CategoryCard(category: HomeCategory, onAction: (CategoriesAction) -> Unit, modifier: Modifier = Modifier) {
    KhazenaCategoryCard(
        art = painterResource(homeImage(category.imageKey)),
        label = category.name.localized(),
        meta = pluralStringResource(Res.plurals.categories_places, category.placeCount, category.placeCount),
        onClick = { onAction(CategoriesAction.OnCategoryClick(category.id)) },
        badge = category.maxDiscountPercent?.let { stringResource(Res.string.home_category_badge, it) },
        modifier = modifier,
    )
}

private val previewState = CategoriesState(
    directory = ContentState(
        LoadState.Data(
            CategoryDirectory(
                city = previewFeed.city,
                categories = previewFeed.categories.mapIndexed { i, c ->
                    c.copy(maxDiscountPercent = listOf(50, 25, 30, 40, 35, 20, 30, 45)[i], placeCount = listOf(128, 64, 41, 87, 52, 29, 46, 23)[i])
                },
            ),
        ),
    ),
)

@Preview(name = "02a Categories", widthDp = 390, heightDp = 1080)
@Composable
private fun CategoriesPreview() = KhazenaTheme { CategoriesScreen(previewState, rememberMessageToastState()) {} }

@Preview(name = "02a Categories · RTL", widthDp = 390, heightDp = 1080)
@Composable
private fun CategoriesRtlPreview() = KhazenaRtlPreview { CategoriesScreen(previewState, rememberMessageToastState()) {} }

@Preview(name = "02a Categories · loading", widthDp = 390, heightDp = 1080)
@Composable
private fun CategoriesLoadingPreview() = KhazenaTheme { CategoriesScreen(CategoriesState(), rememberMessageToastState()) {} }
