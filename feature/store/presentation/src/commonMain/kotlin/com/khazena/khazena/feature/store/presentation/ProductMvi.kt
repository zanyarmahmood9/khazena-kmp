package com.khazena.khazena.feature.store.presentation

import com.khazena.khazena.core.presentation.UiText
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.store.domain.MenuItem
import com.khazena.khazena.feature.store.domain.Store

data class ProductState(
    val store: ContentState<Store> = ContentState(),
    val itemId: String = "",
    val isSaved: Boolean = false,
)

internal fun Store.item(id: String): MenuItem? = menu.firstOrNull { it.id == id }

// "More from …": the rest of the menu, popular picks first.
internal fun Store.moreThan(id: String): List<MenuItem> = menu.filter { it.id != id }.sortedByDescending { it.popular }

sealed interface ProductAction {
    data class OnMoreItemClick(val id: String) : ProductAction
    data object OnBackClick : ProductAction
    data object OnShareClick : ProductAction
    data object OnSaveClick : ProductAction
    data object OnStoreClick : ProductAction
    data object OnSeeMenuClick : ProductAction
    data object OnScanMeClick : ProductAction
    data object OnRetry : ProductAction
    data object OnRefresh : ProductAction
}

sealed interface ProductEvent {
    data class ShowMessage(val message: UiText) : ProductEvent
}
