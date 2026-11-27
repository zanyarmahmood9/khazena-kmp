package com.khazena.khazena.feature.business.reports.presentation

import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.business.reports.domain.WeeklyReport

data class ReportsState(
    val content: ContentState<WeeklyReport> = ContentState(),
)
