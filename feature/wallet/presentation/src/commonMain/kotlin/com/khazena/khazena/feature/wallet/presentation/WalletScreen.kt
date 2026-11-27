package com.khazena.khazena.feature.wallet.presentation

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
import khazena.feature.wallet.presentation.generated.resources.Res
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaStateCard
import com.khazena.khazena.core.designsystem.component.StateTone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.component.KhazenaMembershipCard
import com.khazena.khazena.core.designsystem.component.KhazenaPlaceRow
import com.khazena.khazena.core.designsystem.component.KhazenaSectionHeader
import com.khazena.khazena.core.designsystem.component.KhazenaStatTile
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.presentation.formatGrouped
import com.khazena.khazena.core.presentation.formatIqd
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.core.presentation.placeholderPhoto
import com.khazena.khazena.feature.wallet.domain.Saving
import com.khazena.khazena.feature.wallet.domain.Wallet
import khazena.feature.wallet.presentation.generated.resources.*

@Composable
fun WalletRoot(
    viewModel: WalletViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is WalletEvent.ShowMessage -> toast.show(event.message)
        }
    }
    WalletScreen(
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
fun WalletScreen(state: WalletState, toastState: MessageToastState, onAction: (WalletAction) -> Unit) {
    StateScreen(
        state = state.content,
        onRefresh = { onAction(WalletAction.OnRefresh) },
        onRetry = { onAction(WalletAction.OnRetry) },
        toastState = toastState,
        header = {
            Text(
                stringResource(Res.string.wallet_title),
                style = KhazenaTheme.typography.headingH1,
                color = KhazenaTheme.colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
        },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.Wallet,
                badge = stringResource(Res.string.wallet_empty_badge),
                title = stringResource(Res.string.wallet_empty_title),
                body = stringResource(Res.string.wallet_empty_body),
                tone = StateTone.Sun,
            )
        },
    ) { data ->
        KhazenaMembershipCard(
            active = true,
            planBadge = stringResource(Res.string.wallet_badge),
            statusLabel = stringResource(Res.string.wallet_active),
            memberLabel = stringResource(Res.string.wallet_member),
            memberName = data.memberName,
            planLabel = stringResource(Res.string.wallet_plan),
            planName = data.planName.localized(),
            dateLabel = stringResource(Res.string.wallet_valid_until),
            date = "\u2066${data.validUntil}\u2069",
            modifier = Modifier.revealOnAppear(0),
        )
        Row(Modifier.revealOnAppear(1), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            KhazenaStatTile(formatIqd(data.totalSavedIqd), stringResource(Res.string.wallet_total_saved), Modifier.weight(1f), StateTone.Sun)
            KhazenaStatTile(formatGrouped(data.visits.toLong()), stringResource(Res.string.wallet_visits), Modifier.weight(1f))
        }
        KhazenaSectionHeader(stringResource(Res.string.wallet_recent), Modifier.revealOnAppear(2))
        data.history.forEachIndexed { index, saving ->
            KhazenaPlaceRow(
                photo = placeholderPhoto(index),
                title = saving.merchant.localized(),
                meta = saving.whenLabel.localized(),
                discountLabel = formatIqd(saving.savedIqd),
                modifier = Modifier.revealOnAppear(index + 3),
            )
        }
    }
}

@Preview(name = "Data", widthDp = 390, heightDp = 844)
@Composable
private fun WalletDataPreview() = KhazenaTheme {
    WalletScreen(WalletState(ContentState(LoadState.Data(Wallet("Bandy Kamal", LocalizedText("ساڵانە", "1-Year Premium", "سنوي"), "12 Oct 2027", 184_500, 23, listOf(Saving("1", LocalizedText("ئازمەڕ", "Azmar Grill", "أزمر"), LocalizedText("ئێستا", "5 min ago", "الآن"), 7_500)))))), rememberMessageToastState()) {}
}

@Preview(name = "Empty", widthDp = 390, heightDp = 844)
@Composable
private fun WalletEmptyPreview() = KhazenaTheme {
    WalletScreen(WalletState(ContentState(LoadState.Empty)), rememberMessageToastState()) {}
}
