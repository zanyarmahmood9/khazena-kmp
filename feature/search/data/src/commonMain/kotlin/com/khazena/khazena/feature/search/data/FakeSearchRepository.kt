package com.khazena.khazena.feature.search.data

import com.khazena.khazena.core.data.demo.DemoCatalog
import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.search.domain.Place
import com.khazena.khazena.feature.search.domain.SearchRepository

class FakeSearchRepository(private val demo: DemoDataSettings) : SearchRepository {
    override suspend fun search(query: String): Result<List<Place>, DataError> = demo.demoResult(emptyList()) {
        val term = query.trim()
        DemoCatalog.merchants
            .filter { term.isEmpty() || it.name.matches(term) || it.category.matches(term) }
            .map { Place(it.id, it.name, it.category, it.discountPercent, it.distanceKm) }
    }

    private fun LocalizedText.matches(term: String) =
        listOf(ckb, en, ar).any { it.contains(term, ignoreCase = true) }
}
