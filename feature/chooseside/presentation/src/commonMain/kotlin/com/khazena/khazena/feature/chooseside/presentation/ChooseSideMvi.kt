package com.khazena.khazena.feature.chooseside.presentation

enum class Side { Customer, Business }

data class ChooseSideState(val side: Side = Side.Customer)

sealed interface ChooseSideAction {
    data class OnSideSelected(val side: Side) : ChooseSideAction
    data object OnContinueClick : ChooseSideAction
    data object OnSkipClick : ChooseSideAction
    data object OnSignInClick : ChooseSideAction
}

sealed interface ChooseSideEvent {
    data object ContinueAsCustomer : ChooseSideEvent
    data object ContinueAsBusiness : ChooseSideEvent
    data object OpenSignIn : ChooseSideEvent
}
