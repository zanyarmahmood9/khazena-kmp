package com.khazena.khazena.core.data.di

import android.content.Context
import android.content.res.Resources
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal actual val platformDataModule = module {
    single { createDataStore(get<Context>().filesDir.resolve(PREFERENCES_FILE).absolutePath) }
    // Resources.getSystem() ignores the in-app Locale.setDefault override.
    factory(named(SYSTEM_LANGUAGE)) { Resources.getSystem().configuration.locales[0].language }
}
