package com.khazena.khazena.feature.home.presentation.di

import com.khazena.khazena.feature.home.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule = module {
    viewModelOf(::HomeViewModel)
}
