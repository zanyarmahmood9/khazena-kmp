package com.khazena.khazena.feature.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaAvatar
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaShimmer
import com.khazena.khazena.core.designsystem.component.KhazenaSkeleton
import com.khazena.khazena.core.designsystem.component.KhazenaStateCard
import com.khazena.khazena.core.designsystem.component.KhazenaTag
import com.khazena.khazena.core.designsystem.component.TagType
import com.khazena.khazena.core.designsystem.revealOnAppear
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.demo.DemoDataMode
import com.khazena.khazena.core.domain.language.AppLanguage
import com.khazena.khazena.core.presentation.ObserveAsEvents
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.core.presentation.state.LoadState
import com.khazena.khazena.core.presentation.state.MessageToastState
import com.khazena.khazena.core.presentation.state.StateScreen
import com.khazena.khazena.core.presentation.state.rememberMessageToastState
import com.khazena.khazena.feature.profile.domain.UserProfile
import khazena.core.presentation.generated.resources.Res as CoreRes
import khazena.core.presentation.generated.resources.loading
import khazena.core.presentation.generated.resources.tab_profile
import khazena.feature.profile.presentation.generated.resources.Res
import khazena.feature.profile.presentation.generated.resources.app_language
import khazena.feature.profile.presentation.generated.resources.component_gallery
import khazena.feature.profile.presentation.generated.resources.demo_data
import khazena.feature.profile.presentation.generated.resources.profile_signed_out_action
import khazena.feature.profile.presentation.generated.resources.profile_signed_out_badge
import khazena.feature.profile.presentation.generated.resources.profile_signed_out_body
import khazena.feature.profile.presentation.generated.resources.profile_signed_out_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileRoot(
    onSignIn: () -> Unit,
    onOpenGallery: (() -> Unit)?,
    viewModel: ProfileViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = rememberMessageToastState()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ProfileEvent.ShowMessage -> toast.show(event.message)
        }
    }
    ProfileScreen(
        state = state,
        toastState = toast,
        isDebug = onOpenGallery != null,
        onAction = { action ->
            when (action) {
                ProfileAction.OnSignInClick -> onSignIn()
                ProfileAction.OnGalleryClick -> onOpenGallery?.invoke()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    toastState: MessageToastState,
    isDebug: Boolean,
    onAction: (ProfileAction) -> Unit,
) {
    StateScreen(
        state = state.profile,
        onRefresh = { onAction(ProfileAction.OnRefresh) },
        onRetry = { onAction(ProfileAction.OnRetry) },
        toastState = toastState,
        header = {
            Text(
                stringResource(CoreRes.string.tab_profile),
                style = KhazenaTheme.typography.headingH1,
                color = KhazenaTheme.colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
        },
        loading = {
            KhazenaShimmer(stringResource(CoreRes.string.loading)) {
                KhazenaSkeleton(Modifier.fillMaxWidth().height(132.dp), KhazenaRadius.card)
            }
        },
        empty = {
            KhazenaStateCard(
                icon = KhazenaIcons.User,
                badge = stringResource(Res.string.profile_signed_out_badge),
                title = stringResource(Res.string.profile_signed_out_title),
                body = stringResource(Res.string.profile_signed_out_body),
                actionLabel = stringResource(Res.string.profile_signed_out_action),
                onAction = { onAction(ProfileAction.OnSignInClick) },
            )
        },
        footer = {
            SettingsRow(
                title = stringResource(Res.string.app_language),
                value = state.language?.nativeName,
                onClick = { onAction(ProfileAction.OnLanguageClick) },
            )
            if (isDebug) {
                SettingsRow(stringResource(Res.string.demo_data), state.demoMode.name) { onAction(ProfileAction.OnDemoModeClick) }
                SettingsRow(stringResource(Res.string.component_gallery), null) { onAction(ProfileAction.OnGalleryClick) }
            }
        },
    ) { profile ->
        if (profile != null) ProfileCard(profile, Modifier.revealOnAppear(0))
    }
    if (state.isLanguageSheetOpen) {
        LanguageSheet(state, onAction)
    }
}

@Composable
private fun ProfileCard(profile: UserProfile, modifier: Modifier) {
    val colors = KhazenaTheme.colors
    Column(
        modifier.fillMaxWidth().background(colors.surfaceCard, KhazenaRadius.card).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
            KhazenaAvatar(profile.name.split(" ").mapNotNull { it.firstOrNull() }.take(2).joinToString(""), large = true)
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(profile.name, style = KhazenaTheme.typography.headingH4, color = colors.textPrimary)
                Text(profile.phone, style = KhazenaTheme.typography.bodyS, color = colors.textSecondary)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(KhazenaIcons.MapPin), null, Modifier.size(14.dp), tint = colors.iconMuted)
                    Text(profile.city.localized(), style = KhazenaTheme.typography.bodyS, color = colors.textSecondary)
                }
            }
        }
        profile.planName?.let { KhazenaTag(it.localized(), type = TagType.Discount) }
    }
}

@Composable
private fun SettingsRow(title: String, value: String?, onClick: () -> Unit) {
    val colors = KhazenaTheme.colors
    Row(
        Modifier
            .fillMaxWidth()
            .clip(KhazenaRadius.card)
            .background(colors.surfaceCard)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, style = KhazenaTheme.typography.labelLBold, color = colors.textPrimary, modifier = Modifier.weight(1f))
        if (value != null) Text(value, style = KhazenaTheme.typography.bodyM, color = colors.textSecondary)
        Icon(painterResource(KhazenaIcons.ArrowRight), null, Modifier.size(18.dp), tint = colors.iconMuted)
    }
}

internal val AppLanguage.nativeName: String
    get() = when (this) {
        AppLanguage.CKB -> "کوردی"
        AppLanguage.AR -> "العربية"
        AppLanguage.EN -> "English"
    }

private val previewProfile = UserProfile(
    "Bandy Kamal",
    "+964 750 482 1093",
    LocalizedText("سلێمانی", "Sulaymaniyah, Iraq", "السليمانية"),
    LocalizedText("ساڵانە", "1-Year Premium · Active", "سنوي"),
)

@Preview(name = "Data", widthDp = 390, heightDp = 700)
@Composable
private fun ProfileDataPreview() = KhazenaTheme {
    ProfileScreen(
        ProfileState(ContentState(LoadState.Data(previewProfile)), language = AppLanguage.EN, demoMode = DemoDataMode.Normal),
        rememberMessageToastState(),
        isDebug = true,
    ) {}
}

@Preview(name = "Signed out", widthDp = 390, heightDp = 844)
@Composable
private fun ProfileEmptyPreview() = KhazenaTheme {
    ProfileScreen(ProfileState(ContentState(LoadState.Empty), language = AppLanguage.EN), rememberMessageToastState(), isDebug = false) {}
}
