package com.khazena.khazena.feature.onboarding.presentation

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaRtlPreview
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaTag
import com.khazena.khazena.core.designsystem.component.TagType
import khazena.feature.onboarding.presentation.generated.resources.Res
import khazena.feature.onboarding.presentation.generated.resources.continue_as_business
import khazena.feature.onboarding.presentation.generated.resources.continue_as_customer
import khazena.feature.onboarding.presentation.generated.resources.have_account
import khazena.feature.onboarding.presentation.generated.resources.side_body
import khazena.feature.onboarding.presentation.generated.resources.side_business_body
import khazena.feature.onboarding.presentation.generated.resources.side_business_kicker
import khazena.feature.onboarding.presentation.generated.resources.side_business_tags
import khazena.feature.onboarding.presentation.generated.resources.side_business_title
import khazena.feature.onboarding.presentation.generated.resources.side_customer_body
import khazena.feature.onboarding.presentation.generated.resources.side_customer_kicker
import khazena.feature.onboarding.presentation.generated.resources.side_customer_tags
import khazena.feature.onboarding.presentation.generated.resources.side_customer_title
import khazena.feature.onboarding.presentation.generated.resources.side_kicker
import khazena.feature.onboarding.presentation.generated.resources.side_title
import khazena.feature.onboarding.presentation.generated.resources.sign_in
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.feature.onboarding.presentation.side.ChooseSideAction
import com.khazena.khazena.feature.onboarding.presentation.side.ChooseSideEvent
import com.khazena.khazena.feature.onboarding.presentation.side.ChooseSideState
import com.khazena.khazena.feature.onboarding.presentation.side.ChooseSideViewModel
import com.khazena.khazena.feature.onboarding.presentation.side.Side
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
        OnboardingTopBar(step = ONBOARDING_STEPS - 1, onSkip = { onAction(ChooseSideAction.OnSkipClick) })
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = KhazenaSpacing.screenMargin, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(stringResource(Res.string.side_kicker).uppercase(), style = KhazenaTheme.typography.monoKicker, color = colors.textSecondary)
            Text(
                stringResource(Res.string.side_title),
                style = KhazenaTheme.typography.displayL,
                color = colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Text(stringResource(Res.string.side_body), style = KhazenaTheme.typography.bodyM, color = colors.textSecondary)
            Column(Modifier.selectableGroup().padding(top = 4.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SideCard(
                    selected = side == Side.Customer,
                    onSelect = { onAction(ChooseSideAction.OnSideSelected(Side.Customer)) },
                    container = colors.blockSun,
                    content = colors.textPrimary,
                    iconContainer = colors.blockInk,
                    iconTint = colors.blockSun,
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
                    icon = KhazenaIcons.Storefront,
                    kicker = stringResource(Res.string.side_business_kicker),
                    title = stringResource(Res.string.side_business_title),
                    body = stringResource(Res.string.side_business_body),
                    tags = stringArrayResource(Res.array.side_business_tags),
                )
            }
        }
        Column(
            Modifier.padding(horizontal = KhazenaSpacing.screenMargin).padding(top = 12.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            KhazenaButton(
                text = stringResource(if (side == Side.Customer) Res.string.continue_as_customer else Res.string.continue_as_business),
                onClick = { onAction(ChooseSideAction.OnContinueClick) },
                modifier = Modifier.fillMaxWidth(),
                icon = KhazenaIcons.ArrowRight,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(stringResource(Res.string.have_account), style = KhazenaTheme.typography.bodyS, color = colors.textSecondary)
                Text(
                    stringResource(Res.string.sign_in),
                    style = KhazenaTheme.typography.labelSMedium.copy(textDecoration = TextDecoration.Underline),
                    color = colors.textPrimary,
                    modifier = Modifier.clickable(role = Role.Button) { onAction(ChooseSideAction.OnSignInClick) },
                )
            }
        }
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
    icon: DrawableResource,
    kicker: String,
    title: String,
    body: String,
    tags: List<String>,
) {
    val colors = KhazenaTheme.colors
    Column(
        Modifier
            .fillMaxWidth()
            .background(container, KhazenaRadius.hero)
            .border(2.5.dp, if (selected) colors.borderSelected else Color.Transparent, KhazenaRadius.hero)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onSelect)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(Modifier.size(48.dp).background(iconContainer, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(painterResource(icon), null, Modifier.size(24.dp), tint = iconTint)
            }
            Spacer(Modifier.weight(1f))
            SelectionMark(selected, content)
        }
        Spacer(Modifier.size(4.dp))
        Text(kicker.uppercase(), style = KhazenaTheme.typography.monoKicker, color = content)
        Text(title, style = KhazenaTheme.typography.headingH3, color = content)
        Text(body, style = KhazenaTheme.typography.bodyM, color = content)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            tags.forEach { KhazenaTag(it, type = TagType.OnColourBlock) }
        }
    }
}

@Composable
private fun SelectionMark(selected: Boolean, outline: Color) {
    val colors = KhazenaTheme.colors
    if (selected) {
        Box(Modifier.size(28.dp).background(colors.blockInk, CircleShape), contentAlignment = Alignment.Center) {
            Icon(painterResource(KhazenaIcons.Check), null, Modifier.size(14.dp), tint = colors.textOnDark)
        }
    } else {
        Box(Modifier.size(28.dp).border(1.5.dp, outline, CircleShape))
    }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ChooseSidePreview() = KhazenaTheme { ChooseSideScreen(ChooseSideState()) {} }

@Preview(name = "RTL", widthDp = 390, heightDp = 844)
@Composable
private fun ChooseSideRtlPreview() = KhazenaRtlPreview { ChooseSideScreen(ChooseSideState(Side.Business)) {} }
