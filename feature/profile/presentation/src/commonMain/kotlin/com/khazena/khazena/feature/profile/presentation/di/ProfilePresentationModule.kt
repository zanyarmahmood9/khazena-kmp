package com.khazena.khazena.feature.profile.presentation.di

import com.khazena.khazena.feature.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profilePresentationModule = module {
    viewModelOf(::ProfileViewModel)
}
