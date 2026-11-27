package com.khazena.khazena.feature.home.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result

interface HomeRepository {
    suspend fun getNearbyDeals(): Result<List<Deal>, DataError>
}
