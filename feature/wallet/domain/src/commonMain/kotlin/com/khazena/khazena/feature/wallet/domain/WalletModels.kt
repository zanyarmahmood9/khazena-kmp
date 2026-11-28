package com.khazena.khazena.feature.wallet.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result

data class Saving(val id: String, val merchant: LocalizedText, val whenLabel: LocalizedText, val savedIqd: Long)

data class Wallet(
    val memberName: String,
    val planName: LocalizedText,
    val validUntil: String,
    val totalSavedIqd: Long,
    val visits: Int,
    val history: List<Saving>,
)

interface WalletRepository {
    suspend fun getWallet(): Result<Wallet, DataError>
}
