package com.khazena.khazena.feature.business.services.presentation

import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.business.services.domain.Service

data class ServicesState(
    val content: ContentState<List<Service>> = ContentState(),
)
