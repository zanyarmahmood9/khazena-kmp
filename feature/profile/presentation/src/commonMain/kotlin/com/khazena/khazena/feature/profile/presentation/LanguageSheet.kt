package com.khazena.khazena.feature.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaBottomSheet
import com.khazena.khazena.core.designsystem.component.KhazenaButton
import com.khazena.khazena.core.designsystem.component.KhazenaInfoBanner
import com.khazena.khazena.core.designsystem.component.KhazenaRadio
import com.khazena.khazena.core.designsystem.component.KhazenaSheetTitle
import com.khazena.khazena.core.domain.language.AppLanguage
import khazena.core.presentation.generated.resources.Res as CoreRes
import khazena.core.presentation.generated.resources.close
import khazena.feature.profile.presentation.generated.resources.Res
import khazena.feature.profile.presentation.generated.resources.app_language
import khazena.feature.profile.presentation.generated.resources.apply
import khazena.feature.profile.presentation.generated.resources.language_ar_description
import khazena.feature.profile.presentation.generated.resources.language_ckb_description
import khazena.feature.profile.presentation.generated.resources.language_direction_note
import khazena.feature.profile.presentation.generated.resources.language_en_description
import khazena.feature.profile.presentation.generated.resources.language_sheet_subtitle
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LanguageSheet(state: ProfileState, onAction: (ProfileAction) -> Unit) {
    KhazenaBottomSheet(onDismiss = { onAction(ProfileAction.OnDismissLanguageSheet) }) {
        LanguageSheetContent(state, onAction)
    }
}

@Composable
private fun ColumnScope.LanguageSheetContent(state: ProfileState, onAction: (ProfileAction) -> Unit) {
    KhazenaSheetTitle(
        title = stringResource(Res.string.app_language),
        closeLabel = stringResource(CoreRes.string.close),
        onClose = { onAction(ProfileAction.OnDismissLanguageSheet) },
        subtitle = stringResource(Res.string.language_sheet_subtitle),
    )
    Column(Modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AppLanguage.entries.forEach { language ->
            LanguageOption(language, selected = language == state.pickedLanguage) {
                onAction(ProfileAction.OnPickLanguage(language))
            }
        }
    }
    KhazenaInfoBanner(stringResource(Res.string.language_direction_note))
    KhazenaButton(
        text = stringResource(Res.string.apply),
        onClick = { onAction(ProfileAction.OnApplyLanguage) },
        modifier = Modifier.fillMaxWidth(),
        enabled = state.pickedLanguage != null,
    )
}

@Composable
private fun LanguageOption(language: AppLanguage, selected: Boolean, onClick: () -> Unit) {
    val colors = KhazenaTheme.colors
    val violetTint = colors.blockViolet.copy(alpha = 0.1f)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(KhazenaRadius.card)
            .background(if (selected) violetTint else Color.Transparent)
            .border(1.5.dp, if (selected) colors.borderSelected else Color.Transparent, KhazenaRadius.card)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(44.dp)
                .background(if (selected) colors.blockViolet.copy(alpha = 0.22f) else colors.surfaceTile, KhazenaRadius.sm),
            contentAlignment = Alignment.Center,
        ) {
            Text(language.badge, style = KhazenaTheme.typography.labelMSemiBold, color = colors.textPrimary)
        }
        Column(Modifier.weight(1f)) {
            Text(language.nativeName, style = KhazenaTheme.typography.labelXLBold, color = colors.textPrimary)
            Text(language.description(), style = KhazenaTheme.typography.bodyS, color = colors.textSecondary)
        }
        Text(
            if (language.isRtl) "RTL" else "LTR",
            style = KhazenaTheme.typography.monoTagS,
            color = colors.textSecondary,
            modifier = Modifier.background(colors.surfaceTile, KhazenaRadius.pill).padding(horizontal = 10.dp, vertical = 4.dp),
        )
        KhazenaRadio(selected = selected, onClick = onClick)
    }
}

private val AppLanguage.badge: String
    get() = when (this) {
        AppLanguage.CKB -> "KU"
        AppLanguage.AR -> "AR"
        AppLanguage.EN -> "EN"
    }

@Composable
private fun AppLanguage.description(): String = stringResource(
    when (this) {
        AppLanguage.CKB -> Res.string.language_ckb_description
        AppLanguage.AR -> Res.string.language_ar_description
        AppLanguage.EN -> Res.string.language_en_description
    },
)

@Preview(widthDp = 390)
@Composable
private fun LanguageSheetPreview() = KhazenaTheme {
    Column(
        Modifier.background(KhazenaTheme.colors.surfaceBackground).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LanguageSheetContent(ProfileState(language = AppLanguage.EN, pickedLanguage = AppLanguage.EN), {})
    }
}
