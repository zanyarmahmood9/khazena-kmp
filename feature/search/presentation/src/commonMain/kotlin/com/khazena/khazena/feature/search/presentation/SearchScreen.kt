package com.khazena.khazena.feature.search.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaPlaceRow
import com.khazena.khazena.core.designsystem.component.KhazenaSearchField
import com.khazena.khazena.core.designsystem.component.KhazenaSectionHeader
import com.khazena.khazena.core.designsystem.component.KhazenaStateCard
import com.khazena.khazena.core.designsystem.component.StateTone
import com.khazena.khazena.core.designsystem.revealOnAppear
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.core.presentation.formatDiscount
import com.khazena.khazena.core.presentation.formatDistance
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.core.presentation.placeholderPhoto
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.core.presentation.state.LoadState
import com.khazena.khazena.core.presentation.state.MessageToastState
import com.khazena.khazena.core.presentation.state.StateScreen
import com.khazena.khazena.core.presentation.state.rememberMessageToastState
import com.khazena.khazena.feature.search.domain.Place
import khazena.core.presentation.generated.resources.Res as CoreRes
import khazena.core.presentation.generated.resources.tab_search
import khazena.feature.search.presentation.generated.resources.Res
import khazena.feature.search.presentation.generated.resources.search_clear
import khazena.feature.search.presentation.generated.resources.search_empty_action
import khazena.feature.search.presentation.generated.resources.search_empty_badge
import khazena.feature.search.presentation.generated.resources.search_empty_body
import khazena.feature.search.presentation.generated.resources.search_empty_title
import khazena.feature.search.presentation.generated.resources.search_nothing_body
import khazena.feature.search.presentation.generated.resources.search_nothing_title
import khazena.feature.search.presentation.generated.resources.search_placeholder
import khazena.feature.search.presentation.generated.resources.search_popular
import khazena.feature.search.presentation.generated.resources.search_results
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchRoot(viewModel: SearchViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is SearchEvent.ShowMessage -> toast.show(event.message)
        }
    }
    SearchScreen(state, toast, viewModel::onAction)
}

@Composable
fun SearchScreen(state: SearchState, toastState: MessageToastState, onAction: (SearchAction) -> Unit) {
    val searching = state.query.isNotBlank()
    StateScreen(
        state = state.results,
        onRefresh = { onAction(SearchAction.OnRefresh) },
        onRetry = { onAction(SearchAction.OnRetry) },
        toastState = toastState,
        header = {
            Text(
                stringResource(CoreRes.string.tab_search),
                style = KhazenaTheme.typography.headingH1,
                color = KhazenaTheme.colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
            KhazenaSearchField(
                value = state.query,
                onValueChange = { onAction(SearchAction.OnQueryChange(it)) },
                placeholder = stringResource(Res.string.search_placeholder),
                clearContentDescription = stringResource(Res.string.search_clear),
            )
        },
        empty = {
            if (searching) {
                KhazenaStateCard(
                    icon = KhazenaIcons.MagnifyingGlass,
                    badge = stringResource(Res.string.search_empty_badge),
                    title = stringResource(Res.string.search_empty_title, state.query.trim()),
                    body = stringResource(Res.string.search_empty_body),
                    actionLabel = stringResource(Res.string.search_empty_action),
                    onAction = { onAction(SearchAction.OnClearQuery) },
                )
            } else {
                KhazenaStateCard(
                    icon = KhazenaIcons.Storefront,
                    badge = stringResource(Res.string.search_empty_badge),
                    title = stringResource(Res.string.search_nothing_title),
                    body = stringResource(Res.string.search_nothing_body),
                    tone = StateTone.Sun,
                )
            }
        },
    ) { places ->
        KhazenaSectionHeader(
            stringResource(if (searching) Res.string.search_results else Res.string.search_popular),
            Modifier.revealOnAppear(0),
        )
        places.forEachIndexed { index, place ->
            KhazenaPlaceRow(
                photo = placeholderPhoto(index),
                title = place.name.localized(),
                meta = "${place.category.localized()} · ${formatDistance(place.distanceKm)}",
                discountLabel = formatDiscount(place.discountPercent),
                modifier = Modifier.revealOnAppear(index + 1),
            )
        }
    }
}

@Preview(name = "Data", widthDp = 390, heightDp = 844)
@Composable
private fun SearchDataPreview() = KhazenaTheme {
    val place = Place("1", LocalizedText("ئازمەڕ", "Azmar Grill", "أزمر"), LocalizedText("خواردن", "Food & Cafes", "مطاعم"), 30, 0.8)
    SearchScreen(SearchState(results = ContentState(LoadState.Data(listOf(place)))), rememberMessageToastState()) {}
}

@Preview(name = "No results", widthDp = 390, heightDp = 844)
@Composable
private fun SearchEmptyPreview() = KhazenaTheme {
    SearchScreen(SearchState("sushi", ContentState(LoadState.Empty)), rememberMessageToastState()) {}
}
