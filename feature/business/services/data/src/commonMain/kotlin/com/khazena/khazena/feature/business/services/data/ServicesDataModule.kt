package com.khazena.khazena.feature.business.services.data

import com.khazena.khazena.feature.business.services.domain.ServicesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val servicesDataModule = module {
    singleOf(::FakeServicesRepository) bind ServicesRepository::class
}
