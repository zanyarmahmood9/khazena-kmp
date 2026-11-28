package com.khazena.khazena.feature.store.presentation

import com.khazena.khazena.core.presentation.UiText
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.store.domain.MenuItem
import com.khazena.khazena.feature.store.domain.Review
import com.khazena.khazena.feature.store.domain.Store
import com.khazena.khazena.feature.store.domain.StoreContact

enum class StoreTab { Services, Reviews, Info }

enum class ReviewSort { Recent, Highest, Lowest, WithPhotos }

/** The menu filter row: everything, the popular picks, or one menu section by id. */
sealed interface MenuFilter {
    data object All : MenuFilter
    data object Popular : MenuFilter
    data class Section(val id: String) : MenuFilter
}

data class StoreState(
    val store: ContentState<Store> = ContentState(),
    val tab: StoreTab = StoreTab.Services,
    val menuQuery: String = "",
    val menuFilter: MenuFilter = MenuFilter.All,
    val reviewSort: ReviewSort = ReviewSort.Recent,
    val isSaved: Boolean = false,
)

internal fun Store.itemsFor(filter: MenuFilter): List<MenuItem> = when (filter) {
    MenuFilter.All -> menu
    MenuFilter.Popular -> menu.filter { it.popular }
    is MenuFilter.Section -> menu.filter { it.sectionId == filter.id }
}

internal fun Store.reviewsSortedBy(sort: ReviewSort): List<Review> = when (sort) {
    ReviewSort.Recent -> reviews.sortedBy { it.daysAgo }
    ReviewSort.Highest -> reviews.sortedWith(compareByDescending<Review> { it.rating }.thenBy { it.daysAgo })
    ReviewSort.Lowest -> reviews.sortedWith(compareBy<Review> { it.rating }.thenBy { it.daysAgo })
    ReviewSort.WithPhotos -> reviews.filter { it.hasPhotos }.sortedBy { it.daysAgo }
}

sealed interface StoreAction {
    data class OnTabSelect(val tab: StoreTab) : StoreAction
    data class OnMenuQueryChange(val query: String) : StoreAction
    data class OnMenuFilterClick(val filter: MenuFilter) : StoreAction
    data class OnMenuItemClick(val id: String) : StoreAction
    data class OnReviewSortClick(val sort: ReviewSort) : StoreAction
    data class OnContactClick(val contact: StoreContact) : StoreAction
    data object OnBackClick : StoreAction
    data object OnShareClick : StoreAction
    data object OnSaveClick : StoreAction
    data object OnPhotosClick : StoreAction
    data object OnWriteReviewClick : StoreAction
    data object OnShowAllReviewsClick : StoreAction
    data object OnDirectionsClick : StoreAction
    data object OnCopyAddressClick : StoreAction
    data object OnSeeContactClick : StoreAction
    data object OnScanMeClick : StoreAction
    data object OnRetry : StoreAction
    data object OnRefresh : StoreAction
}

sealed interface StoreEvent {
    data class ShowMessage(val message: UiText) : StoreEvent
}
