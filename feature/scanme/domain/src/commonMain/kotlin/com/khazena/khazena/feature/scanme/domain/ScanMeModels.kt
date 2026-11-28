package com.khazena.khazena.feature.scanme.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result

data class MemberCode(val code: String, val memberName: String, val planName: LocalizedText, val validForSeconds: Int)

interface ScanMeRepository {
    suspend fun getMemberCode(): Result<MemberCode?, DataError>
}
