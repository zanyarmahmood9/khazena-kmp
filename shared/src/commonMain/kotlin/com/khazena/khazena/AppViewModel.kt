package com.khazena.khazena

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.domain.language.AppLanguage
import com.khazena.khazena.core.domain.language.LanguageRepository
import com.khazena.khazena.core.domain.session.SessionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

data class AppState(
    val language: AppLanguage? = null,
    val isSignedIn: Boolean = false,
)

class AppViewModel(
    languageRepository: LanguageRepository,
    sessionRepository: SessionRepository,
) : ViewModel() {

    val state = combine(
        languageRepository.language,
        sessionRepository.isSignedIn.onStart { emit(false) },
        ::AppState,
    ).stateIn(viewModelScope, SharingStarted.Eagerly, AppState())
}
