package com.khazena.khazena.feature.store.presentation

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.bleedHorizontally
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaShimmer
import com.khazena.khazena.core.designsystem.component.KhazenaSkeleton
import com.khazena.khazena.core.designsystem.component.KhazenaSkeletonText
import com.khazena.khazena.core.designsystem.pressScale
import com.khazena.khazena.core.designsystem.rememberThrottledClick
import com.khazena.khazena.core.designsystem.softReveal
import com.khazena.khazena.core.designsystem.textClickable
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.core.presentation.formatDistance
import com.khazena.khazena.core.presentation.formatGrouped
import com.khazena.khazena.core.presentation.formatIqd
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.core.presentation.state.LoadState
import com.khazena.khazena.core.presentation.state.LoadStateContent
import com.khazena.khazena.core.presentation.state.MessageToastHost
import com.khazena.khazena.core.presentation.state.MessageToastState
import com.khazena.khazena.core.presentation.state.rememberMessageToastState
import com.khazena.khazena.core.presentation.system.LightStatusBarIcons
import com.khazena.khazena.feature.store.domain.MenuItem
import com.khazena.khazena.feature.store.domain.Store
import khazena.core.presentation.generated.resources.loading
import khazena.feature.store.presentation.generated.resources.Res
import khazena.feature.store.presentation.generated.resources.product_cta_sub
import khazena.feature.store.presentation.generated.resources.product_cta_title
import khazena.feature.store.presentation.generated.resources.product_description
import khazena.feature.store.presentation.generated.resources.product_discount
import khazena.feature.store.presentation.generated.resources.product_how_title
import khazena.feature.store.presentation.generated.resources.product_included
import khazena.feature.store.presentation.generated.resources.product_member_price
import khazena.feature.store.presentation.generated.resources.product_minutes
import khazena.feature.store.presentation.generated.resources.product_more
import khazena.feature.store.presentation.generated.resources.product_most_ordered
import khazena.feature.store.presentation.generated.resources.product_see_menu
import khazena.feature.store.presentation.generated.resources.product_serves
import khazena.feature.store.presentation.generated.resources.product_spicy
import khazena.feature.store.presentation.generated.resources.product_step_order
import khazena.feature.store.presentation.generated.resources.product_step_scan
import khazena.feature.store.presentation.generated.resources.product_step_show
import khazena.feature.store.presentation.generated.resources.product_you_save
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import khazena.core.presentation.generated.resources.Res as CoreRes

@Composable
fun ProductRoot(
    onBack: () -> Unit,
    onOpenStore: () -> Unit,
    onOpenProduct: (String) -> Unit,
    onScanMe: () -> Unit,
    viewModel: ProductViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ProductEvent.ShowMessage -> toast.show(event.message)
        }
    }
    ProductScreen(state, toast) { action ->
        when (action) {
            ProductAction.OnBackClick -> onBack()
            ProductAction.OnStoreClick, ProductAction.OnSeeMenuClick -> onOpenStore()
            is ProductAction.OnMoreItemClick -> onOpenProduct(action.id)
            ProductAction.OnScanMeClick -> onScanMe()
            // shortcut: the share sheet is a later overlay; the tap does nothing until it lands.
            else -> Unit
        }
        viewModel.onAction(action)
    }
}

