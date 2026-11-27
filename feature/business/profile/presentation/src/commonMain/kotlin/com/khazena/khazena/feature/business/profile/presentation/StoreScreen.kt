package com.khazena.khazena.feature.business.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.revealOnAppear
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.core.presentation.state.LoadState
import com.khazena.khazena.core.presentation.state.MessageToastState
import com.khazena.khazena.core.presentation.state.StateScreen
import com.khazena.khazena.core.presentation.state.rememberMessageToastState
import khazena.feature.business.profile.presentation.generated.resources.Res
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaStateCard
import com.khazena.khazena.core.designsystem.component.StateTone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.component.KhazenaOfferBlock
import com.khazena.khazena.core.designsystem.component.KhazenaTag
import com.khazena.khazena.core.designsystem.component.TagType
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.presentation.formatDiscount
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.feature.business.profile.domain.Store
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import khazena.feature.business.profile.presentation.generated.resources.*

@Composable
fun StoreRoot(
    viewModel: StoreViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is StoreEvent.ShowMessage -> toast.show(event.message)
        }
    }
    StoreScreen(
        state = state,
        toastState = toast,
        onAction = { action ->
            when (action) {
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun StoreScreen(state: StoreState, toastState: MessageToastState, onAction: (StoreAction) -> Unit) {
    StateScreen(
        state = state.content,
        onRefresh = { onAction(StoreAction.OnRefresh) },
        onRetry = { onAction(StoreAction.OnRetry) },
        toastState = toastState,
        header = {
            Text(
                stringResource(Res.string.store_title),
                style = KhazenaTheme.typography.headingH1,
                color = KhazenaTheme.colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
        },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.Storefront,
                badge = stringResource(Res.string.store_empty_badge),
                title = stringResource(Res.string.store_empty_title),
                body = stringResource(Res.string.store_empty_body),
                tone = StateTone.Violet,
            )
        },
    ) { data ->
        if (data != null) {
            Column(
                Modifier.fillMaxWidth().background(KhazenaTheme.colors.surfaceCard, KhazenaRadius.card).padding(18.dp).revealOnAppear(0),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(data.name.localized(), style = KhazenaTheme.typography.headingH2, color = KhazenaTheme.colors.textPrimary)
                KhazenaTag(data.sector.localized(), type = TagType.Verified)
                InfoRow(KhazenaIcons.MapPin, data.address.localized())
                InfoRow(KhazenaIcons.Clock, data.hours.localized())
                InfoRow(KhazenaIcons.Bell, data.phone)
            }
            KhazenaOfferBlock(
                kicker = stringResource(Res.string.store_discount_kicker),
                headline = formatDiscount(data.memberDiscountPercent),
                body = stringResource(Res.string.store_discount_body),
                modifier = Modifier.revealOnAppear(1),
            )
        }
    }
}

@Composable
private fun InfoRow(icon: DrawableResource, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(painterResource(icon), null, Modifier.size(18.dp), tint = KhazenaTheme.colors.iconMuted)
        Text(text, style = KhazenaTheme.typography.bodyM, color = KhazenaTheme.colors.textSecondary)
    }
}

@Preview(name = "Data", widthDp = 390, heightDp = 844)
@Composable
private fun StoreDataPreview() = KhazenaTheme {
    StoreScreen(StoreState(ContentState(LoadState.Data(Store(LocalizedText("ئازمەڕ", "Azmar Grill", "أزمر"), LocalizedText("خواردن", "Food & Cafes", "مطاعم"), LocalizedText("سلێمانی", "Salim Street", "السليمانية"), LocalizedText("هەموو ڕۆژێک", "Every day 11:00 – 23:00", "يوميًا"), "+964 750 000 0000", 30)))), rememberMessageToastState()) {}
}

@Preview(name = "Empty", widthDp = 390, heightDp = 844)
@Composable
private fun StoreEmptyPreview() = KhazenaTheme {
    StoreScreen(StoreState(ContentState(LoadState.Empty)), rememberMessageToastState()) {}
}
