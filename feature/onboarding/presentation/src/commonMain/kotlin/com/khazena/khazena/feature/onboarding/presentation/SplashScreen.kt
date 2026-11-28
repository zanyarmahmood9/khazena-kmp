package com.khazena.khazena.feature.onboarding.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.khazena.khazena.core.designsystem.KhazenaRtlPreview
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaLaunchMark
import com.khazena.khazena.core.designsystem.component.KhazenaWordmark
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.feature.onboarding.presentation.splash.SplashEvent
import com.khazena.khazena.feature.onboarding.presentation.splash.SplashViewModel
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import khazena.feature.onboarding.presentation.generated.resources.Res
import khazena.feature.onboarding.presentation.generated.resources.splash_footer
import khazena.feature.onboarding.presentation.generated.resources.splash_tagline
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun SplashRoot(onFinished: () -> Unit, viewModel: SplashViewModel = koinViewModel()) {
    val exit = remember { Animatable(0f) }
    var leaving by remember { mutableStateOf(false) }
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            SplashEvent.NavigateNext -> leaving = true
        }
    }
    // Logo, text and accents lead the exit; the screen starts fading into the intro as they finish,
    // so the logo never ghosts over the intro and there's no empty orange pause.
    LaunchedEffect(leaving) {
        if (!leaving) return@LaunchedEffect
        launch { exit.animateTo(1f, tween(durationMillis = 320, easing = FastOutSlowInEasing)) }
        delay(200.milliseconds)
        onFinished()
    }
    SplashScreen(exitProgress = { exit.value })
}

private val MarkHeight = 115.6.dp

@Composable
internal fun SplashScreen(exitProgress: () -> Float = { 0f }) {
    val colors = KhazenaTheme.colors
    val typography = KhazenaTheme.typography
    val latinScript = LocalLayoutDirection.current == LayoutDirection.Ltr
    val ink = colors.textPrimary
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(Res.readBytes("files/splash_logo.json").decodeToString())
    }
    val reveal = remember { Animatable(0f) }
    LaunchedEffect(Unit) { reveal.animateTo(1f, tween(durationMillis = 700, delayMillis = 450, easing = FastOutSlowInEasing)) }
    val revealModifier = Modifier.graphicsLayer {
        alpha = reveal.value
        translationY = (1f - reveal.value) * 12.dp.toPx()
    }

    Box(Modifier.fillMaxSize().background(colors.blockEmber)) {
        Box(
            Modifier.fillMaxSize().graphicsLayer {
                val progress = exitProgress()
                alpha = 1f - progress
                scaleX = 1f + 0.06f * progress
                scaleY = scaleX
            },
        ) {
            Accent(colors.blockSun, width = 34.dp, angle = 28f, x = (-112.1).dp, y = (-160).dp)
            Accent(ink, width = 26.dp, angle = -24f, x = 118.7.dp, y = (-191.2).dp)
            Accent(ink, width = 22.dp, angle = -12f, x = (-127.3).dp, y = 180.1.dp)
            Accent(colors.blockSun, width = 38.dp, angle = 20f, x = 127.3.dp, y = 200.7.dp)

            Layout(
                content = {
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(Modifier.absoluteOffset(x = 6.dp).size(width = 102.dp, height = MarkHeight), contentAlignment = Alignment.Center) {
                            if (composition == null) {
                                KhazenaLaunchMark()
                            } else {
                                // The Lottie canvas is 130×140 with the mark centred in it.
                                Image(rememberLottiePainter(composition), null, Modifier.requiredSize(130.dp, 140.dp))
                            }
                        }
                        Spacer(Modifier.height(22.4.dp))
                        // The wordmark is a logo: it keeps its size whatever the system font scale.
                        CompositionLocalProvider(LocalDensity provides Density(LocalDensity.current.density, fontScale = 1f)) {
                            KhazenaWordmark(
                                style = typography.displayXL.copy(fontSize = 52.sp, lineHeight = 1.2.em, textAlign = TextAlign.Center),
                                color = ink,
                                letterSpacing = (-0.05).em,
                                modifier = revealModifier,
                            )
                        }
                        Spacer(Modifier.height(7.6.dp))
                        Text(
                            stringResource(Res.string.splash_tagline),
                            style = typography.bodyM.copy(
                                fontWeight = FontWeight.Medium,
                                lineHeight = if (latinScript) 1.3.em else typography.bodyM.lineHeight,
                            ),
                            color = ink.copy(alpha = 0.85f),
                            textAlign = TextAlign.Center,
                            modifier = revealModifier.fillMaxWidth().padding(horizontal = KhazenaSpacing.screenMargin),
                        )
                    }
                    Text(
                        stringResource(Res.string.splash_footer).uppercase(),
                        style = if (latinScript) typography.monoTagS.copy(letterSpacing = 0.06.em, lineHeight = 1.302.em) else typography.monoTagS,
                        color = ink.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = revealModifier.fillMaxWidth().padding(start = KhazenaSpacing.screenMargin, end = KhazenaSpacing.screenMargin, bottom = 59.7.dp),
                    )
                },
                modifier = Modifier.fillMaxSize(),
            ) { measurables, constraints ->
                val loose = constraints.copy(minHeight = 0)
                val group = measurables[0].measure(loose)
                val footer = measurables[1].measure(loose)
                val height = constraints.maxHeight
                val footerY = height - footer.height
                // Starts with the mark centred (matching the native launch screens), then settles at the Figma position.
                val launchY = height / 2 - (MarkHeight / 2).roundToPx()
                val designY = (height - group.height) / 2 + 1.5.dp.roundToPx()
                val groupY = lerp(launchY, designY, reveal.value)
                    .coerceAtMost(footerY - 16.dp.roundToPx() - group.height)
                    .coerceAtLeast(0)
                layout(constraints.maxWidth, height) {
                    group.place(0, groupY)
                    footer.place(0, footerY)
                }
            }
        }
    }
}

@Composable
private fun BoxScope.Accent(color: Color, width: Dp, angle: Float, x: Dp, y: Dp) = Box(
    Modifier
        .align(Alignment.Center)
        .absoluteOffset(x, y)
        .rotate(angle)
        .size(width, 9.dp)
        .background(color, RoundedCornerShape(5.dp)),
)

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun SplashPreview() = KhazenaTheme { SplashScreen() }

@Preview(name = "RTL", widthDp = 390, heightDp = 844)
@Composable
private fun SplashRtlPreview() = KhazenaRtlPreview { SplashScreen() }
