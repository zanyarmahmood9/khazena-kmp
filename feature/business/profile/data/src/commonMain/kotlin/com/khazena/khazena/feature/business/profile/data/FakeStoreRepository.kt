package com.khazena.khazena.feature.business.profile.data

import com.khazena.khazena.core.data.demo.DemoCatalog
import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.business.profile.domain.Store
import com.khazena.khazena.feature.business.profile.domain.StoreRepository

class FakeStoreRepository(private val demo: DemoDataSettings) : StoreRepository {
    override suspend fun getStore(): Result<Store?, DataError> = demo.demoResult(empty = null) {
        val merchant = DemoCatalog.merchants.first()
        Store(
            name = merchant.name,
            sector = merchant.category,
            address = LocalizedText(ckb = "شەقامی سالم، سلێمانی", en = "Salim Street, Sulaymaniyah", ar = "شارع سالم، السليمانية"),
            hours = LocalizedText(ckb = "هەموو ڕۆژێک ١١:٠٠ – ٢٣:٠٠", en = "Every day 11:00 – 23:00", ar = "يوميًا ١١:٠٠ – ٢٣:٠٠"),
            phone = "+964 750 000 0000",
            memberDiscountPercent = merchant.discountPercent,
        )
    }
}
