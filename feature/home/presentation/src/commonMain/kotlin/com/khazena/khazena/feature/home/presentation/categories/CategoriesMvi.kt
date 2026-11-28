package com.khazena.khazena.feature.home.presentation.categories

import com.khazena.khazena.core.presentation.UiText
import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.home.domain.CategoryDirectory

data class CategoriesState(
    val query: String = "",
    val directory: ContentState<CategoryDirectory> = ContentState(),
)

sealed interface CategoriesAction {
    data class OnQueryChange(val query: String) : CategoriesAction
    data class OnCategoryClick(val id: String) : CategoriesAction
    data object OnBackClick : CategoriesAction
    data object OnRetry : CategoriesAction
    data object OnRefresh : CategoriesAction
}

sealed interface CategoriesEvent {
    data class ShowMessage(val message: UiText) : CategoriesEvent
}
