package com.khazena.khazena.feature.chooseside.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaRtlPreview
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaLogo
import com.khazena.khazena.core.designsystem.component.KhazenaTag
import com.khazena.khazena.core.designsystem.component.KhazenaWordmark
import com.khazena.khazena.core.designsystem.component.TagType
import com.khazena.khazena.core.presentation.ObserveAsEvents
import khazena.feature.chooseside.presentation.generated.resources.Res
import khazena.feature.chooseside.presentation.generated.resources.continue_as_business
import khazena.feature.chooseside.presentation.generated.resources.continue_as_customer
import khazena.feature.chooseside.presentation.generated.resources.have_account
import khazena.feature.chooseside.presentation.generated.resources.side_body
import khazena.feature.chooseside.presentation.generated.resources.side_business_body
import khazena.feature.chooseside.presentation.generated.resources.side_business_kicker
import khazena.feature.chooseside.presentation.generated.resources.side_business_tags
import khazena.feature.chooseside.presentation.generated.resources.side_business_title
import khazena.feature.chooseside.presentation.generated.resources.side_customer_body
import khazena.feature.chooseside.presentation.generated.resources.side_customer_kicker
import khazena.feature.chooseside.presentation.generated.resources.side_customer_tags
import khazena.feature.chooseside.presentation.generated.resources.side_customer_title
import khazena.feature.chooseside.presentation.generated.resources.side_kicker
import khazena.feature.chooseside.presentation.generated.resources.side_title
import khazena.feature.chooseside.presentation.generated.resources.sign_in
import khazena.feature.chooseside.presentation.generated.resources.skip
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ChooseSideRoot(
    onContinueAsCustomer: () -> Unit,
    onContinueAsBusiness: () -> Unit,
    onSignIn: () -> Unit,
    viewModel: ChooseSideViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ChooseSideEvent.ContinueAsCustomer -> onContinueAsCustomer()
            ChooseSideEvent.ContinueAsBusiness -> onContinueAsBusiness()
            ChooseSideEvent.OpenSignIn -> onSignIn()
        }
    }
    ChooseSideScreen(state, viewModel::onAction)
}

@Composable
internal fun ChooseSideScreen(state: ChooseSideState, onAction: (ChooseSideAction) -> Unit) {
    val side = state.side
    val colors = KhazenaTheme.colors
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.surfaceBackground)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        TopBar(onSkip = { onAction(ChooseSideAction.OnSkipClick) })
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = KhazenaSpacing.screenMargin).padding(top = 8.dp, bottom = 12.dp),
        ) {
            Text(stringResource(Res.string.side_kicker).uppercase(), style = KhazenaTheme.typography.monoKicker, color = colors.textSecondary)
            Spacer(Modifier.height(7.dp))
            Text(
                stringResource(Res.string.side_title),
                style = KhazenaTheme.typography.displayL,
                color = colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Spacer(Modifier.height(10.dp))
            Text(
                stringResource(Res.string.side_body),
                style = KhazenaTheme.typography.bodyM,
                color = colors.textSecondary,
                modifier = Modifier.padding(end = 20.dp),
            )
            Spacer(Modifier.height(16.dp))
            Column(Modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SideCard(
                    selected = side == Side.Customer,
                    onSelect = { onAction(ChooseSideAction.OnSideSelected(Side.Customer)) },
                    container = colors.blockSun,
                    content = colors.textPrimary,
                    iconContainer = colors.blockInk,
                    iconTint = colors.blockSun,
                    tagText = colors.textPrimary,
                    icon = KhazenaIcons.ShoppingBag,
                    kicker = stringResource(Res.string.side_customer_kicker),
                    title = stringResource(Res.string.side_customer_title),
                    body = stringResource(Res.string.side_customer_body),
                    tags = stringArrayResource(Res.array.side_customer_tags),
                )
                SideCard(
                    selected = side == Side.Business,
                    onSelect = { onAction(ChooseSideAction.OnSideSelected(Side.Business)) },
                    container = colors.blockViolet,
                    content = colors.textOnViolet,
                    iconContainer = colors.surfaceCard,
                    iconTint = colors.blockViolet,
                    tagText = colors.blockViolet,
                    icon = KhazenaIcons.Storefront,
                    kicker = stringResource(Res.string.side_business_kicker),
                    title = stringResource(Res.string.side_business_title),
                    body = stringResource(Res.string.side_business_body),
                    tags = stringArrayResource(Res.array.side_business_tags),
                )
            }
        }
        Column(
            Modifier.padding(horizontal = KhazenaSpacing.screenMargin).padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            KhazenaButton(
                text = stringResource(if (side == Side.Customer) Res.string.continue_as_customer else Res.string.continue_as_business),
                onClick = { onAction(ChooseSideAction.OnContinueClick) },
                modifier = Modifier.fillMaxWidth(),
                icon = KhazenaIcons.ArrowRight,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(Res.string.have_account),
                    style = KhazenaTheme.typography.labelMSemiBold.copy(fontWeight = FontWeight.Medium),
                    color = colors.textSecondary,
                )
                Text(
                    stringResource(Res.string.sign_in),
                    style = KhazenaTheme.typography.labelMSemiBold.copy(textDecoration = TextDecoration.Underline),
                    color = colors.textPrimary,
                    modifier = Modifier.clickable(role = Role.Button) { onAction(ChooseSideAction.OnSignInClick) },
                )
            }
        }
    }
}

