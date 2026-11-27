package com.khazena.khazena.core.data.di

import com.khazena.khazena.core.data.city.SupabaseCityDataSource
import com.khazena.khazena.core.data.network.SupabaseClientFactory
import com.khazena.khazena.core.domain.city.CityRemoteDataSource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.auth.auth
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single { SupabaseClientFactory.create() }
    single { get<SupabaseClient>().postgrest }
    single { get<SupabaseClient>().auth }
    single { get<SupabaseClient>().storage }
    singleOf(::SupabaseCityDataSource) bind CityRemoteDataSource::class
}
