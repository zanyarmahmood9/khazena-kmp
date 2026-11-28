package com.khazena.khazena.feature.store.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaMotion
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSize
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.clearFocusOnTap
import com.khazena.khazena.core.designsystem.component.FactKind
import com.khazena.khazena.core.designsystem.component.IconButtonStyle
import com.khazena.khazena.core.designsystem.component.KhazenaFactChip
import com.khazena.khazena.core.designsystem.component.KhazenaIconButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaSegmentedControl
import com.khazena.khazena.core.designsystem.component.KhazenaShimmer
import com.khazena.khazena.core.designsystem.component.KhazenaSkeleton
import com.khazena.khazena.core.designsystem.component.KhazenaSkeletonRow
import com.khazena.khazena.core.designsystem.component.KhazenaSkeletonText
import com.khazena.khazena.core.designsystem.component.KhazenaTag
import com.khazena.khazena.core.designsystem.component.TagType
import com.khazena.khazena.core.designsystem.pressScale
import com.khazena.khazena.core.designsystem.rememberThrottledClick
import com.khazena.khazena.core.designsystem.softReveal
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.core.presentation.UiText
import com.khazena.khazena.core.presentation.formatDistance
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.core.presentation.state.LoadState
import com.khazena.khazena.core.presentation.state.LoadStateContent
import com.khazena.khazena.core.presentation.state.MessageToastHost
import com.khazena.khazena.core.presentation.state.MessageToastState
import com.khazena.khazena.core.presentation.state.rememberMessageToastState
import com.khazena.khazena.core.presentation.system.LightStatusBarIcons
import com.khazena.khazena.feature.store.domain.Store
import khazena.core.presentation.generated.resources.back
import khazena.core.presentation.generated.resources.loading
import khazena.feature.store.presentation.generated.resources.Res
import khazena.feature.store.presentation.generated.resources.store_address_copied
import khazena.feature.store.presentation.generated.resources.store_closed
import khazena.feature.store.presentation.generated.resources.store_cta_sub
import khazena.feature.store.presentation.generated.resources.store_cta_title
import khazena.feature.store.presentation.generated.resources.store_offer_headline
import khazena.feature.store.presentation.generated.resources.store_offer_kicker
import khazena.feature.store.presentation.generated.resources.store_offer_note
import khazena.feature.store.presentation.generated.resources.store_open_now
import khazena.feature.store.presentation.generated.resources.store_photos
import khazena.feature.store.presentation.generated.resources.store_save
import khazena.feature.store.presentation.generated.resources.store_scan_me
import khazena.feature.store.presentation.generated.resources.store_share
import khazena.feature.store.presentation.generated.resources.store_tab_info
import khazena.feature.store.presentation.generated.resources.store_tab_reviews
import khazena.feature.store.presentation.generated.resources.store_tab_services
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import khazena.core.presentation.generated.resources.Res as CoreRes

@Composable
fun StoreRoot(onBack: () -> Unit, onScanMe: () -> Unit, viewModel: StoreViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    val uriHandler = LocalUriHandler.current
    @Suppress("DEPRECATION") // LocalClipboard needs a platform ClipEntry; the text manager is the common path for now.
    val clipboard = LocalClipboardManager.current
    val loaded = (state.store.content as? LoadState.Data)?.value
    val address = loaded?.let { "${it.address.localized()}, ${it.area.localized()}" }
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is StoreEvent.ShowMessage -> toast.show(event.message)
        }
    }
    StoreScreen(state, toast) { action ->
        when (action) {
            StoreAction.OnBackClick -> onBack()
            StoreAction.OnScanMeClick -> onScanMe()
            is StoreAction.OnContactClick -> runCatching { uriHandler.openUri(action.contact.link) }
            StoreAction.OnDirectionsClick -> loaded?.let { runCatching { uriHandler.openUri(directionsLink(it)) } }
            StoreAction.OnCopyAddressClick -> address?.let {
                clipboard.setText(AnnotatedString(it))
                toast.show(UiText.Resource(Res.string.store_address_copied))
            }
            // shortcut: share sheet, photo gallery, product page, the review flow and the full review list are later
            // screens (Figma 02f, 07a, overlays); their taps do nothing until those land.
            else -> Unit
        }
        viewModel.onAction(action)
    }
}

