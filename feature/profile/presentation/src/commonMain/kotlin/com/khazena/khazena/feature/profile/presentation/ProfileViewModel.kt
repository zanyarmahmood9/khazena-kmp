package com.khazena.khazena.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.domain.demo.DemoDataMode
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.core.domain.language.LanguageRepository
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.profile.domain.ProfileRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val languageRepository: LanguageRepository,
    profileRepository: ProfileRepository,
    private val demoDataSettings: DemoDataSettings,
) : ViewModel() {

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    private val sheet = MutableStateFlow(ProfileState())

    private val profile = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it == null },
        onRefreshFailed = { eventChannel.send(ProfileEvent.ShowMessage(it)) },
        fetch = profileRepository::getProfile,
    )

    val state = combine(languageRepository.language, sheet, profile.state, demoDataSettings.mode) { language, sheet, profile, mode ->
        sheet.copy(
            profile = profile,
            language = language,
            pickedLanguage = sheet.pickedLanguage ?: language,
            demoMode = mode,
        )
    }
        .onStart { profile.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ProfileState())

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnRetry -> profile.load()
            ProfileAction.OnRefresh -> profile.refresh()
            ProfileAction.OnLanguageClick -> sheet.update { it.copy(isLanguageSheetOpen = true, pickedLanguage = null) }
            is ProfileAction.OnPickLanguage -> sheet.update { it.copy(pickedLanguage = action.language) }
            ProfileAction.OnDismissLanguageSheet -> sheet.update { it.copy(isLanguageSheetOpen = false, pickedLanguage = null) }
            ProfileAction.OnApplyLanguage -> {
                val picked = state.value.pickedLanguage ?: return
                sheet.update { it.copy(isLanguageSheetOpen = false, pickedLanguage = null) }
                viewModelScope.launch { languageRepository.setLanguage(picked) }
            }
            ProfileAction.OnDemoModeClick -> {
                val modes = DemoDataMode.entries
                demoDataSettings.setMode(modes[(demoDataSettings.mode.value.ordinal + 1) % modes.size])
            }
            ProfileAction.OnSignInClick, ProfileAction.OnGalleryClick -> Unit
        }
    }
}
