package com.khazena.khazena.feature.business.reports.presentation.di

import com.khazena.khazena.feature.business.reports.presentation.ReportsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val reportsPresentationModule = module {
    viewModelOf(::ReportsViewModel)
}
