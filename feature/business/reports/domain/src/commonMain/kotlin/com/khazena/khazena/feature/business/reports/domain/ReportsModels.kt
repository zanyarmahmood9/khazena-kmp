package com.khazena.khazena.feature.business.reports.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result

data class WeeklyReport(
    val visitsByDay: List<Int>,
    val newMembers: Int,
    val savingsGivenIqd: Long,
    val returningPercent: Int,
)

interface ReportsRepository {
    suspend fun getWeeklyReport(): Result<WeeklyReport, DataError>
}
