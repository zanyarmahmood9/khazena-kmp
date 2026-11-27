package com.khazena.khazena.feature.business.home.data

import com.khazena.khazena.feature.business.home.domain.BusinessHomeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val businessHomeDataModule = module {
    singleOf(::FakeBusinessHomeRepository) bind BusinessHomeRepository::class
}
