package com.khazena.khazena.feature.business.scan.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result

data class RecentScan(val id: String, val memberName: String, val whenLabel: LocalizedText, val savedIqd: Long)

interface ScanMemberRepository {
    suspend fun getRecentScans(): Result<List<RecentScan>, DataError>
}
