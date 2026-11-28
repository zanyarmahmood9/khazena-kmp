package com.khazena.khazena.feature.store.presentation.di

import com.khazena.khazena.feature.store.presentation.ProductViewModel
import com.khazena.khazena.feature.store.presentation.StoreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val storeDetailPresentationModule = module {
    viewModelOf(::StoreViewModel)
    viewModelOf(::ProductViewModel)
}
