package com.khazena.khazena.feature.search.data

import com.khazena.khazena.feature.search.domain.SearchRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val searchDataModule = module {
    singleOf(::FakeSearchRepository) bind SearchRepository::class
}
