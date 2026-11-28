package com.khazena.khazena.feature.onboarding.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sin
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaColors
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaLogo
import com.khazena.khazena.core.designsystem.component.KhazenaTag
import com.khazena.khazena.core.designsystem.component.TagType
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.core.presentation.formatDiscount
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroAction
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroEvent
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroState
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroViewModel
import khazena.feature.onboarding.presentation.generated.resources.Res
import khazena.feature.onboarding.presentation.generated.resources.allow_location
import khazena.feature.onboarding.presentation.generated.resources.intro_1_body
import khazena.feature.onboarding.presentation.generated.resources.intro_1_card_beauty
import khazena.feature.onboarding.presentation.generated.resources.intro_1_card_food
import khazena.feature.onboarding.presentation.generated.resources.intro_1_card_markets
import khazena.feature.onboarding.presentation.generated.resources.intro_1_card_places
import khazena.feature.onboarding.presentation.generated.resources.intro_1_kicker
import khazena.feature.onboarding.presentation.generated.resources.intro_1_stat_categories
import khazena.feature.onboarding.presentation.generated.resources.intro_1_stat_places
import khazena.feature.onboarding.presentation.generated.resources.intro_1_title
import khazena.feature.onboarding.presentation.generated.resources.intro_2_applied
import khazena.feature.onboarding.presentation.generated.resources.intro_2_body
import khazena.feature.onboarding.presentation.generated.resources.intro_2_kicker
import khazena.feature.onboarding.presentation.generated.resources.intro_2_live_code
import khazena.feature.onboarding.presentation.generated.resources.intro_2_stat_counter
import khazena.feature.onboarding.presentation.generated.resources.intro_2_title
import khazena.feature.onboarding.presentation.generated.resources.intro_3_body
import khazena.feature.onboarding.presentation.generated.resources.intro_3_distance_m
import khazena.feature.onboarding.presentation.generated.resources.intro_3_kicker
import khazena.feature.onboarding.presentation.generated.resources.intro_3_stat_location
import khazena.feature.onboarding.presentation.generated.resources.intro_3_stat_near
import khazena.feature.onboarding.presentation.generated.resources.intro_3_title
import khazena.feature.onboarding.presentation.generated.resources.intro_beauty
import khazena.feature.onboarding.presentation.generated.resources.intro_food
import khazena.feature.onboarding.presentation.generated.resources.intro_market
import khazena.feature.onboarding.presentation.generated.resources.intro_pin
import khazena.feature.onboarding.presentation.generated.resources.next
import khazena.feature.onboarding.presentation.generated.resources.not_now
import khazena.feature.onboarding.presentation.generated.resources.skip
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private class IntroPage(
    val kicker: StringResource,
    val title: StringResource,
    val body: StringResource,
    val icon: DrawableResource,
    val block: (KhazenaColors) -> Color,
    val hero: (@Composable () -> Unit)? = null,
)

private val pages = listOf(
    IntroPage(Res.string.intro_1_kicker, Res.string.intro_1_title, Res.string.intro_1_body, KhazenaIcons.Storefront, { it.blockSun }) { SaveLocalHero() },
    IntroPage(Res.string.intro_2_kicker, Res.string.intro_2_title, Res.string.intro_2_body, KhazenaIcons.QrCode, { it.blockViolet }) { ScanSaveHero() },
    IntroPage(Res.string.intro_3_kicker, Res.string.intro_3_title, Res.string.intro_3_body, KhazenaIcons.MapPin, { it.blockEmber }) { NearYouHero() },
)

@Composable
internal fun IntroRoot(onFinished: () -> Unit, viewModel: IntroViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            IntroEvent.Finished -> onFinished()
        }
    }
    IntroScreen(state, viewModel::onAction)
}

