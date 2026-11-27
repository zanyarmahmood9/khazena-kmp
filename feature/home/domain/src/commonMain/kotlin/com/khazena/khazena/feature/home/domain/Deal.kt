package com.khazena.khazena.feature.home.domain

import com.khazena.khazena.core.domain.LocalizedText

data class Deal(
    val id: String,
    val merchant: LocalizedText,
    val category: LocalizedText,
    val discountPercent: Int,
    val distanceKm: Double,
)
