package com.khazena.khazena.core.data.di

import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSUserDomainMask
import platform.Foundation.languageCode
import platform.Foundation.preferredLanguages

@OptIn(ExperimentalForeignApi::class)
internal actual val platformDataModule = module {
    single {
        val documents = NSFileManager.defaultManager.URLForDirectory(NSDocumentDirectory, NSUserDomainMask, null, true, null)
        createDataStore(requireNotNull(documents?.path) + "/" + PREFERENCES_FILE)
    }
    factory(named(SYSTEM_LANGUAGE)) {
        // The in-app override writes AppleLanguages to the app domain; drop it to read the phone's language.
        NSUserDefaults.standardUserDefaults.removeObjectForKey("AppleLanguages")
        (NSLocale.preferredLanguages.firstOrNull() as? String)?.let { NSLocale(it).languageCode }.orEmpty()
    }
}
