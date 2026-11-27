package com.khazena.khazena.feature.profile.presentation

import com.khazena.khazena.core.domain.demo.DemoDataMode
import com.khazena.khazena.core.domain.language.AppLanguage
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.profile.domain.UserProfile

data class ProfileState(
    val profile: ContentState<UserProfile?> = ContentState(),
    val language: AppLanguage? = null,
    val pickedLanguage: AppLanguage? = null,
    val isLanguageSheetOpen: Boolean = false,
    val demoMode: DemoDataMode = DemoDataMode.Normal,
)
