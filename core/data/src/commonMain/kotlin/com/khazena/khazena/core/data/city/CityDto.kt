package com.khazena.khazena.core.data.city

import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.city.City
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: String,
    @SerialName("name_ckb") val nameCkb: String,
    @SerialName("name_en") val nameEn: String,
    @SerialName("name_ar") val nameAr: String,
    val slug: String,
)

fun CityDto.toCity() = City(
    id = id,
    name = LocalizedText(ckb = nameCkb, en = nameEn, ar = nameAr),
    slug = slug,
)
