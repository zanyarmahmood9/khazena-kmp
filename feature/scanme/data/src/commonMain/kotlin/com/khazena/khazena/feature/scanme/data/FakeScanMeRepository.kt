package com.khazena.khazena.feature.scanme.data

import com.khazena.khazena.core.data.demo.DemoCatalog
import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.scanme.domain.MemberCode
import com.khazena.khazena.feature.scanme.domain.ScanMeRepository
import kotlin.random.Random

class FakeScanMeRepository(private val demo: DemoDataSettings) : ScanMeRepository {
    private val plan = LocalizedText(ckb = "ئەندامێتی ساڵانە", en = "1-Year Premium", ar = "بريميوم سنوي")

    override suspend fun getMemberCode(): Result<MemberCode?, DataError> = demo.demoResult(empty = null) {
        val code = List(3) { Random.nextInt(1000, 9999).toString() }.joinToString(" · ")
        MemberCode(code, DemoCatalog.members.first(), plan, validForSeconds = 60)
    }
}
