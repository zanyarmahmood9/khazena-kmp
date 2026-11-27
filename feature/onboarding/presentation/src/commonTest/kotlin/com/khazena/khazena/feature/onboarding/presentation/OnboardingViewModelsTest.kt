package com.khazena.khazena.feature.onboarding.presentation

import androidx.lifecycle.SavedStateHandle
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroAction
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroEvent
import com.khazena.khazena.feature.onboarding.presentation.intro.IntroViewModel
import com.khazena.khazena.feature.onboarding.presentation.side.ChooseSideAction
import com.khazena.khazena.feature.onboarding.presentation.side.ChooseSideEvent
import com.khazena.khazena.feature.onboarding.presentation.side.ChooseSideViewModel
import com.khazena.khazena.feature.onboarding.presentation.side.Side
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
        assertEquals(listOf<IntroEvent>(IntroEvent.NavigateToChooseSide), events)
    }

    @Test
    fun introRestoresPageFromSavedState() = runTest {
        val viewModel = IntroViewModel(SavedStateHandle(mapOf("page" to 1)))
        assertEquals(1, viewModel.state.value.page)
    }

    @Test
    fun chooseSideContinuesWithSelectedSide() = runTest {
        val viewModel = ChooseSideViewModel(SavedStateHandle())
        val events = mutableListOf<ChooseSideEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect {} }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.events.collect { events += it } }

        viewModel.onAction(ChooseSideAction.OnContinueClick)
        viewModel.onAction(ChooseSideAction.OnSideSelected(Side.Business))
        assertEquals(Side.Business, viewModel.state.value.side)
        viewModel.onAction(ChooseSideAction.OnContinueClick)
        viewModel.onAction(ChooseSideAction.OnSkipClick)

        assertEquals(
            listOf(ChooseSideEvent.ContinueAsCustomer, ChooseSideEvent.ContinueAsBusiness, ChooseSideEvent.ContinueAsCustomer),
            events,
        )
    }
}
