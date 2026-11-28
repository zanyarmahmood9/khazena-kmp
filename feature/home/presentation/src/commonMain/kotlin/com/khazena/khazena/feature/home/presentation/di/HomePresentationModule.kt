package com.khazena.khazena.feature.home.presentation.di

import com.khazena.khazena.feature.home.presentation.HomeViewModel
import com.khazena.khazena.feature.home.presentation.categories.CategoriesViewModel
import com.khazena.khazena.feature.home.presentation.categories.CategoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule = module {
    viewModel { HomeViewModel(get()) }
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::CategoryViewModel)
}
