package com.khazena.khazena.feature.business.services.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result

data class Service(
    val id: String,
    val name: LocalizedText,
    val description: LocalizedText,
    val priceIqd: Long,
    val memberPriceIqd: Long,
)

interface ServicesRepository {
    suspend fun getServices(): Result<List<Service>, DataError>
}
