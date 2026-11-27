package com.khazena.khazena.feature.business.profile.presentation

import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.business.profile.domain.Store

data class StoreState(
    val content: ContentState<Store?> = ContentState(),
)
