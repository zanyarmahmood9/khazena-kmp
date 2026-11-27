package com.khazena.khazena.core.data.language

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.khazena.khazena.core.domain.language.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DataStoreLanguageRepositoryTest {

    private class InMemoryDataStore : DataStore<Preferences> {
        private val state = MutableStateFlow(emptyPreferences())
        override val data: Flow<Preferences> = state
        override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences =
            transform(state.value).also { state.value = it }
    }

    @Test
    fun supportedPhoneLanguageIsUsedUntilUserPicks() = runTest {
        assertEquals(AppLanguage.AR, DataStoreLanguageRepository(InMemoryDataStore(), "ar").language.first())
        assertEquals(AppLanguage.EN, DataStoreLanguageRepository(InMemoryDataStore(), "en").language.first())
    }

    @Test
    fun unsupportedPhoneLanguageFallsBackToKurdish() = runTest {
        assertEquals(AppLanguage.CKB, DataStoreLanguageRepository(InMemoryDataStore(), "de").language.first())
        assertEquals(AppLanguage.CKB, DataStoreLanguageRepository(InMemoryDataStore(), "").language.first())
    }

    @Test
    fun pickedLanguageOverridesPhoneLanguage() = runTest {
        val repository = DataStoreLanguageRepository(InMemoryDataStore(), "en")
        repository.setLanguage(AppLanguage.CKB)
        assertEquals(AppLanguage.CKB, repository.language.first())
    }
}
