package com.khazena.khazena.core.domain.city

interface CityRepository {
    /** Active cities (`is_active`) in display order (`sort_order`). */
    suspend fun getCities(): Result<List<City>>
}
