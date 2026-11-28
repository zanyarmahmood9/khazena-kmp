package com.khazena.khazena.core.data.di

import com.khazena.khazena.core.data.demo.InMemoryDemoDataSettings
import com.khazena.khazena.core.data.language.DataStoreLanguageRepository
import com.khazena.khazena.core.data.onboarding.DataStoreOnboardingRepository
import com.khazena.khazena.core.data.session.LocalSessionRepository
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.core.domain.language.LanguageRepository
import com.khazena.khazena.core.domain.onboarding.OnboardingRepository
import com.khazena.khazena.core.domain.session.SessionRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal const val SYSTEM_LANGUAGE = "systemLanguage"

val coreDataModule = module {
    includes(platformDataModule)
    singleOf(::LocalSessionRepository) bind SessionRepository::class
    singleOf(::InMemoryDemoDataSettings) bind DemoDataSettings::class
    single<LanguageRepository> { DataStoreLanguageRepository(get(), get(named(SYSTEM_LANGUAGE))) }
    singleOf(::DataStoreOnboardingRepository) bind OnboardingRepository::class
}
