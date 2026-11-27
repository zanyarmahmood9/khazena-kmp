package com.khazena.khazena.feature.profile.presentation

import com.khazena.khazena.core.domain.language.AppLanguage

sealed interface ProfileAction {
    data object OnRetry : ProfileAction
    data object OnRefresh : ProfileAction
    data object OnSignInClick : ProfileAction
    data object OnLanguageClick : ProfileAction
    data class OnPickLanguage(val language: AppLanguage) : ProfileAction
    data object OnApplyLanguage : ProfileAction
    data object OnDismissLanguageSheet : ProfileAction
    data object OnDemoModeClick : ProfileAction
    data object OnGalleryClick : ProfileAction
}
