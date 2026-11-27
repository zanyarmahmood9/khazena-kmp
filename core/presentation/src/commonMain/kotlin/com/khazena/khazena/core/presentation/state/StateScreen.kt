package com.khazena.khazena.core.presentation.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaSkeletonList
import khazena.core.presentation.generated.resources.Res
import khazena.core.presentation.generated.resources.loading
import org.jetbrains.compose.resources.stringResource

val TabBarClearance: Dp = 120.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> StateScreen(
    state: ContentState<T>,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    toastState: MessageToastState,
    empty: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    bottomClearance: Dp = TabBarClearance,
    header: @Composable ColumnScope.() -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {},
    loading: @Composable () -> Unit = { KhazenaSkeletonList(stringResource(Res.string.loading)) },
    content: @Composable ColumnScope.(T) -> Unit,
) {
    Box(modifier.fillMaxSize().background(KhazenaTheme.colors.surfaceBackground)) {
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize().statusBarsPadding(),
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = KhazenaSpacing.screenMargin)
                    .padding(top = 8.dp, bottom = bottomClearance),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                header()
                LoadStateContent(state.content, onRetry, empty, Modifier.fillMaxWidth(), loading) { data ->
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { content(data) }
                }
                footer()
            }
        }
        MessageToastHost(toastState, Modifier.align(Alignment.BottomCenter).padding(bottom = bottomClearance - 24.dp))
    }
}
