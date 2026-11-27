package com.khazena.khazena.feature.business.home.presentation

import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.business.home.domain.BusinessDashboard

data class BusinessHomeState(
    val content: ContentState<BusinessDashboard> = ContentState(),
)
