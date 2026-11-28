package com.khazena.khazena.feature.home.data

import com.khazena.khazena.feature.home.domain.HomeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeDataModule = module {
    singleOf(::FakeHomeRepository) bind HomeRepository::class
}
