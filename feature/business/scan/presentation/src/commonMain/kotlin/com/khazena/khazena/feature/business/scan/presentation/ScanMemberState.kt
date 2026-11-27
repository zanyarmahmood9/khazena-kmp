package com.khazena.khazena.feature.business.scan.presentation

import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.business.scan.domain.RecentScan

data class ScanMemberState(
    val content: ContentState<List<RecentScan>> = ContentState(),
)
