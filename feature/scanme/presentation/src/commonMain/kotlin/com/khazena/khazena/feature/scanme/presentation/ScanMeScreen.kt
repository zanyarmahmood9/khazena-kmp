package com.khazena.khazena.feature.scanme.presentation

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
import khazena.feature.scanme.presentation.generated.resources.Res
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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.khazena.khazena.core.designsystem.KhazenaMotion
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.component.KhazenaTag
import com.khazena.khazena.core.designsystem.component.TagType
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.feature.scanme.domain.MemberCode
import kotlinx.coroutines.delay
import khazena.feature.scanme.presentation.generated.resources.*

@Composable
fun ScanMeRoot(
    onBack: () -> Unit,
    viewModel: ScanMeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ScanMeEvent.ShowMessage -> toast.show(event.message)
        }
    }
    ScanMeScreen(
        state = state,
        toastState = toast,
        onAction = { action ->
            when (action) {
                ScanMeAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun ScanMeScreen(state: ScanMeState, toastState: MessageToastState, onAction: (ScanMeAction) -> Unit) {
    StateScreen(
        state = state.content,
        onRefresh = { onAction(ScanMeAction.OnRefresh) },
        onRetry = { onAction(ScanMeAction.OnRetry) },
        toastState = toastState,
        bottomClearance = 48.dp,
        header = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KhazenaIconButton(KhazenaIcons.ArrowLeft, stringResource(CoreRes.string.back), { onAction(ScanMeAction.OnBackClick) }, style = IconButtonStyle.White)
                Text(
                    stringResource(Res.string.scanme_title),
                    style = KhazenaTheme.typography.headingH2,
                    color = KhazenaTheme.colors.textPrimary,
                    modifier = Modifier.semantics { heading() },
                )
            }
        },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.QrCode,
                badge = stringResource(Res.string.scanme_empty_badge),
                title = stringResource(Res.string.scanme_empty_title),
                body = stringResource(Res.string.scanme_empty_body),
                tone = StateTone.Violet,
            )
        },
    ) { data ->
        if (data != null) LiveCode(data, Modifier.revealOnAppear(0)) { onAction(ScanMeAction.OnCodeExpired) }
        Text(
            stringResource(Res.string.scanme_hint),
            style = KhazenaTheme.typography.bodyM,
            color = KhazenaTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().revealOnAppear(1),
        )
    }
}

@Composable
private fun LiveCode(code: MemberCode, modifier: Modifier, onExpired: () -> Unit) {
    val colors = KhazenaTheme.colors
    var remaining by remember(code.code) { mutableIntStateOf(code.validForSeconds) }
    LaunchedEffect(code.code) {
        while (remaining > 0) {
            delay(1_000)
            remaining--
        }
        onExpired()
    }
    Column(
        modifier.fillMaxWidth().background(colors.blockViolet, KhazenaRadius.hero).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        KhazenaTag(stringResource(Res.string.scanme_live), type = TagType.OnColourBlock)
        AnimatedContent(
            targetState = code.code,
            transitionSpec = {
                (fadeIn(tween(KhazenaMotion.MEDIUM)) + slideInVertically(tween(KhazenaMotion.LONG, easing = KhazenaMotion.EmphasizedDecelerate)) { it / 2 }) togetherWith
                    (fadeOut(tween(KhazenaMotion.SHORT)) + slideOutVertically(tween(KhazenaMotion.SHORT)) { -it / 2 })
            },
            label = "code",
        ) { value ->
            Text(value, style = KhazenaTheme.typography.monoCode.copy(fontSize = 30.sp), color = colors.textOnViolet)
        }
        Text(code.memberName, style = KhazenaTheme.typography.labelXLBold, color = colors.textOnViolet)
        Text(code.planName.localized(), style = KhazenaTheme.typography.bodyS, color = colors.textOnViolet)
        Text(
            stringResource(Res.string.scanme_refreshes_in, "${remaining / 60}:${(remaining % 60).toString().padStart(2, '0')}"),
            style = KhazenaTheme.typography.monoTag,
            color = colors.textOnViolet,
        )
    }
}

@Preview(name = "Data", widthDp = 390, heightDp = 844)
@Composable
private fun ScanMeDataPreview() = KhazenaTheme {
    ScanMeScreen(ScanMeState(ContentState(LoadState.Data(MemberCode("4821 · 0937 · 7712", "Bandy Kamal", LocalizedText("ساڵانە", "1-Year Premium", "سنوي"), 60)))), rememberMessageToastState()) {}
}

@Preview(name = "Empty", widthDp = 390, heightDp = 844)
@Composable
private fun ScanMeEmptyPreview() = KhazenaTheme {
    ScanMeScreen(ScanMeState(ContentState(LoadState.Empty)), rememberMessageToastState()) {}
}
