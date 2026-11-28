package com.khazena.khazena.feature.business.profile.data

import com.khazena.khazena.feature.business.profile.domain.StoreRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val storeDataModule = module {
    singleOf(::FakeStoreRepository) bind StoreRepository::class
}
