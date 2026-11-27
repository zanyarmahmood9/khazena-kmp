package com.khazena.khazena.feature.business.scan.presentation.di

import com.khazena.khazena.feature.business.scan.presentation.ScanMemberViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val scanMemberPresentationModule = module {
    viewModelOf(::ScanMemberViewModel)
}
