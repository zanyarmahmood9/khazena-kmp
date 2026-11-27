package com.khazena.khazena.core.data.language

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.khazena.khazena.core.domain.language.AppLanguage
import com.khazena.khazena.core.domain.language.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class DataStoreLanguageRepository(
    private val dataStore: DataStore<Preferences>,
    private val systemLanguageCode: String,
) : LanguageRepository {

    override val language: Flow<AppLanguage> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs ->
            AppLanguage.fromCode(prefs[LANGUAGE_KEY])
                ?: AppLanguage.fromCode(systemLanguageCode)
                ?: AppLanguage.Default
        }
        .distinctUntilChanged()

    override suspend fun setLanguage(language: AppLanguage) {
        dataStore.edit { it[LANGUAGE_KEY] = language.code }
    }

    private companion object {
        val LANGUAGE_KEY = stringPreferencesKey("app_language")
    }
}
