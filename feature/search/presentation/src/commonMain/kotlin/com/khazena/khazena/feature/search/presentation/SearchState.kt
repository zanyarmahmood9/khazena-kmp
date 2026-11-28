package com.khazena.khazena.feature.search.presentation

import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.search.domain.Place

data class SearchState(
    val query: String = "",
    val results: ContentState<List<Place>> = ContentState(),
)
