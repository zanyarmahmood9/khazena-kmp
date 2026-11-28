package com.khazena.khazena.feature.business.home.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.AccountMode
import com.khazena.khazena.core.designsystem.component.KhazenaHomeHeader
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaPersonRow
import com.khazena.khazena.core.designsystem.component.KhazenaSectionHeader
import com.khazena.khazena.core.designsystem.component.KhazenaStatTile
import com.khazena.khazena.core.designsystem.component.KhazenaStateCard
import com.khazena.khazena.core.designsystem.component.StateTone
import com.khazena.khazena.core.designsystem.revealOnAppear
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.core.presentation.formatGrouped
import com.khazena.khazena.core.presentation.formatIqd
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.core.presentation.permission.AskLocationOnce
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.core.presentation.state.LoadState
import com.khazena.khazena.core.presentation.state.MessageToastState
import com.khazena.khazena.core.presentation.state.StateScreen
import com.khazena.khazena.core.presentation.state.rememberMessageToastState
import com.khazena.khazena.feature.business.home.domain.BusinessDashboard
import com.khazena.khazena.feature.business.home.domain.ScanRecord
import khazena.core.presentation.generated.resources.notifications
import khazena.core.presentation.generated.resources.switch_to_personal
import khazena.feature.business.home.presentation.generated.resources.Res
import khazena.feature.business.home.presentation.generated.resources.business_home_empty_action
import khazena.feature.business.home.presentation.generated.resources.business_home_empty_badge
import khazena.feature.business.home.presentation.generated.resources.business_home_empty_body
import khazena.feature.business.home.presentation.generated.resources.business_home_empty_title
import khazena.feature.business.home.presentation.generated.resources.business_home_greeting
import khazena.feature.business.home.presentation.generated.resources.business_home_new_members
import khazena.feature.business.home.presentation.generated.resources.business_home_recent
import khazena.feature.business.home.presentation.generated.resources.business_home_savings_given
import khazena.feature.business.home.presentation.generated.resources.business_home_scans_today
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import khazena.core.presentation.generated.resources.Res as CoreRes

@Composable
fun BusinessHomeRoot(
    onSwitchAccount: () -> Unit,
    onScanMember: () -> Unit,
    viewModel: BusinessHomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is BusinessHomeEvent.ShowMessage -> toast.show(event.message)
        }
    }
    AskLocationOnce()
    BusinessHomeScreen(
        state = state,
        toastState = toast,
        onAction = { action ->
            when (action) {
                BusinessHomeAction.OnSwitchAccountClick -> onSwitchAccount()
                BusinessHomeAction.OnScanMemberClick -> onScanMember()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun BusinessHomeScreen(state: BusinessHomeState, toastState: MessageToastState, onAction: (BusinessHomeAction) -> Unit) {
    StateScreen(
        state = state.content,
        onRefresh = { onAction(BusinessHomeAction.OnRefresh) },
        onRetry = { onAction(BusinessHomeAction.OnRetry) },
        toastState = toastState,
        header = {
            KhazenaHomeHeader(
                mode = AccountMode.Business,
                greeting = stringResource(Res.string.business_home_greeting),
                switchLabel = stringResource(CoreRes.string.switch_to_personal),
                notificationsLabel = stringResource(CoreRes.string.notifications),
                onSwitchAccount = { onAction(BusinessHomeAction.OnSwitchAccountClick) },
                onNotificationsClick = {},
            )
        },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.Scan,
                badge = stringResource(Res.string.business_home_empty_badge),
                title = stringResource(Res.string.business_home_empty_title),
                body = stringResource(Res.string.business_home_empty_body),
                tone = StateTone.Sun,
                actionLabel = stringResource(Res.string.business_home_empty_action),
                onAction = { onAction(BusinessHomeAction.OnScanMemberClick) },
            )
        },
    ) { data ->
        Row(Modifier.revealOnAppear(0), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            KhazenaStatTile(formatGrouped(data.scansToday.toLong()), stringResource(Res.string.business_home_scans_today), Modifier.weight(1f), StateTone.Sun)
            KhazenaStatTile(formatGrouped(data.newMembersThisWeek.toLong()), stringResource(Res.string.business_home_new_members), Modifier.weight(1f), StateTone.Violet)
        }
        KhazenaStatTile(formatIqd(data.savingsGivenIqd), stringResource(Res.string.business_home_savings_given), Modifier.fillMaxWidth().revealOnAppear(1))
        KhazenaSectionHeader(stringResource(Res.string.business_home_recent), Modifier.revealOnAppear(2))
        data.recentScans.forEachIndexed { index, scan ->
            KhazenaPersonRow(
                initials = scan.memberName.split(" ").mapNotNull { it.firstOrNull() }.take(2).joinToString(""),
                title = scan.memberName,
                meta = scan.whenLabel.localized(),
                trailing = formatIqd(scan.savedIqd),
                modifier = Modifier.revealOnAppear(index + 3),
            )
        }
    }
}

@Preview(name = "Data", widthDp = 390, heightDp = 844)
@Composable
private fun BusinessHomeDataPreview() = KhazenaTheme {
    BusinessHomeScreen(BusinessHomeState(ContentState(LoadState.Data(BusinessDashboard(42, 9, 128_000, listOf(ScanRecord("1", "Rozh Ahmed", LocalizedText("ئێستا", "4 min ago", "الآن"), 4_500)))))), rememberMessageToastState()) {}
}

@Preview(name = "Empty", widthDp = 390, heightDp = 844)
@Composable
private fun BusinessHomeEmptyPreview() = KhazenaTheme {
    BusinessHomeScreen(BusinessHomeState(ContentState(LoadState.Empty)), rememberMessageToastState()) {}
}