@Composable
internal fun IntroScreen(state: IntroState, onAction: (IntroAction) -> Unit) {
    val pagerState = rememberPagerState(initialPage = state.page) { state.pageCount }
    LaunchedEffect(state.page) {
        if (pagerState.currentPage != state.page) {
            pagerState.animateScrollToPage(state.page, animationSpec = tween(durationMillis = 550, easing = FastOutSlowInEasing))
        }
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
        val locationPage = pagerState.currentPage == state.pageCount - 1
        OnboardingTopBar(
            step = pagerState.currentPage,
            onSkip = { onAction(IntroAction.OnSkipClick) },
            skipLabel = if (locationPage) Res.string.not_now else Res.string.skip,
        )
        HorizontalPager(
            pagerState,
            Modifier.weight(1f),
            flingBehavior = PagerDefaults.flingBehavior(pagerState, snapAnimationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing)),
        ) { index ->
            IntroPageContent(pages[index]) { (pagerState.currentPage - index + pagerState.currentPageOffsetFraction).absoluteValue.coerceIn(0f, 1f) }
        }
        KhazenaButton(
            // shortcut: "Allow location" only advances; wire a real permission request once a permissions lib is added.
            text = stringResource(if (locationPage) Res.string.allow_location else Res.string.next),
            onClick = { onAction(IntroAction.OnNextClick) },
            modifier = Modifier.fillMaxWidth().padding(KhazenaSpacing.screenMargin),
            icon = if (locationPage) KhazenaIcons.Crosshair else KhazenaIcons.ArrowRight,
        )
    }
}

