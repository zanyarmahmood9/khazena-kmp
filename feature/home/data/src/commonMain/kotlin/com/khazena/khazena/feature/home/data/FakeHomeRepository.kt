package com.khazena.khazena.feature.home.data

import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.home.domain.CategoryDetail
import com.khazena.khazena.feature.home.domain.CategoryDirectory
import com.khazena.khazena.feature.home.domain.FeaturedOffer
import com.khazena.khazena.feature.home.domain.HomeCategory
import com.khazena.khazena.feature.home.domain.HomeFeed
import com.khazena.khazena.feature.home.domain.HomeRepository
import com.khazena.khazena.feature.home.domain.MemberSummary
import com.khazena.khazena.feature.home.domain.NearbyPlace
import com.khazena.khazena.feature.home.domain.TopDeal

class FakeHomeRepository(private val demo: DemoDataSettings) : HomeRepository {
    override suspend fun getHomeFeed(): Result<HomeFeed, DataError> = demo.demoResult(EmptyFeed) { DemoFeed }

    override suspend fun getCategories(): Result<CategoryDirectory, DataError> =
        demo.demoResult(CategoryDirectory(City, emptyList())) { DemoDirectory }

    override suspend fun getCategory(id: String): Result<CategoryDetail, DataError> {
        val detail = DemoCategoryDetails[id] ?: return Result.Error(DataError.Network.NOT_FOUND)
        return demo.demoResult(detail.copy(subcategories = emptyList(), places = emptyList())) { detail }
    }
}

private val Member = MemberSummary(
    firstName = "Bandy",
    initials = "BK",
    plan = LocalizedText(ckb = "پریمیەم", en = "Premium", ar = "بريميوم"),
    period = LocalizedText(ckb = "تشرینی ٢", en = "Nov", ar = "نوفمبر"),
    savedIqd = 48_500,
    visits = 12,
    stores = 5,
)

private val City = LocalizedText(ckb = "سلێمانی", en = "Sulaymaniyah", ar = "السليمانية")

private val EmptyFeed = HomeFeed(Member, City, unreadNotifications = 0, emptyList(), null, emptyList(), emptyList())

private val DemoFeed = HomeFeed(
    member = Member,
    city = City,
    unreadNotifications = 3,
    categories = listOf(
        HomeCategory("food", LocalizedText("خواردن و کافێ", "Food & Cafes", "مطاعم ومقاهٍ"), "cat_food", maxDiscountPercent = 50),
        HomeCategory("markets", LocalizedText("مارکێت و بازاڕ", "Markets & Groceries", "أسواق وبقالة"), "cat_markets"),
        HomeCategory("automotive", LocalizedText("ئۆتۆمبێل", "Automotive", "سيارات"), "cat_automotive"),
        HomeCategory("beauty", LocalizedText("جوانی و تەندروستی", "Health & Beauty", "صحة وجمال"), "cat_beauty"),
        HomeCategory("fashion", LocalizedText("جلوبەرگ", "Fashion", "أزياء"), "cat_fashion"),
        HomeCategory("electronics", LocalizedText("ئەلیکترۆنیات", "Electronics", "إلكترونيات"), "cat_electronics"),
        HomeCategory("services", LocalizedText("خزمەتگوزاری", "Services", "خدمات"), "cat_services"),
        HomeCategory("entertainment", LocalizedText("کات بەسەربردن", "Entertainment", "ترفيه"), "cat_entertainment"),
    ),
    featuredOffer = FeaturedOffer(
        id = "azmar-grill-platter",
        title = LocalizedText(ckb = "%٣٠ داشکاندن لە هەموو پلێتی گریل", en = "30% off every grill platter", ar = "خصم 30% على كل أطباق المشاوي"),
        merchant = LocalizedText("ئازمەڕ گریل", "Azmar Grill", "أزمر غريل"),
        note = LocalizedText(ckb = "تەنها بۆ ئەندامان ئەم هەفتەیە", en = "Members only this week", ar = "للأعضاء فقط هذا الأسبوع"),
        discountPercent = 30,
        imageKey = "photo_azmar",
        sponsored = true,
    ),
    topDeals = listOf(
        TopDeal("azmar", LocalizedText("ئازمەڕ گریل", "Azmar Grill", "أزمر غريل"), LocalizedText("گریل", "Grill", "مشاوي"), 4.8, 30, "photo_azmar"),
        TopDeal("lavender", LocalizedText("لاڤێندەر بیوتی", "Lavender Beauty", "لافندر بيوتي"), LocalizedText("ساڵۆن", "Salon", "صالون"), 4.9, 25, "photo_lavender"),
        TopDeal(
            "goizha",
            LocalizedText("گۆیژە کار کەیر", "Goizha Car Care", "كويژة كار كير"),
            LocalizedText("شوشتنی ئۆتۆمبێل", "Car wash", "غسيل سيارات"),
            4.6,
            20,
            "photo_goizha",
            area = LocalizedText("ڕاپەڕین", "Raparin", "رابرين"),
        ),
    ),
    nearby = listOf(
        NearbyPlace("naz", LocalizedText("ناز کافێ", "Naz Café", "ناز كافيه"), LocalizedText("کافێ", "Café", "مقهى"), 0.6, 10, "photo_naz"),
        NearbyPlace("shirin", LocalizedText("شیرینی شیرین", "Shirin Sweets", "حلويات شيرين"), LocalizedText("شیرینی و نانەواخانە", "Sweets & bakery", "حلويات ومخبز"), 0.9, 20, "photo_shirin"),
        NearbyPlace("bazaar", LocalizedText("بازاڕی فرێش", "Bazaar Fresh Market", "بازار فريش ماركت"), LocalizedText("مارکێت", "Market", "سوق"), 1.1, 15, "photo_bazaar"),
        NearbyPlace("shifa", LocalizedText("دەرمانخانەی شیفا", "Shifa Pharmacy", "صيدلية شفاء"), LocalizedText("دەرمانخانە", "Pharmacy", "صيدلية"), 0.4, 10, "photo_shifa"),
    ),
)

private val DemoDirectory = CategoryDirectory(
    city = City,
    categories = listOf(
        HomeCategory("food", LocalizedText("خواردن و کافێ", "Food & Cafes", "مطاعم ومقاهٍ"), "cat_food", maxDiscountPercent = 50, placeCount = 128),
        HomeCategory("markets", LocalizedText("مارکێت و بازاڕ", "Markets & Groceries", "أسواق وبقالة"), "cat_markets", maxDiscountPercent = 25, placeCount = 64),
        HomeCategory("automotive", LocalizedText("ئۆتۆمبێل", "Automotive", "سيارات"), "cat_automotive", maxDiscountPercent = 30, placeCount = 41),
        HomeCategory("beauty", LocalizedText("جوانی و تەندروستی", "Health & Beauty", "صحة وجمال"), "cat_beauty", maxDiscountPercent = 40, placeCount = 87),
        HomeCategory("fashion", LocalizedText("جلوبەرگ", "Fashion", "أزياء"), "cat_fashion", maxDiscountPercent = 35, placeCount = 52),
        HomeCategory("electronics", LocalizedText("ئەلیکترۆنیات", "Electronics", "إلكترونيات"), "cat_electronics", maxDiscountPercent = 20, placeCount = 29),
        HomeCategory("services", LocalizedText("خزمەتگوزاری", "Services", "خدمات"), "cat_services", maxDiscountPercent = 30, placeCount = 46),
        HomeCategory("entertainment", LocalizedText("کات بەسەربردن", "Entertainment", "ترفيه"), "cat_entertainment", maxDiscountPercent = 45, placeCount = 23),
    ),
)
