package com.khazena.khazena.feature.home.data

import com.khazena.khazena.core.data.demo.DemoCatalog
import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.home.domain.Deal
import com.khazena.khazena.feature.home.domain.HomeRepository

class FakeHomeRepository(private val demo: DemoDataSettings) : HomeRepository {
    override suspend fun getNearbyDeals(): Result<List<Deal>, DataError> = demo.demoResult(emptyList()) {
        DemoCatalog.merchants
            .sortedByDescending { it.discountPercent }
            .map { Deal(it.id, it.name, it.category, it.discountPercent, it.distanceKm) }
    }
}
