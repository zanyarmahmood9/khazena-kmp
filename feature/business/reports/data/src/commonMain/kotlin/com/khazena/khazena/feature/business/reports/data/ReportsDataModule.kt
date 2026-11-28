package com.khazena.khazena.feature.business.reports.data

import com.khazena.khazena.feature.business.reports.domain.ReportsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val reportsDataModule = module {
    singleOf(::FakeReportsRepository) bind ReportsRepository::class
}
