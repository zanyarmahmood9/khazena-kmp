package com.khazena.khazena.feature.chooseside.presentation

import androidx.lifecycle.SavedStateHandle
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
class ChooseSideViewModelTest {

    @BeforeTest
    fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun tearDown() = Dispatchers.resetMain()

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
