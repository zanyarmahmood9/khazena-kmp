package com.khazena.khazena.feature.business.services.data

import com.khazena.khazena.core.data.demo.DemoCatalog
import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.business.services.domain.Service
import com.khazena.khazena.feature.business.services.domain.ServicesRepository

class FakeServicesRepository(private val demo: DemoDataSettings) : ServicesRepository {
    override suspend fun getServices(): Result<List<Service>, DataError> = demo.demoResult(emptyList()) {
        listOf(
            Service("1", LocalizedText("پلێتی گۆشتی تێکەڵ", "Mixed grill plate", "طبق مشاوي مشكلة"), LocalizedText("کەباب، تکە و برنج", "Kebab, tikka and rice", "كباب وتكة وأرز"), 18_000, 12_600),
            Service("2", LocalizedText("شیشی مریشک", "Chicken shish", "شيش دجاج"), LocalizedText("لەگەڵ زەڵاتە", "Served with salad", "مع السلطة"), 12_000, 8_400),
            Service("3", LocalizedText("کوبە", "Kubba", "كبة"), LocalizedText("چوار پارچە", "Four pieces", "أربع قطع"), 6_000, 4_200),
            Service("4", LocalizedText("ژەمی خێزانی", "Family meal", "وجبة عائلية"), LocalizedText("بۆ چوار کەس", "For four people", "لأربعة أشخاص"), 45_000, 31_500),
            Service("5", LocalizedText("شەربەتی تازە", "Fresh juice", "عصير طازج"), LocalizedText("پرتەقاڵ یان هەنار", "Orange or pomegranate", "برتقال أو رمان"), 3_000, 2_100),
        )
    }
}
