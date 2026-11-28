package com.khazena.khazena.feature.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaRtlPreview
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.bleedHorizontally
import com.khazena.khazena.core.designsystem.component.KhazenaCategoryTile
import com.khazena.khazena.core.designsystem.component.KhazenaDealCard
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaPlaceRow
import com.khazena.khazena.core.designsystem.component.KhazenaSectionHeader
import com.khazena.khazena.core.designsystem.component.KhazenaStateCard
import com.khazena.khazena.core.designsystem.component.StateTone
import com.khazena.khazena.core.designsystem.revealOnAppear
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.core.presentation.formatDistance
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.core.presentation.permission.AskLocationOnce
import com.khazena.khazena.core.presentation.permission.LocationPermissionStatus
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.core.presentation.state.LoadState
import com.khazena.khazena.core.presentation.state.MessageToastState
import com.khazena.khazena.core.presentation.state.StateScreen
import com.khazena.khazena.core.presentation.state.rememberMessageToastState
import com.khazena.khazena.feature.home.domain.HomeCategory
import com.khazena.khazena.feature.home.domain.HomeFeed
import com.khazena.khazena.feature.home.domain.NearbyPlace
import com.khazena.khazena.feature.home.domain.TopDeal
import khazena.feature.home.presentation.generated.resources.Res
import khazena.feature.home.presentation.generated.resources.home_category_badge
import khazena.feature.home.presentation.generated.resources.home_deal_off
import khazena.feature.home.presentation.generated.resources.home_empty_badge
import khazena.feature.home.presentation.generated.resources.home_empty_body
import khazena.feature.home.presentation.generated.resources.home_empty_title
import khazena.feature.home.presentation.generated.resources.home_map
import khazena.feature.home.presentation.generated.resources.home_rating
import khazena.feature.home.presentation.generated.resources.home_redeem
import khazena.feature.home.presentation.generated.resources.home_save_percent
import khazena.feature.home.presentation.generated.resources.home_section_categories
import khazena.feature.home.presentation.generated.resources.home_section_deals
import khazena.feature.home.presentation.generated.resources.home_section_near_you
import khazena.feature.home.presentation.generated.resources.home_see_all
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoot(
    onSwitchAccount: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenWallet: () -> Unit,
    onScanMe: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is HomeEvent.ShowMessage -> toast.show(event.message)
        }
    }
    val location = AskLocationOnce()
    HomeScreen(
        state = state,
        toastState = toast,
        locationOn = location.status == LocationPermissionStatus.Granted,
        onAction = { action ->
            when (action) {
                HomeAction.OnTurnOnLocationClick -> location.enable()
                HomeAction.OnSwitchAccountClick -> onSwitchAccount()
                HomeAction.OnSearchClick -> onOpenSearch()
                HomeAction.OnWalletClick -> onOpenWallet()
                HomeAction.OnScanToSaveClick -> onScanMe()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun HomeScreen(state: HomeState, toastState: MessageToastState, locationOn: Boolean, onAction: (HomeAction) -> Unit) {
    val feed = (state.feed.content as? LoadState.Data)?.value
    StateScreen(
        state = state.feed,
        onRefresh = { onAction(HomeAction.OnRefresh) },
        onRetry = { onAction(HomeAction.OnRetry) },
        toastState = toastState,
        header = { HomeHeader(feed, state.daypart, onAction) },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.MapPin,
                badge = stringResource(Res.string.home_empty_badge),
                title = stringResource(Res.string.home_empty_title),
                body = stringResource(Res.string.home_empty_body),
                tone = StateTone.Sun,
            )
        },
    ) { data -> HomeContent(data, locationOn, onAction) }
}

// Vertical rhythm from Figma (02 Home · Customer): 30dp above each section header, 8dp below it.
private val SectionGap = 30.dp
private val HeaderGap = 8.dp

@Composable
private fun HomeContent(feed: HomeFeed, locationOn: Boolean, onAction: (HomeAction) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        MembershipCard(feed.member, onAction, Modifier.revealOnAppear(0))

        if (feed.categories.isNotEmpty()) {
            Spacer(Modifier.height(SectionGap))
            KhazenaSectionHeader(stringResource(Res.string.home_section_categories), linkLabel = stringResource(Res.string.home_see_all))
            Spacer(Modifier.height(HeaderGap))
            CategoryGrid(feed.categories, Modifier.revealOnAppear(1))
        }

        feed.featuredOffer?.let { offer ->
            Spacer(Modifier.height(32.dp))
            FeaturedOfferCard(offer, Modifier.revealOnAppear(2))
        }

        if (feed.topDeals.isNotEmpty()) {
            Spacer(Modifier.height(SectionGap))
            KhazenaSectionHeader(stringResource(Res.string.home_section_deals), linkLabel = stringResource(Res.string.home_see_all))
            Spacer(Modifier.height(HeaderGap))
            LazyRow(
                Modifier.bleedHorizontally(KhazenaSpacing.screenMargin).revealOnAppear(3),
                contentPadding = PaddingValues(horizontal = KhazenaSpacing.screenMargin),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(feed.topDeals, key = { it.id }) { deal -> TopDealCard(deal) }
            }
        }

        if (!locationOn) {
            Spacer(Modifier.height(SectionGap))
            KhazenaSectionHeader(stringResource(Res.string.home_section_near_you))
            Spacer(Modifier.height(HeaderGap))
            LocationOffCard({ onAction(HomeAction.OnTurnOnLocationClick) }, Modifier.revealOnAppear(4))
        } else if (feed.nearby.isNotEmpty()) {
            Spacer(Modifier.height(SectionGap))
            KhazenaSectionHeader(stringResource(Res.string.home_section_near_you), linkLabel = stringResource(Res.string.home_map))
            Spacer(Modifier.height(HeaderGap))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                feed.nearby.forEachIndexed { index, place -> NearbyRow(place, Modifier.revealOnAppear(index + 4)) }
            }
        }

        Spacer(Modifier.height(18.dp))
        InviteCard()
    }
}

