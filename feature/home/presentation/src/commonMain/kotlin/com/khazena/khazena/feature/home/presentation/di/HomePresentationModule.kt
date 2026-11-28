package com.khazena.khazena.feature.home.presentation.di

import com.khazena.khazena.feature.home.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homePresentationModule = module {
    viewModel { HomeViewModel(get()) }
}
