package com.khazena.khazena.core.presentation.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.key
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.khazena.khazena.core.domain.language.AppLanguage

@Composable
fun ProvideAppLanguage(language: AppLanguage, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppLocale provides language.code,
        LocalLayoutDirection provides if (language.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr,
    ) {
        // Compose resources cache strings per locale; a new group reloads them.
        key(language) { content() }
    }
}
