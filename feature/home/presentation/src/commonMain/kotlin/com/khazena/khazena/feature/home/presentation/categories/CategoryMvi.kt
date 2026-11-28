package com.khazena.khazena.feature.home.presentation.categories

import com.khazena.khazena.core.presentation.UiText
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.home.domain.CategoryDetail
import com.khazena.khazena.feature.home.domain.CategoryPlace
import com.khazena.khazena.feature.home.domain.Subcategory

enum class PlaceSort { Discount, Distance, Rating }

data class CategoryState(
    val detail: ContentState<CategoryDetail> = ContentState(),
    val subcategoryId: String? = null,
    val sort: PlaceSort = PlaceSort.Discount,
    val isSortSheetOpen: Boolean = false,
    val isSearchOpen: Boolean = false,
    val query: String = "",
)

internal fun CategoryDetail.placesFor(subcategoryId: String?, sort: PlaceSort): List<CategoryPlace> {
    val filtered = if (subcategoryId == null) places else places.filter { it.subcategoryId == subcategoryId }
    return when (sort) {
        PlaceSort.Discount -> filtered.sortedByDescending { it.discountPercent }
        PlaceSort.Distance -> filtered.sortedBy { it.distanceKm }
        PlaceSort.Rating -> filtered.sortedByDescending { it.rating }
    }
}

// Only sub-categories that have places get a chip; an empty one would just lead to "No places here yet".
internal val CategoryDetail.subcategoriesWithPlaces: List<Subcategory>
    get() = subcategories.filter { sub -> places.any { it.subcategoryId == sub.id } }

internal fun CategoryDetail.countFor(subcategoryId: String?): Int =
    subcategories.firstOrNull { it.id == subcategoryId }?.placeCount ?: placeCount

sealed interface CategoryAction {
    data class OnSubcategoryClick(val id: String?) : CategoryAction
    data class OnPlaceClick(val id: String) : CategoryAction
    data class OnPickSort(val sort: PlaceSort) : CategoryAction
    data object OnSortClick : CategoryAction
    data object OnDismissSort : CategoryAction
    data object OnBackClick : CategoryAction
    data object OnSearchClick : CategoryAction
    data object OnCloseSearch : CategoryAction
    data class OnQueryChange(val query: String) : CategoryAction
    data object OnRetry : CategoryAction
    data object OnRefresh : CategoryAction
}

sealed interface CategoryEvent {
    data class ShowMessage(val message: UiText) : CategoryEvent
}
