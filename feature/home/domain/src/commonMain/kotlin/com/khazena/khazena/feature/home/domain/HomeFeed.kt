package com.khazena.khazena.feature.home.domain

import com.khazena.khazena.core.domain.LocalizedText

data class HomeFeed(
    val member: MemberSummary,
    val city: LocalizedText,
    val unreadNotifications: Int,
    val categories: List<HomeCategory>,
    val featuredOffer: FeaturedOffer?,
    val topDeals: List<TopDeal>,
    val nearby: List<NearbyPlace>,
) {
    val isEmpty: Boolean get() = categories.isEmpty() && featuredOffer == null && topDeals.isEmpty() && nearby.isEmpty()
}

data class MemberSummary(
    val firstName: String,
    val initials: String,
    val plan: LocalizedText,
    val period: LocalizedText,
    val savedIqd: Long,
    val visits: Int,
    val stores: Int,
)

/** [imageKey] names a bundled demo image until images come from the API. */
data class HomeCategory(
    val id: String,
    val name: LocalizedText,
    val imageKey: String,
    val maxDiscountPercent: Int? = null,
    val placeCount: Int = 0,
)

data class CategoryDirectory(val city: LocalizedText, val categories: List<HomeCategory>) {
    val totalPlaces: Int get() = categories.sumOf { it.placeCount }
}

data class FeaturedOffer(
    val id: String,
    val title: LocalizedText,
    val merchant: LocalizedText,
    val note: LocalizedText,
    val discountPercent: Int,
    val imageKey: String,
    val sponsored: Boolean,
    val storeId: String,
)

/** [area] is shown instead of the rating in the meta line; the rating then moves to a chip on the photo. */
data class TopDeal(
    val id: String,
    val name: LocalizedText,
    val kind: LocalizedText,
    val rating: Double,
    val discountPercent: Int,
    val imageKey: String,
    val area: LocalizedText? = null,
)

data class NearbyPlace(
    val id: String,
    val name: LocalizedText,
    val kind: LocalizedText,
    val distanceKm: Double,
    val discountPercent: Int,
    val imageKey: String,
)
