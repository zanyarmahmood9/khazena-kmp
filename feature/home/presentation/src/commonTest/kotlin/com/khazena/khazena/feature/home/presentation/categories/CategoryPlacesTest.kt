package com.khazena.khazena.feature.home.presentation.categories

import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.feature.home.domain.CategoryDetail
import com.khazena.khazena.feature.home.domain.CategoryPlace
import com.khazena.khazena.feature.home.domain.Subcategory
import kotlin.test.Test
import kotlin.test.assertEquals

class CategoryPlacesTest {
    private val text = LocalizedText("", "", "")
    private fun place(id: String, sub: String, km: Double, rating: Double, discount: Int) =
        CategoryPlace(id, text, text, text, km, rating, 1, discount, "", sub)

    private val detail = CategoryDetail(
        id = "food", name = text, description = text, imageKey = "", placeCount = 128, topDiscountPercent = 50, openNow = 1,
        subcategories = listOf(Subcategory("cafes", text, 31), Subcategory("bakeries", text, 12)),
        places = listOf(place("a", "restaurants", 1.4, 4.8, 30), place("b", "cafes", 0.6, 4.5, 10), place("c", "cafes", 0.9, 4.9, 20)),
    )

    @Test
    fun sortsAllPlacesByTheChosenOrder() {
        assertEquals(listOf("a", "c", "b"), detail.placesFor(null, PlaceSort.Discount).map { it.id })
        assertEquals(listOf("b", "c", "a"), detail.placesFor(null, PlaceSort.Distance).map { it.id })
        assertEquals(listOf("c", "a", "b"), detail.placesFor(null, PlaceSort.Rating).map { it.id })
    }

    @Test
    fun onlySubcategoriesWithPlacesGetAChip() {
        assertEquals(listOf("cafes"), detail.subcategoriesWithPlaces.map { it.id })
    }

    @Test
    fun subcategoryFiltersPlacesAndCount() {
        assertEquals(listOf("c", "b"), detail.placesFor("cafes", PlaceSort.Discount).map { it.id })
        assertEquals(31, detail.countFor("cafes"))
        assertEquals(128, detail.countFor(null))
    }
}
