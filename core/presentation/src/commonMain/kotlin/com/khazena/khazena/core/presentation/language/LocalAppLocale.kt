package com.khazena.khazena.core.presentation.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue

expect object LocalAppLocale {
    val current: String @Composable get

    @Composable
    infix fun provides(value: String): ProvidedValue<*>
}
