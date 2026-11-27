package com.khazena.khazena.feature.profile.data

import com.khazena.khazena.feature.profile.domain.ProfileRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val profileDataModule = module {
    singleOf(::FakeProfileRepository) bind ProfileRepository::class
}
