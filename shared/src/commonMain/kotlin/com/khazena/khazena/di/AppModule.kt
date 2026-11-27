package com.khazena.khazena.di

import com.khazena.khazena.AppViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::AppViewModel)
}
