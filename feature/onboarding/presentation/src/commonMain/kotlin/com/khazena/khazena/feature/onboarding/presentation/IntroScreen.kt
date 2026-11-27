package com.khazena.khazena.feature.onboarding.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroAction
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroEvent
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroState
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaColors
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import khazena.feature.onboarding.presentation.generated.resources.Res
import khazena.feature.onboarding.presentation.generated.resources.intro_1_body
import khazena.feature.onboarding.presentation.generated.resources.intro_1_kicker
import khazena.feature.onboarding.presentation.generated.resources.intro_1_title
import khazena.feature.onboarding.presentation.generated.resources.intro_2_body
import khazena.feature.onboarding.presentation.generated.resources.intro_2_kicker
import khazena.feature.onboarding.presentation.generated.resources.intro_2_title
import khazena.feature.onboarding.presentation.generated.resources.intro_3_body
import khazena.feature.onboarding.presentation.generated.resources.intro_3_kicker
import khazena.feature.onboarding.presentation.generated.resources.intro_3_title
import khazena.feature.onboarding.presentation.generated.resources.next
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private class IntroPage(
    val kicker: StringResource,
    val title: StringResource,
    val body: StringResource,
    val icon: DrawableResource,
    val block: (KhazenaColors) -> Color,
)

private val pages = listOf(
    IntroPage(Res.string.intro_1_kicker, Res.string.intro_1_title, Res.string.intro_1_body, KhazenaIcons.Storefront) { it.blockSun },
    IntroPage(Res.string.intro_2_kicker, Res.string.intro_2_title, Res.string.intro_2_body, KhazenaIcons.QrCode) { it.blockViolet },
    IntroPage(Res.string.intro_3_kicker, Res.string.intro_3_title, Res.string.intro_3_body, KhazenaIcons.MapPin) { it.blockEmber },
)

@Composable
internal fun IntroRoot(onDone: () -> Unit, viewModel: IntroViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            IntroEvent.NavigateToChooseSide -> onDone()
        }
    }
    IntroScreen(state, viewModel::onAction)
}

@Composable
internal fun IntroScreen(state: IntroState, onAction: (IntroAction) -> Unit) {
    val pagerState = rememberPagerState(initialPage = state.page) { state.pageCount }
    LaunchedEffect(state.page) {
        if (pagerState.currentPage != state.page) pagerState.animateScrollToPage(state.page)
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { onAction(IntroAction.OnPageSettled(it)) }
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(KhazenaTheme.colors.surfaceBackground)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        OnboardingTopBar(step = pagerState.currentPage, onSkip = { onAction(IntroAction.OnSkipClick) })
        HorizontalPager(pagerState, Modifier.weight(1f)) { index -> IntroPageContent(pages[index]) }
        KhazenaButton(
            text = stringResource(Res.string.next),
            onClick = { onAction(IntroAction.OnNextClick) },
            modifier = Modifier.fillMaxWidth().padding(KhazenaSpacing.screenMargin),
            icon = KhazenaIcons.ArrowRight,
        )
    }
}

@Composable
private fun IntroPageContent(page: IntroPage) {
    val colors = KhazenaTheme.colors
    Column(Modifier.fillMaxSize().padding(horizontal = KhazenaSpacing.screenMargin, vertical = 8.dp)) {
        Box(
            Modifier.fillMaxWidth().aspectRatio(0.84f, matchHeightConstraintsFirst = false)
                .weight(1f, fill = false)
                .background(page.block(colors), KhazenaRadius.hero),
            contentAlignment = Alignment.Center,
        ) {
            Box(Modifier.size(112.dp).background(colors.surfaceCard, CircleShape), contentAlignment = Alignment.Center) {
                Icon(painterResource(page.icon), null, Modifier.size(48.dp), tint = colors.textPrimary)
            }
        }
        Spacer(Modifier.height(20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(stringResource(page.kicker).uppercase(), style = KhazenaTheme.typography.monoKicker, color = colors.textSecondary)
            Text(
                stringResource(page.title),
                style = KhazenaTheme.typography.headingH1,
                color = colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Text(stringResource(page.body), style = KhazenaTheme.typography.bodyM, color = colors.textSecondary)
        }
    }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun IntroPreview() = KhazenaTheme { IntroScreen(IntroState()) {} }
