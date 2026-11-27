package com.khazena.khazena.feature.profile.presentation

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataMode
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.core.domain.language.AppLanguage
import com.khazena.khazena.core.domain.language.LanguageRepository
import com.khazena.khazena.core.presentation.state.LoadState
import com.khazena.khazena.feature.profile.domain.ProfileRepository
import com.khazena.khazena.feature.profile.domain.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private class FakeLanguageRepository(initial: AppLanguage) : LanguageRepository {
        override val language = MutableStateFlow(initial)
        override suspend fun setLanguage(language: AppLanguage) {
            this.language.value = language
        }
    }

    private class FakeDemoSettings : DemoDataSettings {
        override val mode = MutableStateFlow(DemoDataMode.Normal)
        override fun setMode(mode: DemoDataMode) {
            this.mode.value = mode
        }
    }

    private class FakeProfileRepository : ProfileRepository {
        var result: Result<UserProfile?, DataError> = Result.Success(
            UserProfile("Bandy Kamal", "+964", LocalizedText("س", "Sulaymaniyah", "س"), null),
        )
        override suspend fun getProfile() = result
    }

    private val languages = FakeLanguageRepository(AppLanguage.EN)
    private val profiles = FakeProfileRepository()
    private val demo = FakeDemoSettings()
    private lateinit var viewModel: ProfileViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = ProfileViewModel(languages, profiles, demo)
    }

    @AfterTest
    fun tearDown() = Dispatchers.resetMain()

    private fun kotlinx.coroutines.test.TestScope.collectState() =
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect {} }

    @Test
    fun loadsProfileIntoDataState() = runTest {
        collectState()
        assertIs<LoadState.Data<UserProfile?>>(viewModel.state.value.profile.content)
    }

    @Test
    fun signedOutProfileIsEmptyState() = runTest {
        profiles.result = Result.Success(null)
        collectState()
        assertEquals(LoadState.Empty, viewModel.state.value.profile.content)
    }

    @Test
    fun failedLoadIsErrorStateAndRetryRecovers() = runTest {
        profiles.result = Result.Error(DataError.Network.NO_INTERNET)
        collectState()
        assertIs<LoadState.Error>(viewModel.state.value.profile.content)

        profiles.result = Result.Success(UserProfile("Lana", "+964", LocalizedText("", "", ""), null))
        viewModel.onAction(ProfileAction.OnRetry)
        assertIs<LoadState.Data<UserProfile?>>(viewModel.state.value.profile.content)
    }

    @Test
    fun failedRefreshKeepsDataAndSendsMessage() = runTest {
        collectState()
        val events = mutableListOf<ProfileEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.events.collect { events += it } }

        profiles.result = Result.Error(DataError.Network.NO_INTERNET)
        viewModel.onAction(ProfileAction.OnRefresh)

        assertIs<LoadState.Data<UserProfile?>>(viewModel.state.value.profile.content)
        assertFalse(viewModel.state.value.profile.isRefreshing)
        assertIs<ProfileEvent.ShowMessage>(events.single())
    }

    @Test
    fun applyingPickedLanguageSavesItAndClosesSheet() = runTest {
        collectState()
        viewModel.onAction(ProfileAction.OnLanguageClick)
        viewModel.onAction(ProfileAction.OnPickLanguage(AppLanguage.CKB))
        viewModel.onAction(ProfileAction.OnApplyLanguage)

        assertEquals(AppLanguage.CKB, languages.language.value)
        assertFalse(viewModel.state.value.isLanguageSheetOpen)
    }

    @Test
    fun dismissingKeepsLanguage() = runTest {
        collectState()
        viewModel.onAction(ProfileAction.OnLanguageClick)
        assertTrue(viewModel.state.value.isLanguageSheetOpen)
        viewModel.onAction(ProfileAction.OnPickLanguage(AppLanguage.AR))
        viewModel.onAction(ProfileAction.OnDismissLanguageSheet)

        assertEquals(AppLanguage.EN, languages.language.value)
        assertFalse(viewModel.state.value.isLanguageSheetOpen)
    }

    @Test
    fun demoModeCyclesThroughModes() = runTest {
        collectState()
        viewModel.onAction(ProfileAction.OnDemoModeClick)
        assertEquals(DemoDataMode.Empty, viewModel.state.value.demoMode)
        viewModel.onAction(ProfileAction.OnDemoModeClick)
        assertEquals(DemoDataMode.Error, viewModel.state.value.demoMode)
    }
}