private fun directionsLink(store: Store) = "https://www.google.com/maps/dir/?api=1&destination=${store.latitude},${store.longitude}"

// Figma 02c: the cover runs under the status bar, 300dp tall; the info card overlaps it from 238dp.
private val CoverHeight = 300.dp
private val CardTop = 238.dp
private val CoverShade = Brush.verticalGradient(0f to Color.Black.copy(alpha = 0.45f), 1f to Color.Transparent)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(state: StoreState, toastState: MessageToastState, onAction: (StoreAction) -> Unit) {
    val store = (state.store.content as? LoadState.Data)?.value
    val colors = KhazenaTheme.colors
    val scroll = rememberScrollState()
    val refreshState = rememberPullToRefreshState()
    // The bar turns solid once the cover has scrolled up under it.
    var coverBottom by remember { mutableFloatStateOf(Float.MAX_VALUE) }
    var barBottom by remember { mutableFloatStateOf(0f) }
    // The cover photo is dark, so the status bar goes light over it; the light skeleton cover keeps dark icons.
    val overCover = store != null && coverBottom > barBottom
    LightStatusBarIcons(enabled = overCover)
    Box(Modifier.fillMaxSize().background(colors.surfaceBackground).clearFocusOnTap()) {
        PullToRefreshBox(
            isRefreshing = state.store.isRefreshing,
            onRefresh = { onAction(StoreAction.OnRefresh) },
            state = refreshState,
            modifier = Modifier.fillMaxSize(),
            indicator = {
                PullToRefreshDefaults.Indicator(
                    state = refreshState,
                    isRefreshing = state.store.isRefreshing,
                    modifier = Modifier.align(Alignment.TopCenter).statusBarsPadding().padding(top = 60.dp),
                )
            },
        ) {
            Column(Modifier.fillMaxSize().verticalScroll(scroll).navigationBarsPadding().padding(bottom = CheckoutBarClearance)) {
                AnimatedContent(
                    targetState = state.store.content,
                    contentKey = { it::class },
                    transitionSpec = { softReveal() },
                    contentAlignment = Alignment.TopCenter,
                    label = "store-content",
                ) { content ->
                    when (content) {
                        is LoadState.Data -> Column(Modifier.fillMaxWidth()) { StoreBody(content.value, state, onAction) { coverBottom = it } }
                        LoadState.Loading -> StoreSkeleton()
                        else -> Column {
                            Box(Modifier.fillMaxWidth().height(CoverHeight).background(colors.surfaceTile))
                            LoadStateContent(
                                state = content,
                                onRetry = { onAction(StoreAction.OnRetry) },
                                empty = {},
                                modifier = Modifier.fillMaxWidth().padding(KhazenaSpacing.screenMargin),
                            ) {}
                        }
                    }
                }
            }
        }
        StoreTopBar(
            title = store?.name?.localized().orEmpty(),
            solid = store != null && !overCover,
            isSaved = state.isSaved,
            onAction = onAction,
            modifier = Modifier.onGloballyPositioned { barBottom = it.boundsInRoot().bottom },
        )
        if (store != null) {
            CheckoutBar(store.discountPercent, { onAction(StoreAction.OnScanMeClick) }, Modifier.align(Alignment.BottomCenter))
        }
        MessageToastHost(toastState, Modifier.align(Alignment.BottomCenter).navigationBarsPadding().padding(bottom = CheckoutBarClearance))
    }
}

