package com.khazena.khazena.feature.business.home.data

import com.khazena.khazena.core.data.demo.DemoCatalog
import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.business.home.domain.BusinessDashboard
import com.khazena.khazena.feature.business.home.domain.BusinessHomeRepository
import com.khazena.khazena.feature.business.home.domain.ScanRecord

class FakeBusinessHomeRepository(private val demo: DemoDataSettings) : BusinessHomeRepository {
    override suspend fun getDashboard(): Result<BusinessDashboard, DataError> = demo.demoResult(
        empty = BusinessDashboard(0, 0, 0, emptyList()),
    ) {
        val scans = DemoCatalog.members.mapIndexed { index, name ->
            ScanRecord("r$index", name, DemoCatalog.ago(index * 18 + 4), (index + 3) * 1_500L)
        }
        BusinessDashboard(scansToday = 42, newMembersThisWeek = 9, savingsGivenIqd = 128_000, recentScans = scans)
    }
}
