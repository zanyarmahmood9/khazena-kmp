package com.khazena.khazena.core.data.onboarding

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.khazena.khazena.core.domain.onboarding.AppSide
import com.khazena.khazena.core.domain.onboarding.OnboardingProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DataStoreOnboardingRepositoryTest {

    private class InMemoryDataStore : DataStore<Preferences> {
        private val state = MutableStateFlow(emptyPreferences())
        override val data: Flow<Preferences> = state
        override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences =
            transform(state.value).also { state.value = it }
    }

    @Test
    fun freshInstallHasNothingDone() = runTest {
        assertEquals(OnboardingProgress(), DataStoreOnboardingRepository(InMemoryDataStore()).progress.first())
    }

    @Test
    fun finishingIntroIsRemembered() = runTest {
        val repository = DataStoreOnboardingRepository(InMemoryDataStore())
        repository.completeIntro()
        assertEquals(OnboardingProgress(introDone = true), repository.progress.first())
    }

    @Test
    fun enteringASideMarksIntroDoneAndLatestSideWins() = runTest {
        val repository = DataStoreOnboardingRepository(InMemoryDataStore())
        repository.enterSide(AppSide.Business)
        repository.enterSide(AppSide.Customer)
        assertEquals(OnboardingProgress(introDone = true, side = AppSide.Customer), repository.progress.first())
    }
}
