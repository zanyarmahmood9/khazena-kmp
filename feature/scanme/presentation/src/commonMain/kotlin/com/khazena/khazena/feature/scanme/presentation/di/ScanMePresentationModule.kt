package com.khazena.khazena.feature.scanme.presentation.di

import com.khazena.khazena.feature.scanme.presentation.ScanMeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val scanMePresentationModule = module {
    viewModelOf(::ScanMeViewModel)
}
