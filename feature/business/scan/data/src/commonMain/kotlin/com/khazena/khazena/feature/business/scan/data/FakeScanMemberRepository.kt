package com.khazena.khazena.feature.business.scan.data

import com.khazena.khazena.core.data.demo.DemoCatalog
import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.business.scan.domain.RecentScan
import com.khazena.khazena.feature.business.scan.domain.ScanMemberRepository

class FakeScanMemberRepository(private val demo: DemoDataSettings) : ScanMemberRepository {
    override suspend fun getRecentScans(): Result<List<RecentScan>, DataError> = demo.demoResult(emptyList()) {
        DemoCatalog.members.take(3).mapIndexed { index, name ->
            RecentScan("t$index", name, DemoCatalog.ago(index * 25 + 2), (index + 2) * 2_000L)
        }
    }
}
