package com.khazena.khazena.core.presentation.language

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

actual object LocalAppLocale {
    actual val current: String
        @Composable get() = LocalConfiguration.current.locales[0].language

    @Composable
    actual infix fun provides(value: String): ProvidedValue<*> {
        val locale = Locale.forLanguageTag(value)
        Locale.setDefault(locale)
        val configuration = Configuration(LocalConfiguration.current).apply { setLocale(locale) }
        val resources = LocalContext.current.resources
        @Suppress("DEPRECATION")
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return LocalConfiguration provides configuration
    }
}
