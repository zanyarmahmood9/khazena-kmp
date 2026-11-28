package com.khazena.khazena.feature.scanme.presentation

import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.scanme.domain.MemberCode

data class ScanMeState(
    val content: ContentState<MemberCode?> = ContentState(),
)
