package com.khazena.khazena.feature.home.presentation

import com.khazena.khazena.core.presentation.state.ContentState
import com.khazena.khazena.feature.home.domain.HomeFeed

enum class Daypart { Morning, Afternoon, Evening }

data class HomeState(
    val feed: ContentState<HomeFeed> = ContentState(),
    val daypart: Daypart = Daypart.Morning,
)

internal fun daypartAt(hour: Int): Daypart = when (hour) {
    in 5..11 -> Daypart.Morning
    in 12..16 -> Daypart.Afternoon
    else -> Daypart.Evening
}
