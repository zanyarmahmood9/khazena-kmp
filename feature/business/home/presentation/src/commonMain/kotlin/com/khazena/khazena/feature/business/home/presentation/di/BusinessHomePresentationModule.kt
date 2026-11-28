package com.khazena.khazena.feature.business.home.presentation.di

import com.khazena.khazena.feature.business.home.presentation.BusinessHomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val businessHomePresentationModule = module {
    viewModelOf(::BusinessHomeViewModel)
}