@Composable
private fun StoreBody(store: Store, state: StoreState, onAction: (StoreAction) -> Unit, onCoverBottom: (Float) -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Cover(store, Modifier.onGloballyPositioned { onCoverBottom(it.boundsInRoot().bottom) }, onAction)
        InfoCard(store, Modifier.padding(start = 16.dp, end = 16.dp, top = CardTop))
    }
    KhazenaSegmentedControl(
        options = listOf(
            stringResource(Res.string.store_tab_services),
            stringResource(Res.string.store_tab_reviews, store.reviewCount),
            stringResource(Res.string.store_tab_info),
        ),
        selectedIndex = state.tab.ordinal,
        onSelect = { onAction(StoreAction.OnTabSelect(StoreTab.entries[it])) },
        modifier = Modifier.padding(start = KhazenaSpacing.screenMargin, end = KhazenaSpacing.screenMargin, top = 21.dp),
    )
    val rtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    // The tab body follows the segment: a later tab slides in from the trailing side, an earlier one from the leading side.
    AnimatedContent(
        targetState = state.tab,
        transitionSpec = {
            val fromEnd = (targetState.ordinal > initialState.ordinal) != rtl
            val slide = tween<IntOffset>(KhazenaMotion.LONG, easing = KhazenaMotion.EmphasizedDecelerate)
            (slideInHorizontally(slide) { if (fromEnd) it / 3 else -it / 3 } + fadeIn(tween(KhazenaMotion.MEDIUM))) togetherWith
                (slideOutHorizontally(slide) { if (fromEnd) -it / 3 else it / 3 } + fadeOut(tween(KhazenaMotion.SHORT))) using
                SizeTransform(clip = false)
        },
        contentAlignment = Alignment.TopCenter,
        label = "store-tab",
    ) { tab ->
        Column(Modifier.fillMaxWidth().padding(horizontal = KhazenaSpacing.screenMargin).padding(top = 16.dp)) {
            when (tab) {
                StoreTab.Services -> ServicesTab(store, state, onAction)
                StoreTab.Reviews -> ReviewsTab(store, state.reviewSort, onAction)
                StoreTab.Info -> InfoTab(store, onAction)
            }
        }
    }
}

