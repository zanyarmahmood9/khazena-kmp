package com.khazena.khazena.core.presentation.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import platform.Foundation.NSUserDefaults

actual object LocalAppLocale {
    private const val APPLE_LANGUAGES = "AppleLanguages"
    private val local = staticCompositionLocalOf { "" }

    actual val current: String
        @Composable get() = local.current

    @Composable
    actual infix fun provides(value: String): ProvidedValue<*> {
        NSUserDefaults.standardUserDefaults.setObject(listOf(value), APPLE_LANGUAGES)
        return local provides value
    }
}