// Figma 02f: a 380dp photo under the status bar; the details sheet overlaps it from 344dp with 32dp top corners.
private val PhotoHeight = 380.dp
private val SheetTop = 344.dp
private val SheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(state: ProductState, toastState: MessageToastState, onAction: (ProductAction) -> Unit) {
    val store = (state.store.content as? LoadState.Data)?.value
    val item = store?.item(state.itemId)
    val colors = KhazenaTheme.colors
    val scroll = rememberScrollState()
    val refreshState = rememberPullToRefreshState()
    var photoBottom by remember { mutableFloatStateOf(Float.MAX_VALUE) }
    var barBottom by remember { mutableFloatStateOf(0f) }
    val overPhoto = item != null && photoBottom > barBottom
    LightStatusBarIcons(enabled = overPhoto)
    Box(Modifier.fillMaxSize().background(colors.surfaceBackground)) {
        PullToRefreshBox(
            isRefreshing = state.store.isRefreshing,
            onRefresh = { onAction(ProductAction.OnRefresh) },
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
                    label = "product-content",
                ) { content ->
                    val loaded = (content as? LoadState.Data)?.value
                    val product = loaded?.item(state.itemId)
                    when {
                        loaded != null && product != null -> ProductBody(loaded, product, onAction) { photoBottom = it }
                        content == LoadState.Loading -> ProductSkeleton()
                        else -> Column {
                            Box(Modifier.fillMaxWidth().height(PhotoHeight).background(colors.surfaceTile))
                            LoadStateContent(
                                state = content,
                                onRetry = { onAction(ProductAction.OnRetry) },
                                empty = {},
                                modifier = Modifier.fillMaxWidth().padding(KhazenaSpacing.screenMargin),
                            ) {}
                        }
                    }
                }
            }
        }
        PhotoTopBar(
            title = item?.name?.localized().orEmpty(),
            solid = item != null && !overPhoto,
            isSaved = state.isSaved,
            onBack = { onAction(ProductAction.OnBackClick) },
            onShare = { onAction(ProductAction.OnShareClick) },
            onSave = { onAction(ProductAction.OnSaveClick) },
            modifier = Modifier.onGloballyPositioned { barBottom = it.boundsInRoot().bottom },
        )
        if (item != null) {
            CheckoutBar(
                title = stringResource(Res.string.product_cta_title),
                subtitle = stringResource(Res.string.product_cta_sub, formatGrouped(item.memberPriceIqd), formatIqd(item.priceIqd)),
                onScanMe = { onAction(ProductAction.OnScanMeClick) },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
        MessageToastHost(toastState, Modifier.align(Alignment.BottomCenter).navigationBarsPadding().padding(bottom = CheckoutBarClearance))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProductBody(store: Store, item: MenuItem, onAction: (ProductAction) -> Unit, onPhotoBottom: (Float) -> Unit) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    val details = item.details
    Box(Modifier.fillMaxWidth()) {
        Box(Modifier.fillMaxWidth().height(PhotoHeight).onGloballyPositioned { onPhotoBottom(it.boundsInRoot().bottom) }) {
            Image(painterResource(productPhoto(item.imageKey)), null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Box(Modifier.fillMaxWidth().height(130.dp).background(CoverShade))
        }
        Column(
            Modifier
                .padding(top = SheetTop)
                .fillMaxWidth()
                .clip(SheetShape)
                .background(colors.surfaceBackground)
                .padding(horizontal = KhazenaSpacing.screenMargin, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                store.menuSections.firstOrNull { it.id == item.sectionId }?.let { section ->
                    Badge(section.name.localized(), colors.surfaceTile)
                }
                if (item.popular) Badge(stringResource(Res.string.product_most_ordered), colors.statusPendingTint, KhazenaIcons.Fire)
            }
            Text(
                item.name.localized(),
                style = type.headingH2Tight.copy(lineHeight = 30.sp, letterSpacing = (-0.02).em),
                color = colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
            StoreRow(store) { onAction(ProductAction.OnStoreClick) }
            PriceCard(store, item)
            if (details != null) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    details.serves?.let { Fact(KhazenaIcons.UserLine, stringResource(Res.string.product_serves, it)) }
                    details.prepMinutes?.let { Fact(KhazenaIcons.ClockLine, stringResource(Res.string.product_minutes, it)) }
                    if (details.spicyOption) Fact(KhazenaIcons.Fire, stringResource(Res.string.product_spicy))
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                SectionTitle(stringResource(Res.string.product_description))
                Text(
                    (details?.story ?: item.description).localized(),
                    style = type.bodyS.copy(lineHeight = 20.sp),
                    color = colors.textSecondary,
                )
            }
            if (details != null && details.includes.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionTitle(stringResource(Res.string.product_included))
                    details.includes.forEach { included ->
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(22.dp).background(colors.blockInk, CircleShape), contentAlignment = Alignment.Center) {
                                Icon(painterResource(KhazenaIcons.CheckLine), null, Modifier.size(12.dp), tint = Color.White)
                            }
                            Text(included.localized(), style = type.bodyM.copy(fontSize = 14.sp), color = colors.textPrimary)
                        }
                    }
                }
            }
            HowToGetPrice(store.discountPercent)
            val more = store.moreThan(item.id)
            if (more.isNotEmpty()) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    SectionTitle(stringResource(Res.string.product_more, store.name.localized()), Modifier.weight(1f))
                    Text(
                        stringResource(Res.string.product_see_menu),
                        style = type.bodyS,
                        color = colors.textSecondary,
                        modifier = Modifier.textClickable(onClick = { onAction(ProductAction.OnSeeMenuClick) }),
                    )
                }
                LazyRow(
                    Modifier.bleedHorizontally(KhazenaSpacing.screenMargin),
                    contentPadding = PaddingValues(horizontal = KhazenaSpacing.screenMargin),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(more, key = { it.id }) { other -> MiniItem(other) { onAction(ProductAction.OnMoreItemClick(other.id)) } }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(text, style = KhazenaTheme.typography.labelLBold.copy(fontWeight = FontWeight.SemiBold), color = KhazenaTheme.colors.textPrimary, modifier = modifier)
}

@Composable
private fun Badge(label: String, container: Color, icon: DrawableResource? = null) {
    Row(
        Modifier.clip(KhazenaRadius.pill).background(container).padding(horizontal = 10.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) Icon(painterResource(icon), null, Modifier.size(12.dp), tint = KhazenaTheme.colors.textPrimary)
        Text(
            label,
            style = KhazenaTheme.typography.labelXSMedium.copy(fontSize = 11.sp, fontWeight = FontWeight.SemiBold),
            color = KhazenaTheme.colors.textPrimary,
            maxLines = 1,
        )
    }
}

@Composable
private fun StoreRow(store: Store, onClick: () -> Unit) {
    val colors = KhazenaTheme.colors
    Row(
        Modifier.clip(KhazenaRadius.xs).clickable(role = Role.Button, onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StoreLogo(store, Modifier.size(26.dp), RoundedCornerShape(8.dp))
        Text(store.name.localized(), style = KhazenaTheme.typography.labelSMedium.copy(fontWeight = FontWeight.SemiBold), color = colors.textPrimary, maxLines = 1)
        Icon(painterResource(KhazenaIcons.StarSmall), null, Modifier.size(13.dp), tint = colors.blockSun)
        Text(
            "${store.rating} · ${store.street.localized()} · ${formatDistance(store.distanceKm)}",
            style = KhazenaTheme.typography.bodyS.copy(fontSize = 12.sp),
            color = colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun PriceCard(store: Store, item: MenuItem) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Column(
        Modifier.fillMaxWidth().clip(KhazenaRadius.card).background(colors.surfaceCard).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(stringResource(Res.string.product_member_price), style = type.bodyS.copy(fontSize = 12.sp), color = colors.textSecondary)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Bottom) {
                    Text(
                        formatIqd(item.memberPriceIqd),
                        style = type.headingH2.copy(fontSize = 30.sp, letterSpacing = (-0.04).em),
                        color = colors.textPrimary,
                        modifier = Modifier.alignByBaseline(),
                    )
                    Text(
                        formatIqd(item.priceIqd),
                        style = type.bodyM.copy(fontSize = 14.sp, textDecoration = TextDecoration.LineThrough),
                        color = colors.textPlaceholder,
                        modifier = Modifier.alignByBaseline(),
                    )
                }
            }
            Text(
                stringResource(Res.string.product_discount, store.discountPercent),
                style = type.monoTag,
                color = colors.textPrimary,
                modifier = Modifier.clip(KhazenaRadius.pill).background(colors.tagDiscount).padding(horizontal = 10.dp, vertical = 6.dp),
            )
        }
        HorizontalDivider(thickness = 1.dp, color = colors.surfaceTile)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(26.dp).background(colors.blockSun, CircleShape), contentAlignment = Alignment.Center) {
                Icon(painterResource(KhazenaIcons.CheckLine), null, Modifier.size(14.dp), tint = colors.textPrimary)
            }
            Text(
                stringResource(Res.string.product_you_save, formatIqd(item.priceIqd - item.memberPriceIqd)),
                style = type.labelSMedium.copy(fontWeight = FontWeight.SemiBold),
                color = colors.textPrimary,
            )
        }
    }
}