@Composable
private fun Cover(store: Store, modifier: Modifier, onAction: (StoreAction) -> Unit) {
    Box(modifier.fillMaxWidth().height(CoverHeight)) {
        Image(painterResource(storeImage(store.coverKey)), null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Box(Modifier.fillMaxWidth().height(130.dp).background(CoverShade))
        Text(
            stringResource(Res.string.store_photos, 1, store.photoCount).uppercase(),
            style = KhazenaTheme.typography.monoTagS,
            color = Color.White,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 200.dp, end = KhazenaSpacing.screenMargin)
                .clip(KhazenaRadius.pill)
                .background(KhazenaTheme.colors.blockInk.copy(alpha = 0.85f))
                .clickable(role = Role.Button) { onAction(StoreAction.OnPhotosClick) }
                .padding(horizontal = 10.dp, vertical = 5.dp),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InfoCard(store: Store, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Column(
        modifier
            .fillMaxWidth()
            .clip(KhazenaRadius.card)
            .background(colors.surfaceCard)
            .padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 16.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                store.categoryLine.localized().uppercase(),
                style = type.monoKicker,
                color = colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            OpenTag(store.isOpenNow)
        }
        Row(Modifier.padding(top = 10.dp), horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
            StoreLogo(store, Modifier.size(60.dp), RoundedCornerShape(16.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                // Figma sets the name with a closing full stop, as a brand flourish.
                Text(
                    store.name.localized() + ".",
                    style = type.headingH2.copy(lineHeight = 30.8.sp),
                    color = colors.textPrimary,
                    modifier = Modifier.semantics { heading() },
                )
                Text(store.cuisine.localized(), style = type.labelSMedium, color = colors.textSecondary)
            }
        }
        FlowRow(Modifier.padding(top = 14.dp), horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            KhazenaFactChip(FactKind.Rating, "${store.rating} (${store.reviewCount})")
            KhazenaFactChip(FactKind.Distance, formatDistance(store.distanceKm))
            KhazenaFactChip(FactKind.Hours, store.hoursNote.localized())
        }
        Spacer(Modifier.height(14.dp))
        OfferCard(store.discountPercent)
    }
}

@Composable
internal fun OpenTag(open: Boolean) {
    KhazenaTag(
        stringResource(if (open) Res.string.store_open_now else Res.string.store_closed),
        type = if (open) TagType.Live else TagType.Expired,
    )
}

// The store's logo tile; a store without one gets its initial on ink, the same tile shape.
@Composable
internal fun StoreLogo(store: Store, modifier: Modifier, shape: RoundedCornerShape) {
    val colors = KhazenaTheme.colors
    Box(modifier.clip(shape).background(colors.blockInk), contentAlignment = Alignment.Center) {
        val logo = store.logoKey
        if (logo != null) {
            // Figma crops the emblem at 111% so its ring sits just inside the tile.
            Image(
                painterResource(storeImage(logo)),
                null,
                Modifier.fillMaxSize().graphicsLayer { scaleX = 1.111f; scaleY = 1.111f },
                contentScale = ContentScale.Crop,
            )
        } else {
            Text(store.name.localized().take(1), style = KhazenaTheme.typography.headingH2, color = colors.blockSun)
        }
    }
}

@Composable
private fun OfferCard(discountPercent: Int) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Row(
        Modifier.fillMaxWidth().clip(KhazenaRadius.card).background(colors.blockSun).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(stringResource(Res.string.store_offer_kicker).uppercase(), style = type.monoKicker, color = colors.textPrimary.copy(alpha = 0.75f))
            Text(stringResource(Res.string.store_offer_headline, discountPercent), style = type.headingH2Tight, color = colors.textPrimary)
            Text(stringResource(Res.string.store_offer_note), style = type.bodyS.copy(fontSize = 12.sp), color = colors.textPrimary.copy(alpha = 0.8f))
        }
        Box(Modifier.size(48.dp).background(colors.blockInk, CircleShape), contentAlignment = Alignment.Center) {
            Text("%", style = type.headingH2.copy(fontSize = 24.sp), color = colors.blockSun)
        }
    }
}

// Pinned over the cover: white back button and the share/save pill. Once the cover is gone the bar fills with
// paper and shows the store name, like the category screen.
@Composable
private fun StoreTopBar(title: String, solid: Boolean, isSaved: Boolean, onAction: (StoreAction) -> Unit, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val background by animateColorAsState(if (solid) colors.surfaceBackground else colors.surfaceBackground.copy(alpha = 0f), tween(KhazenaMotion.MEDIUM), label = "store-bar")
    val solidAlpha by animateFloatAsState(if (solid) 1f else 0f, tween(KhazenaMotion.MEDIUM), label = "store-bar-title")
    Box(
        modifier
            .fillMaxWidth()
            .background(background)
            .drawBehind {
                drawLine(colors.borderDefault.copy(alpha = solidAlpha), Offset(0f, size.height), Offset(size.width, size.height), 1.dp.toPx())
            }
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        KhazenaIconButton(
            KhazenaIcons.ArrowLeft,
            stringResource(CoreRes.string.back),
            { onAction(StoreAction.OnBackClick) },
            Modifier.align(Alignment.CenterStart),
            style = IconButtonStyle.White,
        )
        Text(
            title,
            style = KhazenaTheme.typography.headingH4.copy(fontSize = 20.sp),
            color = colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 112.dp).graphicsLayer { alpha = solidAlpha },
        )
        Row(
            Modifier.align(Alignment.CenterEnd).clip(KhazenaRadius.pill).background(colors.surfaceCard).padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BarAction(KhazenaIcons.Export, stringResource(Res.string.store_share), colors.textPrimary) { onAction(StoreAction.OnShareClick) }
            Box(Modifier.width(1.dp).height(20.dp).background(colors.blockInk.copy(alpha = 0.15f)))
            BarAction(KhazenaIcons.Bookmark, stringResource(Res.string.store_save), if (isSaved) colors.actionPrimary else colors.textPrimary) {
                onAction(StoreAction.OnSaveClick)
            }
        }
    }
}

@Composable
private fun BarAction(icon: DrawableResource, label: String, tint: Color, onClick: () -> Unit) {
    val interaction = remember { MutableInteractionSource() }
    Box(
        Modifier
            .size(KhazenaSize.tapMin)
            .pressScale(interaction, pressedScale = 0.9f)
            .clip(CircleShape)
            .clickable(interaction, LocalIndication.current, role = Role.Button, onClickLabel = label, onClick = rememberThrottledClick(onClick)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(painterResource(icon), label, Modifier.size(20.dp), tint = tint)
    }
}

// Figma "Sticky checkout bar": a paper fade, then a frosted pill with the QR prompt and the Scan Me button.
private val CheckoutBarClearance = 108.dp

@Composable
private fun CheckoutBar(discountPercent: Int, onScanMe: () -> Unit, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    val paper = colors.surfaceBackground
    Box(
        modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(0f to paper.copy(alpha = 0f), 0.6f to paper.copy(alpha = 0.9f), 1f to paper))
            .navigationBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 14.dp, bottom = 8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 66.dp)
                .clip(RoundedCornerShape(33.dp))
                .background(Color.White.copy(alpha = 0.94f))
                .border(1.dp, Color.White.copy(alpha = 0.9f), RoundedCornerShape(33.dp))
                .padding(start = 18.dp, end = 7.dp, top = 7.dp, bottom = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(stringResource(Res.string.store_cta_title), style = type.labelMSemiBold, color = colors.textPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(stringResource(Res.string.store_cta_sub, discountPercent), style = type.bodyS.copy(fontSize = 12.sp), color = colors.textSecondary, maxLines = 1)
            }
            PillButton(
                label = stringResource(Res.string.store_scan_me),
                icon = KhazenaIcons.QrCodeLine,
                container = colors.actionPrimary,
                content = colors.actionOnPrimary,
                onClick = onScanMe,
                height = 52.dp,
                paddingX = 18.dp,
                textStyle = type.labelMSemiBold.copy(fontWeight = FontWeight.Bold),
                iconSize = 18.dp,
            )
        }
    }
}

