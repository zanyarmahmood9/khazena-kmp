package com.khazena.khazena.feature.store.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result

/** [coverKey], [logoKey] and item [MenuItem.imageKey] name bundled demo images until images come from the API. */
data class Store(
    val id: String,
    val name: LocalizedText,
    val categoryLine: LocalizedText,
    val cuisine: LocalizedText,
    val coverKey: String,
    val logoKey: String?,
    val photoCount: Int,
    val isOpenNow: Boolean,
    val hoursNote: LocalizedText,
    val rating: Double,
    val reviewCount: Int,
    val distanceKm: Double,
    val discountPercent: Int,
    val offerRules: List<LocalizedText>,
    val menuSections: List<MenuSection>,
    val menu: List<MenuItem>,
    /** Review counts for 5, 4, 3, 2 and 1 stars, in that order. */
    val ratingBreakdown: List<Int>,
    val reviews: List<Review>,
    val about: LocalizedText,
    val address: LocalizedText,
    /** Short form for one-line meta rows ("Salim St"). */
    val street: LocalizedText,
    val area: LocalizedText,
    val latitude: Double,
    val longitude: Double,
    val hours: List<OpeningHours>,
    val contacts: List<StoreContact>,
)

data class MenuSection(val id: String, val name: LocalizedText)

data class MenuItem(
    val id: String,
    val name: LocalizedText,
    val description: LocalizedText,
    val priceIqd: Long,
    val memberPriceIqd: Long,
    val imageKey: String,
    val sectionId: String,
    val popular: Boolean = false,
    val details: ProductDetails? = null,
)

/** The product page (Figma 02f); items without details show their short description instead. */
data class ProductDetails(
    val story: LocalizedText,
    val includes: List<LocalizedText>,
    val serves: Int? = null,
    val prepMinutes: Int? = null,
    val spicyOption: Boolean = false,
)

enum class AvatarTone { Violet, Sun, Green }

data class Review(
    val id: String,
    val author: String,
    val initials: String,
    val tone: AvatarTone,
    val age: LocalizedText,
    val daysAgo: Int,
    val rating: Int,
    val text: LocalizedText,
    val savedIqd: Long,
    val hasPhotos: Boolean = false,
)

/** Kurdish week order: Saturday first. */
enum class Weekday { Saturday, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday }

data class OpeningHours(val day: Weekday, val opens: String, val closes: String)

enum class ContactKind { Phone, Instagram, Facebook, TikTok }

/** [value] is what the row shows; [link] is where tapping it goes (tel:, https:). */
data class StoreContact(val kind: ContactKind, val value: String, val link: String)

interface StoreRepository {
    suspend fun getStore(id: String): Result<Store, DataError>
}
