package com.khazena.khazena.core.domain.city

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result

interface CityRemoteDataSource {
    suspend fun getActiveCities(): Result<List<City>, DataError.Network>
}