// Cover, info card, segments and menu rows at their loaded positions.
@Composable
private fun StoreSkeleton() {
    val colors = KhazenaTheme.colors
    KhazenaShimmer(stringResource(CoreRes.string.loading)) {
        Column {
            Box(Modifier.fillMaxWidth()) {
                KhazenaSkeleton(Modifier.fillMaxWidth().height(CoverHeight), RoundedCornerShape(0.dp))
                Column(
                    Modifier
                        .padding(start = 16.dp, end = 16.dp, top = CardTop)
                        .fillMaxWidth()
                        .clip(KhazenaRadius.card)
                        .background(colors.surfaceCard)
                        .padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 16.dp),
                ) {
                    Row(Modifier.fillMaxWidth().height(24.dp), verticalAlignment = Alignment.CenterVertically) {
                        KhazenaSkeletonText(KhazenaTheme.typography.monoKicker, listOf(140.dp), Modifier.weight(1f))
                        KhazenaSkeleton(Modifier.size(width = 86.dp, height = 24.dp), KhazenaRadius.pill)
                    }
                    Row(Modifier.padding(top = 10.dp), horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        KhazenaSkeleton(Modifier.size(60.dp), RoundedCornerShape(16.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            KhazenaSkeletonText(KhazenaTheme.typography.headingH2.copy(lineHeight = 30.8.sp), listOf(160.dp))
                            KhazenaSkeletonText(KhazenaTheme.typography.labelSMedium, listOf(200.dp))
                        }
                    }
                    Row(Modifier.padding(top = 14.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(102, 81, 115).forEach { KhazenaSkeleton(Modifier.size(width = it.dp, height = 28.dp), KhazenaRadius.pill) }
                    }
                    KhazenaSkeleton(Modifier.padding(top = 14.dp).fillMaxWidth().height(125.dp), KhazenaRadius.card)
                }
            }
            KhazenaSkeleton(
                Modifier.padding(start = KhazenaSpacing.screenMargin, end = KhazenaSpacing.screenMargin, top = 21.dp).fillMaxWidth().height(48.dp),
                KhazenaRadius.pill,
            )
            Column(
                Modifier.padding(horizontal = KhazenaSpacing.screenMargin).padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                KhazenaSkeleton(Modifier.fillMaxWidth().height(52.dp), RoundedCornerShape(18.dp))
                repeat(3) { KhazenaSkeletonRow() }
            }
        }
    }
}

@Composable
internal fun PillButton(
    label: String,
    icon: DrawableResource?,
    container: Color,
    content: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 40.dp,
    paddingX: Dp = 16.dp,
    textStyle: TextStyle = KhazenaTheme.typography.labelSMedium,
    iconSize: Dp = 16.dp,
    trailingIcon: Boolean = false,
) {
    val interaction = remember { MutableInteractionSource() }
    Row(
        modifier
            .height(height)
            .pressScale(interaction)
            .clip(KhazenaRadius.pill)
            .background(container)
            .clickable(interaction, LocalIndication.current, role = Role.Button, onClick = rememberThrottledClick(onClick))
            .padding(horizontal = paddingX),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null && !trailingIcon) Icon(painterResource(icon), null, Modifier.size(iconSize), tint = content)
        Text(label, style = textStyle, color = content, maxLines = 1)
        if (icon != null && trailingIcon) Icon(painterResource(icon), null, Modifier.size(iconSize), tint = content)
    }
}
