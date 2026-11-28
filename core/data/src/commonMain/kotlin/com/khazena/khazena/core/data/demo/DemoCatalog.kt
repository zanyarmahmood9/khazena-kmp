package com.khazena.khazena.core.data.demo

import com.khazena.khazena.core.domain.LocalizedText

class DemoMerchant(
    val id: String,
    val name: LocalizedText,
    val category: LocalizedText,
    val discountPercent: Int,
    val distanceKm: Double,
)

object DemoCatalog {
    private val food = LocalizedText(ckb = "خواردن و کافێ", en = "Food & Cafes", ar = "مطاعم ومقاهٍ")
    private val markets = LocalizedText(ckb = "مارکێت", en = "Markets & Groceries", ar = "أسواق وبقالة")
    private val beauty = LocalizedText(ckb = "جوانی و تەندروستی", en = "Health & Beauty", ar = "صحة وجمال")
    private val cars = LocalizedText(ckb = "خزمەتگوزاری ئۆتۆمبێل", en = "Car services", ar = "خدمات السيارات")

    val merchants = listOf(
        DemoMerchant("m1", LocalizedText("ئازمەڕ گریل", "Azmar Grill", "أزمر غريل"), food, 30, 0.8),
        DemoMerchant("m2", LocalizedText("کافێ چاڤی", "Chavi Cafe", "مقهى چاڤي"), food, 20, 1.2),
        DemoMerchant("m3", LocalizedText("مارکێتی سەرای", "Saray Market", "سوق سراي"), markets, 15, 2.1),
        DemoMerchant("m4", LocalizedText("ساڵۆنی لانا", "Lana Beauty Salon", "صالون لانا"), beauty, 25, 2.6),
        DemoMerchant("m5", LocalizedText("شوشتنی باوان", "Bawan Car Wash", "غسيل باوان"), cars, 40, 3.4),
        DemoMerchant("m6", LocalizedText("پیتزای شار", "Shar Pizza", "بيتزا شار"), food, 35, 4.0),
    )

    val members = listOf("Bandy Kamal", "Rozh Ahmed", "Lana Omar", "Dara Salih", "Shilan Karim")

    fun ago(minutes: Int) = LocalizedText(ckb = "$minutes خولەک لەمەوبەر", en = "$minutes min ago", ar = "قبل $minutes دقيقة")
}
