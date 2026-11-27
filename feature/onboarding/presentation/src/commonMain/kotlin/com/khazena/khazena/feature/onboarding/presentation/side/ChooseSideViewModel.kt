package com.khazena.khazena.feature.onboarding.presentation.side

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val SIDE_KEY = "side"

class ChooseSideViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val eventChannel = Channel<ChooseSideEvent>()
    val events = eventChannel.receiveAsFlow()

    val state = savedStateHandle.getStateFlow(SIDE_KEY, Side.Customer.name)
        .map { ChooseSideState(Side.valueOf(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ChooseSideState())

    fun onAction(action: ChooseSideAction) {
        when (action) {
            is ChooseSideAction.OnSideSelected -> savedStateHandle[SIDE_KEY] = action.side.name
            ChooseSideAction.OnContinueClick -> send(
                if (state.value.side == Side.Customer) ChooseSideEvent.ContinueAsCustomer else ChooseSideEvent.ContinueAsBusiness,
            )
            ChooseSideAction.OnSkipClick -> send(ChooseSideEvent.ContinueAsCustomer)
            ChooseSideAction.OnSignInClick -> send(ChooseSideEvent.OpenSignIn)
        }
    }

    private fun send(event: ChooseSideEvent) {
        viewModelScope.launch { eventChannel.send(event) }
    }
}
