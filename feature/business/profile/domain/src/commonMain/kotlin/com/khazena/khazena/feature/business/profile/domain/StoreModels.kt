package com.khazena.khazena.feature.business.profile.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result

data class Store(
    val name: LocalizedText,
    val sector: LocalizedText,
    val address: LocalizedText,
    val hours: LocalizedText,
    val phone: String,
    val memberDiscountPercent: Int,
)

interface StoreRepository {
    suspend fun getStore(): Result<Store?, DataError>
}
