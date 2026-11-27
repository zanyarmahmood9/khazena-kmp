package com.khazena.khazena.feature.business.reports.presentation

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
import khazena.feature.business.reports.presentation.generated.resources.Res
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
import com.khazena.khazena.core.designsystem.component.KhazenaBarChart
import com.khazena.khazena.core.designsystem.component.KhazenaSectionHeader
import com.khazena.khazena.core.designsystem.component.KhazenaStatTile
import com.khazena.khazena.core.presentation.formatGrouped
import com.khazena.khazena.core.presentation.formatIqd
import com.khazena.khazena.feature.business.reports.domain.WeeklyReport
import org.jetbrains.compose.resources.stringArrayResource
import khazena.feature.business.reports.presentation.generated.resources.*

@Composable
fun ReportsRoot(
    viewModel: ReportsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ReportsEvent.ShowMessage -> toast.show(event.message)
        }
    }
    ReportsScreen(
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
fun ReportsScreen(state: ReportsState, toastState: MessageToastState, onAction: (ReportsAction) -> Unit) {
    StateScreen(
        state = state.content,
        onRefresh = { onAction(ReportsAction.OnRefresh) },
        onRetry = { onAction(ReportsAction.OnRetry) },
        toastState = toastState,
        header = {
            Text(
                stringResource(Res.string.reports_title),
                style = KhazenaTheme.typography.headingH1,
                color = KhazenaTheme.colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Text(stringResource(Res.string.reports_subtitle), style = KhazenaTheme.typography.bodyM, color = KhazenaTheme.colors.textSecondary)
        },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.ChartBar,
                badge = stringResource(Res.string.reports_empty_badge),
                title = stringResource(Res.string.reports_empty_title),
                body = stringResource(Res.string.reports_empty_body),
                tone = StateTone.Violet,
            )
        },
    ) { data ->
        Row(Modifier.revealOnAppear(0), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            KhazenaStatTile(formatGrouped(data.visitsByDay.sum().toLong()), stringResource(Res.string.reports_visits), Modifier.weight(1f), StateTone.Sun)
            KhazenaStatTile(formatGrouped(data.newMembers.toLong()), stringResource(Res.string.reports_new_members), Modifier.weight(1f), StateTone.Violet)
        }
        Row(Modifier.revealOnAppear(1), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            KhazenaStatTile(formatIqd(data.savingsGivenIqd), stringResource(Res.string.reports_savings), Modifier.weight(1f))
            KhazenaStatTile("${data.returningPercent}%", stringResource(Res.string.reports_returning), Modifier.weight(1f))
        }
        KhazenaSectionHeader(stringResource(Res.string.reports_per_day), Modifier.revealOnAppear(2))
        val days = stringArrayResource(Res.array.reports_days)
        KhazenaBarChart(
            values = data.visitsByDay,
            labels = days,
            description = days.zip(data.visitsByDay).joinToString { (day, visits) -> "$day $visits" },
            modifier = Modifier.revealOnAppear(3),
        )
    }
}

@Preview(name = "Data", widthDp = 390, heightDp = 844)
@Composable
private fun ReportsDataPreview() = KhazenaTheme {
    ReportsScreen(ReportsState(ContentState(LoadState.Data(WeeklyReport(listOf(12, 18, 9, 22, 30, 26, 14), 9, 128_000, 64)))), rememberMessageToastState()) {}
}

@Preview(name = "Empty", widthDp = 390, heightDp = 844)
@Composable
private fun ReportsEmptyPreview() = KhazenaTheme {
    ReportsScreen(ReportsState(ContentState(LoadState.Empty)), rememberMessageToastState()) {}
}
