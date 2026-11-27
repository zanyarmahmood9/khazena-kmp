package com.khazena.khazena.feature.onboarding.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaLogo
import com.khazena.khazena.core.designsystem.component.KhazenaWordmark
import khazena.feature.onboarding.presentation.generated.resources.Res
import khazena.feature.onboarding.presentation.generated.resources.splash_footer
import khazena.feature.onboarding.presentation.generated.resources.splash_tagline
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.feature.onboarding.presentation.splash.SplashEvent
import com.khazena.khazena.feature.onboarding.presentation.splash.SplashViewModel

@Composable
internal fun SplashRoot(onFinished: () -> Unit, viewModel: SplashViewModel = koinViewModel()) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            SplashEvent.NavigateNext -> onFinished()
        }
    }
    SplashContent()
}

@Composable
private fun SplashContent() {
    val colors = KhazenaTheme.colors
    Box(Modifier.fillMaxSize().background(colors.blockEmber).navigationBarsPadding()) {
        Column(
            Modifier.align(Alignment.Center).padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Image(painterResource(KhazenaLogo.MarkOnPaper), null, Modifier.size(width = 66.dp, height = 75.dp))
            KhazenaWordmark(KhazenaTheme.typography.displayL, colors.blockInk)
            Text(
                stringResource(Res.string.splash_tagline),
                style = KhazenaTheme.typography.bodyM,
                color = colors.blockInk,
                textAlign = TextAlign.Center,
            )
        }
        Text(
            stringResource(Res.string.splash_footer).uppercase(),
            style = KhazenaTheme.typography.monoTagS,
            color = colors.blockInk,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp),
        )
    }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun SplashPreview() = KhazenaTheme { SplashContent() }
