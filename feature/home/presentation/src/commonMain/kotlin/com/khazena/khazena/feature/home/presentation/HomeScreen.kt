package com.khazena.khazena.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.AccountMode
import com.khazena.khazena.core.designsystem.component.KhazenaHomeHeader
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaOfferBlock
import com.khazena.khazena.core.designsystem.component.KhazenaPlaceRow
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
import com.khazena.khazena.feature.home.domain.Deal
import khazena.core.presentation.generated.resources.Res as CoreRes
import khazena.core.presentation.generated.resources.notifications
import khazena.core.presentation.generated.resources.switch_to_business
import khazena.feature.home.presentation.generated.resources.Res
import khazena.feature.home.presentation.generated.resources.home_empty_badge
import khazena.feature.home.presentation.generated.resources.home_empty_body
import khazena.feature.home.presentation.generated.resources.home_empty_title
import khazena.feature.home.presentation.generated.resources.home_greeting
import khazena.feature.home.presentation.generated.resources.home_offer_body
import khazena.feature.home.presentation.generated.resources.home_offer_headline
import khazena.feature.home.presentation.generated.resources.home_offer_kicker
import khazena.feature.home.presentation.generated.resources.home_section_deals
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoot(
    onSwitchAccount: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is HomeEvent.ShowMessage -> toast.show(event.message)
        }
    }
    HomeScreen(
        state = state,
        toastState = toast,
        onAction = { action ->
            when (action) {
                HomeAction.OnSwitchAccountClick -> onSwitchAccount()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun HomeScreen(state: HomeState, toastState: MessageToastState, onAction: (HomeAction) -> Unit) {
    StateScreen(
        state = state.deals,
        onRefresh = { onAction(HomeAction.OnRefresh) },
        onRetry = { onAction(HomeAction.OnRetry) },
        toastState = toastState,
        header = {
            KhazenaHomeHeader(
                mode = AccountMode.Personal,
                greeting = stringResource(Res.string.home_greeting),
                switchLabel = stringResource(CoreRes.string.switch_to_business),
                notificationsLabel = stringResource(CoreRes.string.notifications),
                onSwitchAccount = { onAction(HomeAction.OnSwitchAccountClick) },
                onNotificationsClick = {},
            )
        },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.MapPin,
                badge = stringResource(Res.string.home_empty_badge),
                title = stringResource(Res.string.home_empty_title),
                body = stringResource(Res.string.home_empty_body),
                tone = StateTone.Sun,
            )
        },
    ) { deals ->
        KhazenaOfferBlock(
            kicker = stringResource(Res.string.home_offer_kicker),
            headline = stringResource(Res.string.home_offer_headline),
            body = stringResource(Res.string.home_offer_body),
            modifier = Modifier.revealOnAppear(0),
        )
        KhazenaSectionHeader(stringResource(Res.string.home_section_deals), Modifier.revealOnAppear(1))
        deals.forEachIndexed { index, deal ->
            KhazenaPlaceRow(
                photo = placeholderPhoto(index),
                title = deal.merchant.localized(),
                meta = "${deal.category.localized()} · ${formatDistance(deal.distanceKm)}",
                discountLabel = formatDiscount(deal.discountPercent),
                modifier = Modifier.revealOnAppear(index + 2),
            )
        }
    }
}

private val previewDeals = listOf(
    Deal("1", LocalizedText("ئازمەڕ گریل", "Azmar Grill", "أزمر غريل"), LocalizedText("خواردن", "Food & Cafes", "مطاعم"), 30, 0.8),
    Deal("2", LocalizedText("کافێ چاڤی", "Chavi Cafe", "مقهى چاڤي"), LocalizedText("خواردن", "Food & Cafes", "مطاعم"), 20, 1.2),
)

@Preview(name = "Data", widthDp = 390, heightDp = 844)
@Composable
private fun HomeDataPreview() = KhazenaTheme {
    HomeScreen(HomeState(ContentState(LoadState.Data(previewDeals))), rememberMessageToastState()) {}
}

@Preview(name = "Loading", widthDp = 390, heightDp = 844)
@Composable
private fun HomeLoadingPreview() = KhazenaTheme { HomeScreen(HomeState(), rememberMessageToastState()) {} }

@Preview(name = "Empty", widthDp = 390, heightDp = 844)
@Composable
private fun HomeEmptyPreview() = KhazenaTheme {
    HomeScreen(HomeState(ContentState(LoadState.Empty)), rememberMessageToastState()) {}
}
