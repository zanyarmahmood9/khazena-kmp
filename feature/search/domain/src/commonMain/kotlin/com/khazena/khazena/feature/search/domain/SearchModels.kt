package com.khazena.khazena.feature.search.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result

data class Place(
    val id: String,
    val name: LocalizedText,
    val category: LocalizedText,
    val discountPercent: Int,
    val distanceKm: Double,
)

interface SearchRepository {
    suspend fun search(query: String): Result<List<Place>, DataError>
}