@Composable
private fun Fact(icon: DrawableResource, label: String) {
    val colors = KhazenaTheme.colors
    Row(
        Modifier
            .clip(KhazenaRadius.pill)
            .background(colors.surfaceCard)
            .border(1.dp, colors.borderDefault, KhazenaRadius.pill)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(painterResource(icon), null, Modifier.size(14.dp), tint = colors.textSecondary)
        Text(label, style = KhazenaTheme.typography.labelXSMedium, color = colors.textPrimary, maxLines = 1)
    }
}

@Composable
private fun HowToGetPrice(discountPercent: Int) {
    val type = KhazenaTheme.typography
    val onViolet = Color.White
    Column(
        Modifier.fillMaxWidth().clip(KhazenaRadius.block).background(KhazenaTheme.colors.blockViolet).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(stringResource(Res.string.product_how_title), style = type.labelPrice, color = onViolet)
        listOf(
            KhazenaIcons.Receipt to stringResource(Res.string.product_step_order),
            KhazenaIcons.QrCodeLine to stringResource(Res.string.product_step_show),
            KhazenaIcons.ScanLine to stringResource(Res.string.product_step_scan, discountPercent),
        ).forEach { (icon, text) ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(32.dp).background(Color.White, CircleShape), contentAlignment = Alignment.Center) {
                    Icon(painterResource(icon), null, Modifier.size(16.dp), tint = KhazenaTheme.colors.textPrimary)
                }
                Text(text, style = type.labelSMedium, color = onViolet.copy(alpha = 0.95f), modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun MiniItem(item: MenuItem, onClick: () -> Unit) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    val interaction = remember { MutableInteractionSource() }
    Column(
        Modifier
            .width(160.dp)
            .pressScale(interaction)
            .clip(KhazenaRadius.card)
            .background(colors.surfaceCard)
            .clickable(interaction, LocalIndication.current, role = Role.Button, onClick = rememberThrottledClick(onClick))
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Image(
            painterResource(storeImage(item.imageKey)),
            null,
            Modifier.fillMaxWidth().height(100.dp).clip(KhazenaRadius.card),
            contentScale = ContentScale.Crop,
        )
        Column(Modifier.padding(horizontal = 4.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                item.name.localized(),
                style = type.labelSMedium.copy(fontWeight = FontWeight.SemiBold),
                color = colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(formatIqd(item.memberPriceIqd), style = type.labelSMedium.copy(fontWeight = FontWeight.Bold), color = colors.textPrimary, modifier = Modifier.alignByBaseline())
                Text(
                    formatGrouped(item.priceIqd),
                    style = type.bodyS.copy(fontSize = 11.sp, textDecoration = TextDecoration.LineThrough),
                    color = colors.textPlaceholder,
                    modifier = Modifier.alignByBaseline(),
                )
            }
        }
    }
}

// Photo, then the sheet's badges, title, store row, price card and facts at their loaded positions.
@Composable
private fun ProductSkeleton() {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    KhazenaShimmer(stringResource(CoreRes.string.loading)) {
        Box(Modifier.fillMaxWidth()) {
            KhazenaSkeleton(Modifier.fillMaxWidth().height(PhotoHeight), RoundedCornerShape(0.dp))
            Column(
                Modifier
                    .padding(top = SheetTop)
                    .fillMaxWidth()
                    .clip(SheetShape)
                    .background(colors.surfaceBackground)
                    .padding(horizontal = KhazenaSpacing.screenMargin, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    KhazenaSkeleton(Modifier.size(width = 52.dp, height = 24.dp), KhazenaRadius.pill)
                    KhazenaSkeleton(Modifier.size(width = 104.dp, height = 24.dp), KhazenaRadius.pill)
                }
                KhazenaSkeletonText(type.headingH2Tight.copy(lineHeight = 30.sp), listOf(240.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    KhazenaSkeleton(Modifier.size(26.dp), RoundedCornerShape(8.dp))
                    KhazenaSkeletonText(type.bodyS, listOf(200.dp))
                }
                KhazenaSkeleton(Modifier.fillMaxWidth().height(124.dp), KhazenaRadius.card)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(86, 84, 110).forEach { KhazenaSkeleton(Modifier.size(width = it.dp, height = 32.dp), KhazenaRadius.pill) }
                }
                KhazenaSkeletonText(type.bodyS.copy(lineHeight = 20.sp), listOf(320.dp, 300.dp, 260.dp))
            }
        }
    }
}
