package com.khazena.khazena.feature.business.services.presentation

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
import khazena.feature.business.services.presentation.generated.resources.Res
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaStateCard
import com.khazena.khazena.core.designsystem.component.StateTone

import com.khazena.khazena.core.designsystem.component.KhazenaMenuItem
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.presentation.formatDiscount
import com.khazena.khazena.core.presentation.formatIqd
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.core.presentation.placeholderPhoto
import com.khazena.khazena.feature.business.services.domain.Service
import khazena.feature.business.services.presentation.generated.resources.*

@Composable
fun ServicesRoot(
    viewModel: ServicesViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ServicesEvent.ShowMessage -> toast.show(event.message)
        }
    }
    ServicesScreen(
        state = state,
        toastState = toast,
        onAction = { action ->
            when (action) {
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun ServicesScreen(state: ServicesState, toastState: MessageToastState, onAction: (ServicesAction) -> Unit) {
    StateScreen(
        state = state.content,
        onRefresh = { onAction(ServicesAction.OnRefresh) },
        onRetry = { onAction(ServicesAction.OnRetry) },
        toastState = toastState,
        header = {
            Text(
                stringResource(Res.string.services_title),
                style = KhazenaTheme.typography.headingH1,
                color = KhazenaTheme.colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Text(stringResource(Res.string.services_subtitle), style = KhazenaTheme.typography.bodyM, color = KhazenaTheme.colors.textSecondary)
        },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.ListBullets,
                badge = stringResource(Res.string.services_empty_badge),
                title = stringResource(Res.string.services_empty_title),
                body = stringResource(Res.string.services_empty_body),
                tone = StateTone.Sun,
            )
        },
    ) { data ->
        data.forEachIndexed { index, service ->
            KhazenaMenuItem(
                photo = placeholderPhoto(index),
                title = service.name.localized(),
                description = service.description.localized(),
                price = formatIqd(service.memberPriceIqd),
                originalPrice = formatIqd(service.priceIqd),
                savingLabel = formatDiscount((100 - service.memberPriceIqd * 100 / service.priceIqd).toInt()),
                modifier = Modifier.revealOnAppear(index),
            )
        }
    }
}

@Preview(name = "Data", widthDp = 390, heightDp = 844)
@Composable
private fun ServicesDataPreview() = KhazenaTheme {
    ServicesScreen(ServicesState(ContentState(LoadState.Data(listOf(Service("1", LocalizedText("کوبە", "Kubba", "كبة"), LocalizedText("چوار", "Four pieces", "أربع"), 6_000, 4_200))))), rememberMessageToastState()) {}
}

@Preview(name = "Empty", widthDp = 390, heightDp = 844)
@Composable
private fun ServicesEmptyPreview() = KhazenaTheme {
    ServicesScreen(ServicesState(ContentState(LoadState.Empty)), rememberMessageToastState()) {}
}
