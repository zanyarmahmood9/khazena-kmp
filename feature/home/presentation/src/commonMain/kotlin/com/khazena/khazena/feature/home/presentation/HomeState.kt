package com.khazena.khazena.feature.home.presentation

import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.home.domain.Deal

data class HomeState(
    val deals: ContentState<List<Deal>> = ContentState(),
)