@Composable
private fun TopBar(onSkip: () -> Unit) {
    val colors = KhazenaTheme.colors
    Box(Modifier.fillMaxWidth().height(56.dp).padding(horizontal = KhazenaSpacing.screenMargin)) {
        Row(
            Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painterResource(KhazenaLogo.MarkOnPaper), null, Modifier.width(16.dp).height(18.dp))
            KhazenaWordmark(KhazenaTheme.typography.wordmarkS, colors.textPrimary)
        }
        Text(
            stringResource(Res.string.skip),
            style = KhazenaTheme.typography.labelMSemiBold.copy(textDecoration = TextDecoration.Underline),
            color = colors.textPrimary,
            modifier = Modifier.align(Alignment.CenterEnd).clickable(role = Role.Button, onClick = onSkip).padding(8.dp),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SideCard(
    selected: Boolean,
    onSelect: () -> Unit,
    container: Color,
    content: Color,
    iconContainer: Color,
    iconTint: Color,
    tagText: Color,
    icon: DrawableResource,
    kicker: String,
    title: String,
    body: String,
    tags: List<String>,
) {
    val colors = KhazenaTheme.colors
    val border by animateColorAsState(if (selected) colors.borderSelected else Color.Transparent, tween(durationMillis = 300))
    val scale by animateFloatAsState(if (selected) 1f else 0.98f, spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessLow))
    Column(
        Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(KhazenaRadius.hero)
            .background(container)
            .border(3.dp, border, KhazenaRadius.hero)
            // Ripple clipped to the card's corners and tinted with its own content colour instead of the default grey.
            .selectable(selected = selected, interactionSource = null, indication = ripple(color = content), role = Role.RadioButton, onClick = onSelect)
            .padding(20.dp),
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(Modifier.size(48.dp).background(iconContainer, KhazenaRadius.sm), contentAlignment = Alignment.Center) {
                Icon(painterResource(icon), null, Modifier.size(24.dp), tint = iconTint)
            }
            Spacer(Modifier.weight(1f))
            SelectionMark(selected, content)
        }
        Spacer(Modifier.height(14.dp))
        Text(kicker.uppercase(), style = KhazenaTheme.typography.monoKicker, color = content.copy(alpha = 0.75f))
        Spacer(Modifier.height(4.dp))
        Text(title, style = KhazenaTheme.typography.headingH2Tight, color = content)
        Spacer(Modifier.height(10.dp))
        Text(
            body,
            style = KhazenaTheme.typography.labelSMedium.copy(fontSize = 14.sp, lineHeight = 1.3.em),
            color = content.copy(alpha = 0.85f),
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            tags.forEach { KhazenaTag(it, type = TagType.OnColourBlock, contentColor = tagText) }
        }
    }
}

@Composable
private fun SelectionMark(selected: Boolean, outline: Color) {
    val colors = KhazenaTheme.colors
    val fill by animateFloatAsState(if (selected) 1f else 0f, spring(dampingRatio = 0.7f, stiffness = Spring.StiffnessMediumLow))
    Box(Modifier.size(28.dp).border(2.dp, outline.copy(alpha = 0.7f * (1f - fill)), CircleShape), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .matchParentSize()
                .graphicsLayer {
                    scaleX = fill
                    scaleY = fill
                    alpha = fill.coerceIn(0f, 1f)
                }
                .background(colors.blockInk, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(painterResource(KhazenaIcons.Check), null, Modifier.size(14.dp), tint = colors.textOnDark)
        }
    }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ChooseSidePreview() = KhazenaTheme { ChooseSideScreen(ChooseSideState()) {} }

@Preview(name = "RTL", widthDp = 390, heightDp = 844)
@Composable
private fun ChooseSideRtlPreview() = KhazenaRtlPreview { ChooseSideScreen(ChooseSideState(Side.Business)) {} }
