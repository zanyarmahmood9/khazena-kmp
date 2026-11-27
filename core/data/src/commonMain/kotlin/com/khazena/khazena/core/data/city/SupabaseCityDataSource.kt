package com.khazena.khazena.core.data.city

import com.khazena.khazena.core.data.network.safeCall
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.city.City
import com.khazena.khazena.core.domain.city.CityRemoteDataSource
import com.khazena.khazena.core.domain.map
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order

class SupabaseCityDataSource(private val postgrest: Postgrest) : CityRemoteDataSource {

    override suspend fun getActiveCities(): Result<List<City>, DataError.Network> = safeCall {
        postgrest.from("cities").select(Columns.list("id", "name_ckb", "name_en", "name_ar", "slug")) {
            filter { eq("is_active", true) }
            order("sort_order", Order.ASCENDING)
        }.decodeList<CityDto>()
    }.map { cities -> cities.map { it.toCity() } }
}
