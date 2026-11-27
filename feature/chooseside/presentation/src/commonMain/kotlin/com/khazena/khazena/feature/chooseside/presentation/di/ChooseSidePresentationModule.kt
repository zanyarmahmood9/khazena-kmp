package com.khazena.khazena.feature.chooseside.presentation.di

import com.khazena.khazena.feature.chooseside.presentation.ChooseSideViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chooseSidePresentationModule = module {
    viewModelOf(::ChooseSideViewModel)
}
