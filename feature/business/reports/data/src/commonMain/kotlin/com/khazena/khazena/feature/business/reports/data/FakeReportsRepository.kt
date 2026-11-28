package com.khazena.khazena.feature.business.reports.data

import com.khazena.khazena.core.data.demo.DemoCatalog
import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.business.reports.domain.ReportsRepository
import com.khazena.khazena.feature.business.reports.domain.WeeklyReport

class FakeReportsRepository(private val demo: DemoDataSettings) : ReportsRepository {
    override suspend fun getWeeklyReport(): Result<WeeklyReport, DataError> = demo.demoResult(
        empty = WeeklyReport(List(7) { 0 }, 0, 0, 0),
    ) {
        WeeklyReport(listOf(12, 18, 9, 22, 30, 26, 14), newMembers = 9, savingsGivenIqd = 128_000, returningPercent = 64)
    }
}
