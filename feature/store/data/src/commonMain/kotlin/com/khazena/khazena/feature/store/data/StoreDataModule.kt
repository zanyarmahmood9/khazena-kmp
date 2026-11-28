package com.khazena.khazena.feature.store.data

import com.khazena.khazena.feature.store.domain.StoreRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val storeDetailDataModule = module {
    singleOf(::FakeStoreRepository) bind StoreRepository::class
}
