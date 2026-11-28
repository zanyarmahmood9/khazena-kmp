package com.khazena.khazena.feature.home.domain

import com.khazena.khazena.core.domain.LocalizedText

data class CategoryDetail(
    val id: String,
    val name: LocalizedText,
    val description: LocalizedText,
    val imageKey: String,
    val placeCount: Int,
    val topDiscountPercent: Int,
    val openNow: Int,
    val subcategories: List<Subcategory>,
    val places: List<CategoryPlace>,
)

data class Subcategory(val id: String, val name: LocalizedText, val placeCount: Int)

data class CategoryPlace(
    val id: String,
    val name: LocalizedText,
    val kind: LocalizedText,
    val area: LocalizedText,
    val distanceKm: Double,
    val rating: Double,
    val reviewCount: Int,
    val discountPercent: Int,
    val imageKey: String,
    val subcategoryId: String,
)
