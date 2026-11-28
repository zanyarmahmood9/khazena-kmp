package com.khazena.khazena.core.data.onboarding

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.khazena.khazena.core.domain.onboarding.AppSide
import com.khazena.khazena.core.domain.onboarding.OnboardingProgress
import com.khazena.khazena.core.domain.onboarding.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class DataStoreOnboardingRepository(private val dataStore: DataStore<Preferences>) : OnboardingRepository {

    override val progress: Flow<OnboardingProgress> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs ->
            OnboardingProgress(
                introDone = prefs[INTRO_DONE_KEY] ?: false,
                side = AppSide.entries.firstOrNull { it.name == prefs[SIDE_KEY] },
            )
        }
        .distinctUntilChanged()

    override suspend fun completeIntro() {
        dataStore.edit { it[INTRO_DONE_KEY] = true }
    }

    override suspend fun enterSide(side: AppSide) {
        dataStore.edit {
            it[INTRO_DONE_KEY] = true
            it[SIDE_KEY] = side.name
        }
    }

    private companion object {
        val INTRO_DONE_KEY = booleanPreferencesKey("onboarding_intro_done")
        val SIDE_KEY = stringPreferencesKey("onboarding_side")
    }
}
