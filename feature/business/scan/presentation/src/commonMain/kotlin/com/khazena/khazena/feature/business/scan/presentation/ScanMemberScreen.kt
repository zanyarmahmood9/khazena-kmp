package com.khazena.khazena.feature.business.scan.presentation

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
import khazena.feature.business.scan.presentation.generated.resources.Res
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.component.IconButtonStyle
import com.khazena.khazena.core.designsystem.component.KhazenaIconButton
import khazena.core.presentation.generated.resources.Res as CoreRes
import khazena.core.presentation.generated.resources.back

import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaStateCard
import com.khazena.khazena.core.designsystem.component.StateTone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.ui.text.style.TextAlign
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.component.KhazenaPersonRow
import com.khazena.khazena.core.designsystem.component.KhazenaSectionHeader
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.presentation.formatIqd
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.feature.business.scan.domain.RecentScan
import org.jetbrains.compose.resources.painterResource
import khazena.feature.business.scan.presentation.generated.resources.*

@Composable
fun ScanMemberRoot(
    onBack: () -> Unit,
    viewModel: ScanMemberViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ScanMemberEvent.ShowMessage -> toast.show(event.message)
        }
    }
    ScanMemberScreen(
        state = state,
        toastState = toast,
        onAction = { action ->
            when (action) {
                ScanMemberAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun ScanMemberScreen(state: ScanMemberState, toastState: MessageToastState, onAction: (ScanMemberAction) -> Unit) {
    StateScreen(
        state = state.content,
        onRefresh = { onAction(ScanMemberAction.OnRefresh) },
        onRetry = { onAction(ScanMemberAction.OnRetry) },
        toastState = toastState,
        bottomClearance = 48.dp,
        header = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KhazenaIconButton(KhazenaIcons.ArrowLeft, stringResource(CoreRes.string.back), { onAction(ScanMemberAction.OnBackClick) }, style = IconButtonStyle.White)
                Text(
                    stringResource(Res.string.scan_member_title),
                    style = KhazenaTheme.typography.headingH2,
                    color = KhazenaTheme.colors.textPrimary,
                    modifier = Modifier.semantics { heading() },
                )
            }

            Box(
                Modifier.fillMaxWidth().height(220.dp).background(KhazenaTheme.colors.blockInk, KhazenaRadius.hero),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(painterResource(KhazenaIcons.Scan), null, Modifier.size(56.dp), tint = KhazenaTheme.colors.blockSun)
                    Text(
                        stringResource(Res.string.scan_member_camera_hint),
                        style = KhazenaTheme.typography.bodyS,
                        color = KhazenaTheme.colors.textOnDark,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp),
                    )
                }
            }
            KhazenaSectionHeader(stringResource(Res.string.scan_member_recent))
        },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.Scan,
                badge = stringResource(Res.string.scan_member_empty_badge),
                title = stringResource(Res.string.scan_member_empty_title),
                body = stringResource(Res.string.scan_member_empty_body),
                tone = StateTone.Sun,
            )
        },
    ) { data ->
        data.forEachIndexed { index, scan ->
            KhazenaPersonRow(
                initials = scan.memberName.split(" ").mapNotNull { it.firstOrNull() }.take(2).joinToString(""),
                title = scan.memberName,
                meta = scan.whenLabel.localized(),
                trailing = formatIqd(scan.savedIqd),
                modifier = Modifier.revealOnAppear(index),
            )
        }
    }
}

@Preview(name = "Data", widthDp = 390, heightDp = 844)
@Composable
private fun ScanMemberDataPreview() = KhazenaTheme {
    ScanMemberScreen(ScanMemberState(ContentState(LoadState.Data(listOf(RecentScan("1", "Lana Omar", LocalizedText("ئێستا", "2 min ago", "الآن"), 4_000))))), rememberMessageToastState()) {}
}

@Preview(name = "Empty", widthDp = 390, heightDp = 844)
@Composable
private fun ScanMemberEmptyPreview() = KhazenaTheme {
    ScanMemberScreen(ScanMemberState(ContentState(LoadState.Empty)), rememberMessageToastState()) {}
}
