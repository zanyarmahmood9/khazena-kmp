package com.khazena.khazena

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.domain.language.AppLanguage
import com.khazena.khazena.core.domain.language.LanguageRepository
import com.khazena.khazena.core.domain.onboarding.AppSide
import com.khazena.khazena.core.domain.onboarding.OnboardingProgress
import com.khazena.khazena.core.domain.onboarding.OnboardingRepository
import com.khazena.khazena.core.domain.session.SessionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AppState(
    val language: AppLanguage? = null,
    val isSignedIn: Boolean = false,
    val onboarding: OnboardingProgress? = null,
)

class AppViewModel(
    languageRepository: LanguageRepository,
    sessionRepository: SessionRepository,
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {

    val state = combine(
        languageRepository.language,
        sessionRepository.isSignedIn.onStart { emit(false) },
        onboardingRepository.progress,
        ::AppState,
    ).stateIn(viewModelScope, SharingStarted.Eagerly, AppState())

    fun onIntroCompleted() {
        viewModelScope.launch { onboardingRepository.completeIntro() }
    }

    fun onSideEntered(side: AppSide) {
        viewModelScope.launch { onboardingRepository.enterSide(side) }
    }
}
