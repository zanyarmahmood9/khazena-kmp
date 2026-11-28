package com.khazena.khazena.feature.business.profile.presentation.di

import com.khazena.khazena.feature.business.profile.presentation.StoreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val storePresentationModule = module {
    viewModelOf(::StoreViewModel)
}