@Composable
private fun CategoryGrid(categories: List<HomeCategory>, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        categories.chunked(4).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                row.forEach { category ->
                    KhazenaCategoryTile(
                        art = painterResource(homeImage(category.imageKey)),
                        label = category.name.localized(),
                        badge = category.maxDiscountPercent?.let { stringResource(Res.string.home_category_badge, it) },
                    )
                }
            }
        }
    }
}

@Composable
private fun TopDealCard(deal: TopDeal) {
    val rating = stringResource(Res.string.home_rating, deal.rating.toString())
    val area = deal.area
    KhazenaDealCard(
        photo = painterResource(homeImage(deal.imageKey)),
        title = deal.name.localized(),
        meta = if (area != null) "${deal.kind.localized()} · ${area.localized()}" else "${deal.kind.localized()} · $rating",
        discountLabel = stringResource(Res.string.home_deal_off, deal.discountPercent),
        actionLabel = stringResource(Res.string.home_redeem),
        ratingLabel = if (area != null) rating else null,
    )
}

@Composable
private fun NearbyRow(place: NearbyPlace, modifier: Modifier = Modifier) {
    KhazenaPlaceRow(
        photo = painterResource(homeImage(place.imageKey)),
        title = place.name.localized(),
        meta = "${place.kind.localized()} · ${formatDistance(place.distanceKm)}",
        discountLabel = stringResource(Res.string.home_save_percent, place.discountPercent),
        modifier = modifier,
    )
}

@Preview(name = "Data", widthDp = 390, heightDp = 1990)
@Composable
private fun HomeDataPreview() = KhazenaTheme {
    HomeScreen(HomeState(ContentState(LoadState.Data(previewFeed))), rememberMessageToastState(), locationOn = true) {}
}

@Preview(name = "Location off", widthDp = 390, heightDp = 2100)
@Composable
private fun HomeLocationOffPreview() = KhazenaTheme {
    HomeScreen(HomeState(ContentState(LoadState.Data(previewFeed))), rememberMessageToastState(), locationOn = false) {}
}

@Preview(name = "Data · RTL", widthDp = 390, heightDp = 1990)
@Composable
private fun HomeRtlPreview() = KhazenaRtlPreview {
    HomeScreen(HomeState(ContentState(LoadState.Data(previewFeed))), rememberMessageToastState(), locationOn = true) {}
}

@Preview(name = "Loading", widthDp = 390, heightDp = 844)
@Composable
private fun HomeLoadingPreview() = KhazenaTheme { HomeScreen(HomeState(), rememberMessageToastState(), locationOn = true) {} }
