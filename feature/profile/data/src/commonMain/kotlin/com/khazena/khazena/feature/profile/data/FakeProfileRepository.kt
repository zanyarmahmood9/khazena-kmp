package com.khazena.khazena.feature.profile.data

import com.khazena.khazena.core.data.demo.DemoCatalog
import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.profile.domain.ProfileRepository
import com.khazena.khazena.feature.profile.domain.UserProfile

class FakeProfileRepository(private val demo: DemoDataSettings) : ProfileRepository {
    override suspend fun getProfile(): Result<UserProfile?, DataError> = demo.demoResult(empty = null) {
        UserProfile(
            name = DemoCatalog.members.first(),
            phone = "+964 750 482 1093",
            city = LocalizedText(ckb = "سلێمانی، عێراق", en = "Sulaymaniyah, Iraq", ar = "السليمانية، العراق"),
            planName = LocalizedText(ckb = "ئەندامێتی ساڵانە · چالاک", en = "1-Year Premium · Active", ar = "بريميوم سنوي · نشط"),
        )
    }
}
