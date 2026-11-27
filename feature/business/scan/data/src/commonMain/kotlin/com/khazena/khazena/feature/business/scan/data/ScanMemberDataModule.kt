package com.khazena.khazena.feature.business.scan.data

import com.khazena.khazena.feature.business.scan.domain.ScanMemberRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scanMemberDataModule = module {
    singleOf(::FakeScanMemberRepository) bind ScanMemberRepository::class
}
