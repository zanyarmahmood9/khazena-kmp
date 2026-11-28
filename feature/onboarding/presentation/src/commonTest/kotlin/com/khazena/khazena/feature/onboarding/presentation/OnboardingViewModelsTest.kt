package com.khazena.khazena.feature.onboarding.presentation

import androidx.lifecycle.SavedStateHandle
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroAction
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroEvent
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelsTest {

    @BeforeTest
    fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun introNextAdvancesPagesThenFinishes() = runTest {
        val viewModel = IntroViewModel(SavedStateHandle())
        val events = mutableListOf<IntroEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect {} }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.events.collect { events += it } }

        viewModel.onAction(IntroAction.OnNextClick)
        viewModel.onAction(IntroAction.OnNextClick)
        assertEquals(2, viewModel.state.value.page)
        assertEquals(emptyList(), events)

        viewModel.onAction(IntroAction.OnNextClick)
        assertEquals(listOf<IntroEvent>(IntroEvent.Finished), events)
    }

    @Test
    fun introBackGoesToPreviousPageAndStopsAtFirst() = runTest {
        val viewModel = IntroViewModel(SavedStateHandle(mapOf("page" to 2)))
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect {} }

        viewModel.onAction(IntroAction.OnBackClick)
        assertEquals(1, viewModel.state.value.page)
        viewModel.onAction(IntroAction.OnBackClick)
        viewModel.onAction(IntroAction.OnBackClick)
        assertEquals(0, viewModel.state.value.page)
    }

    @Test
    fun introRestoresPageFromSavedState() = runTest {
        val viewModel = IntroViewModel(SavedStateHandle(mapOf("page" to 1)))
        assertEquals(1, viewModel.state.value.page)
    }
}
