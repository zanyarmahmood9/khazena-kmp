package com.khazena.khazena.feature.onboarding.presentation.intro

data class IntroState(
    val page: Int = 0,
    val pageCount: Int = INTRO_PAGE_COUNT,
)

sealed interface IntroAction {
    data class OnPageSettled(val page: Int) : IntroAction
    data object OnNextClick : IntroAction
    data object OnBackClick : IntroAction
    data object OnSkipClick : IntroAction
}

sealed interface IntroEvent {
    data object Finished : IntroEvent
}

const val INTRO_PAGE_COUNT = 3
