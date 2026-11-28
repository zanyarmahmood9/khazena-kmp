package com.khazena.khazena.feature.business.home.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result

data class ScanRecord(val id: String, val memberName: String, val whenLabel: LocalizedText, val savedIqd: Long)

data class BusinessDashboard(
    val scansToday: Int,
    val newMembersThisWeek: Int,
    val savingsGivenIqd: Long,
    val recentScans: List<ScanRecord>,
)

interface BusinessHomeRepository {
    suspend fun getDashboard(): Result<BusinessDashboard, DataError>
}