@Composable
private fun IntroPageContent(page: IntroPage, pageOffset: () -> Float) {
    val colors = KhazenaTheme.colors
    Column(Modifier.fillMaxSize().padding(horizontal = KhazenaSpacing.screenMargin, vertical = 8.dp)) {
        Box(
            Modifier.fillMaxWidth().aspectRatio(0.84f, matchHeightConstraintsFirst = false)
                .weight(1f, fill = false)
                .graphicsLayer {
                    val offset = pageOffset()
                    scaleX = lerp(1f, 0.92f, offset)
                    scaleY = scaleX
                    alpha = lerp(1f, 0.5f, offset)
                }
                .background(page.block(colors), KhazenaRadius.hero),
            contentAlignment = Alignment.Center,
        ) {
            if (page.hero != null) {
                page.hero()
            } else {
                Box(Modifier.size(112.dp).background(colors.surfaceCard, CircleShape), contentAlignment = Alignment.Center) {
                    Icon(painterResource(page.icon), null, Modifier.size(48.dp), tint = colors.textPrimary)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        Column(
            Modifier.graphicsLayer {
                val offset = pageOffset()
                alpha = 1f - offset
                translationY = offset * 16.dp.toPx()
            },
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
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

@Composable
private fun rememberDrift(): State<Float> = rememberInfiniteTransition().animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(tween(durationMillis = 6_000, easing = LinearEasing), RepeatMode.Restart),
)

private fun Modifier.drift(time: State<Float>, phase: Float, amplitude: Dp = 3.dp) = graphicsLayer {
    translationY = sin((time.value + phase) * 2 * PI.toFloat()) * amplitude.toPx()
}

private fun Modifier.breathe(time: State<Float>, phase: Float) = graphicsLayer {
    val scale = 1f + 0.05f * sin((time.value + phase) * 2 * PI.toFloat())
    scaleX = scale
    scaleY = scale
}

@Composable
private fun SaveLocalHero() {
    val colors = KhazenaTheme.colors
    val kicker = KhazenaTheme.typography.monoKicker
    val time = rememberDrift()
    Column(Modifier.fillMaxSize().padding(20.dp).clearAndSetSemantics {}) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(Res.string.intro_1_stat_places).uppercase(), style = kicker, color = colors.textPrimary.copy(alpha = 0.75f))
            Text(stringResource(Res.string.intro_1_stat_categories).uppercase(), style = kicker, color = colors.textPrimary.copy(alpha = 0.75f))
        }
        Spacer(Modifier.height(30.dp))
        Column(Modifier.padding(horizontal = 10.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            HeroCard(Res.drawable.intro_food, Res.string.intro_1_card_food, 128, 50, rotation = 3f, shift = 2.dp, Modifier.drift(time, 0f))
            HeroCard(Res.drawable.intro_market, Res.string.intro_1_card_markets, 64, 25, rotation = -3f, shift = 6.dp, Modifier.drift(time, 0.33f))
            HeroCard(Res.drawable.intro_beauty, Res.string.intro_1_card_beauty, 87, 40, rotation = 2f, shift = (-1).dp, Modifier.drift(time, 0.66f))
        }
        Spacer(Modifier.weight(1f).heightIn(min = 12.dp))
        Text(
            formatDiscount(50),
            style = KhazenaTheme.typography.displayXL.copy(fontSize = 56.sp),
            color = colors.textPrimary,
        )
    }
}

@Composable
private fun HeroCard(photo: DrawableResource, title: StringResource, places: Int, discount: Int, rotation: Float, shift: Dp, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    Row(
        modifier
            .fillMaxWidth()
            .offset(x = shift)
            .rotate(rotation)
            .background(colors.surfaceCard, KhazenaRadius.card)
            .padding(start = 9.dp, top = 9.dp, bottom = 9.dp, end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(painterResource(photo), null, Modifier.size(60.dp).clip(KhazenaRadius.sm), contentScale = ContentScale.Crop)
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                stringResource(title),
                style = KhazenaTheme.typography.labelMSemiBold.copy(fontWeight = FontWeight.Bold),
                color = colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(stringResource(Res.string.intro_1_card_places, places), style = KhazenaTheme.typography.labelXSMedium, color = colors.textSecondary)
        }
        KhazenaTag(formatDiscount(discount))
    }
}

private val qrModules = listOf(
    "........###.#......",
    "........##..#......",
    ".......#.#..#......",
    "......#..##.#......",
    "......#..##.#......",
    "......#.##.##......",
    "#.#..#.#..######...",
    "##.#.##.....##.##..",
    "......#.....####..#",
    ".##.##......#...#..",
    "###.........#.####.",
    "..#.##......####.##",
    "#.###.##.#.#...###.",
    "......#####.##...##",
    "...........#..#....",
    "..........##...#...",
    "......#..##...#.##.",
    "......##..###.#.#..",
    "......#.###..##.##.",
)

@Composable
private fun ScanSaveHero() {
    val colors = KhazenaTheme.colors
    val kicker = KhazenaTheme.typography.monoKicker
    val time = rememberDrift()
    Box(Modifier.fillMaxSize().clip(KhazenaRadius.hero).clearAndSetSemantics {}) {
        Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(Res.string.intro_2_stat_counter).uppercase(), style = kicker, color = Color.White.copy(alpha = 0.8f))
            Text(stringResource(Res.string.intro_2_live_code).uppercase(), style = kicker, color = Color.White.copy(alpha = 0.8f))
        }
        // Art keeps Figma's 350×424 frame coordinates and centres in whatever space the block gets.
        Box(Modifier.align(Alignment.Center).requiredSize(350.dp, 424.dp)) {
            Tilted(26.75.dp, 120.dp, 25.18.dp, 17.07.dp, 24f, Modifier.drift(time, 0.2f)) { Box(Modifier.size(24.dp, 8.dp).background(colors.blockSun, KhazenaRadius.pill)) }
            Tilted(300.dp, 86.dp, 21.32.dp, 16.93.dp, -30f, Modifier.drift(time, 0.5f)) { Box(Modifier.size(20.dp, 8.dp).background(colors.blockEmber, KhazenaRadius.pill)) }
            Tilted(24.dp, 247.22.dp, 17.15.dp, 10.66.dp, -10f, Modifier.drift(time, 0.8f)) { Box(Modifier.size(16.dp, 8.dp).background(Color.White, KhazenaRadius.pill)) }
            Tilted(53.31.dp, 70.dp, 224.19.dp, 281.72.dp, 4f, Modifier.drift(time, 0f, 4.dp)) { CodeCard() }
            Box(
                Modifier.offset(246.dp, 300.dp).breathe(time, 0.4f).size(76.dp)
                    .background(colors.blockViolet, CircleShape).padding(6.dp)
                    .background(colors.blockSun, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(painterResource(KhazenaIcons.Check), null, Modifier.size(36.dp), tint = colors.textPrimary)
            }
            Tilted(36.dp, 347.63.dp, 121.8.dp, 38.3.dp, -4f, Modifier.drift(time, 0.7f)) {
                Box(Modifier.height(30.dp).background(colors.blockSun, KhazenaRadius.pill).padding(horizontal = 14.dp), contentAlignment = Alignment.Center) {
                    Text(
                        stringResource(Res.string.intro_2_applied, formatDiscount(30)).uppercase(),
                        style = KhazenaTheme.typography.monoTag,
                        color = colors.textPrimary,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Composable
private fun Tilted(x: Dp, y: Dp, width: Dp, height: Dp, rotation: Float, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(Modifier.offset(x, y).size(width, height).wrapContentSize(unbounded = true).then(modifier).rotate(rotation)) { content() }
}

@Composable
private fun CodeCard() {
    val colors = KhazenaTheme.colors
    Column(
        Modifier.size(206.dp, 268.dp).background(colors.surfaceCard, KhazenaRadius.hero).padding(top = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        KhazenaTag(stringResource(Res.string.intro_2_live_code), type = TagType.Live)
        Spacer(Modifier.height(12.dp))
        Box(Modifier.size(152.dp)) {
            val ink = colors.textPrimary
            Canvas(Modifier.matchParentSize()) {
                val cell = 8.dp.toPx()
                qrModules.forEachIndexed { row, line ->
                    line.forEachIndexed { col, c ->
                        if (c == '#') drawRoundRect(ink, Offset(col * cell, row * cell), Size(6.dp.toPx(), 6.dp.toPx()), CornerRadius(2.dp.toPx()))
                    }
                }
                listOf(Offset.Zero, Offset(112.dp.toPx(), 0f), Offset(0f, 112.dp.toPx())).forEach { o ->
                    drawRoundRect(ink, o, Size(40.dp.toPx(), 40.dp.toPx()), CornerRadius(10.dp.toPx()))
                    drawRoundRect(Color.White, o + Offset(7.dp.toPx(), 7.dp.toPx()), Size(26.dp.toPx(), 26.dp.toPx()), CornerRadius(6.dp.toPx()))
                    drawRoundRect(ink, o + Offset(13.dp.toPx(), 13.dp.toPx()), Size(14.dp.toPx(), 14.dp.toPx()), CornerRadius(4.dp.toPx()))
                }
            }
            Box(Modifier.offset(56.dp, 56.dp).size(40.dp).background(ink, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Image(painterResource(KhazenaLogo.MarkOnInk), null, Modifier.size(19.8.dp, 22.44.dp))
            }
        }
        Spacer(Modifier.height(18.dp))
        Text("4821 · 0937 · 7Q", style = KhazenaTheme.typography.monoCode.copy(fontSize = 13.sp), color = colors.textPrimary)
    }
}

@Composable
private fun NearYouHero() {
    val colors = KhazenaTheme.colors
    val kicker = KhazenaTheme.typography.monoKicker
    val time = rememberDrift()
    Box(Modifier.fillMaxSize().clip(KhazenaRadius.hero).clearAndSetSemantics {}) {
        Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(Res.string.intro_3_stat_near).uppercase(), style = kicker, color = colors.textPrimary)
            Text(stringResource(Res.string.intro_3_stat_location).uppercase(), style = kicker, color = colors.textPrimary)
        }
        Box(Modifier.align(Alignment.Center).requiredSize(350.dp, 424.dp)) {
            Tilted(23.95.dp, 104.dp, 289.02.dp, 252.66.dp, 5f) { MapTile() }
            Box(Modifier.offset(100.dp, 141.dp).breathe(time, 0f).size(150.dp).background(colors.textPrimary.copy(alpha = 0.18f), CircleShape))
            Box(Modifier.offset(123.dp, 164.dp).breathe(time, 0.15f).size(104.dp).background(colors.textPrimary.copy(alpha = 0.3f), CircleShape))
            Box(
                Modifier.offset(141.dp, 182.dp).size(68.dp)
                    .background(colors.surfaceBackground, CircleShape).padding(4.dp)
                    .background(colors.textPrimary, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Image(painterResource(Res.drawable.intro_pin), null, Modifier.size(34.dp))
            }
            Tilted(22.dp, 77.36.dp, 174.31.dp, 48.86.dp, -5f, Modifier.drift(time, 0.25f)) { PlaceChip(colors.blockSun, Res.string.intro_1_card_food, 200) }
            Tilted(143.63.dp, 324.dp, 191.91.dp, 47.17.dp, 4f, Modifier.drift(time, 0.75f)) { PlaceChip(colors.blockViolet, Res.string.intro_1_card_beauty, 450) }
            Tilted(292.dp, 116.dp, 23.05.dp, 17.93.dp, 30f, Modifier.drift(time, 0.1f)) { Box(Modifier.size(22.dp, 8.dp).background(colors.blockSun, KhazenaRadius.pill)) }
            Tilted(26.dp, 312.39.dp, 19.69.dp, 14.86.dp, -25f, Modifier.drift(time, 0.45f)) { Box(Modifier.size(18.dp, 8.dp).background(colors.surfaceBackground, KhazenaRadius.pill)) }
            Tilted(300.dp, 259.72.dp, 17.4.dp, 16.41.dp, -40f, Modifier.drift(time, 0.9f)) { Box(Modifier.size(16.dp, 8.dp).background(colors.textPrimary, KhazenaRadius.pill)) }
        }
    }
}

@Composable
private fun MapTile() {
    val colors = KhazenaTheme.colors
    val road = colors.borderDefault
    Box(Modifier.size(270.dp, 230.dp).clip(KhazenaRadius.hero).background(colors.surfaceBackground)) {
        Box(Modifier.wrapContentSize(Alignment.TopStart, unbounded = true).offset((-10).dp, 62.dp).requiredSize(300.dp, 16.dp).background(road, KhazenaRadius.pill))
        Box(Modifier.wrapContentSize(Alignment.TopStart, unbounded = true).offset((-10).dp, 150.dp).requiredSize(300.dp, 16.dp).background(road, KhazenaRadius.pill))
        Box(Modifier.wrapContentSize(Alignment.TopStart, unbounded = true).offset(78.dp, (-10).dp).requiredSize(16.dp, 260.dp).background(road, KhazenaRadius.pill))
        Box(Modifier.wrapContentSize(Alignment.TopStart, unbounded = true).offset(182.dp, (-10).dp).requiredSize(16.dp, 260.dp).background(road, KhazenaRadius.pill))
        Box(Modifier.offset(18.dp, 18.dp).size(44.dp, 30.dp).background(colors.blockSun, RoundedCornerShape(9.dp)))
        Box(Modifier.offset(110.dp, 96.dp).size(56.dp, 38.dp).background(colors.blockViolet, RoundedCornerShape(10.dp)))
        Box(Modifier.offset(212.dp, 20.dp).size(40.dp, 30.dp).background(colors.blockSun, RoundedCornerShape(9.dp)))
        Box(Modifier.offset(20.dp, 184.dp).size(44.dp, 30.dp).background(colors.blockViolet, RoundedCornerShape(9.dp)))
        Box(Modifier.offset(212.dp, 180.dp).size(40.dp, 34.dp).background(colors.blockSun, RoundedCornerShape(9.dp)))
    }
}

@Composable
private fun PlaceChip(dot: Color, name: StringResource, meters: Int) {
    val colors = KhazenaTheme.colors
    Row(
        Modifier.background(colors.surfaceCard, KhazenaRadius.pill).padding(start = 10.dp, end = 14.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(18.dp).background(dot, CircleShape))
        Text(stringResource(name), style = KhazenaTheme.typography.labelSMedium.copy(fontWeight = FontWeight.SemiBold), color = colors.textPrimary, maxLines = 1)
        Text(stringResource(Res.string.intro_3_distance_m, meters).uppercase(), style = KhazenaTheme.typography.monoTagS.copy(letterSpacing = 0.sp), color = colors.textSecondary, maxLines = 1)
    }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun IntroPreview() = KhazenaTheme { IntroScreen(IntroState()) {} }
