package com.khazena.khazena.feature.scanme.data

import com.khazena.khazena.feature.scanme.domain.ScanMeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scanMeDataModule = module {
    singleOf(::FakeScanMeRepository) bind ScanMeRepository::class
}
