package com.khazena.khazena.feature.business.services.presentation.di

import com.khazena.khazena.feature.business.services.presentation.ServicesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val servicesPresentationModule = module {
    viewModelOf(::ServicesViewModel)
}
