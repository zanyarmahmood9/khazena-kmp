package com.khazena.khazena.feature.home.presentation

import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.presentation.demoPhoto
import com.khazena.khazena.feature.home.domain.FeaturedOffer
import com.khazena.khazena.feature.home.domain.HomeCategory
import com.khazena.khazena.feature.home.domain.HomeFeed
import com.khazena.khazena.feature.home.domain.MemberSummary
import com.khazena.khazena.feature.home.domain.NearbyPlace
import com.khazena.khazena.feature.home.domain.TopDeal
import khazena.feature.home.presentation.generated.resources.Res
import khazena.feature.home.presentation.generated.resources.home_cat_automotive
import khazena.feature.home.presentation.generated.resources.home_cat_beauty
import khazena.feature.home.presentation.generated.resources.home_cat_electronics
import khazena.feature.home.presentation.generated.resources.home_cat_entertainment
import khazena.feature.home.presentation.generated.resources.home_cat_fashion
import khazena.feature.home.presentation.generated.resources.home_cat_food
import khazena.feature.home.presentation.generated.resources.home_cat_markets
import khazena.feature.home.presentation.generated.resources.home_cat_services
import org.jetbrains.compose.resources.DrawableResource

// shortcut: category art is bundled and looked up by key (place photos come from core's demoPhoto); switch to Coil URLs when the feed comes from the API.
private val homeImages = mapOf(
    "cat_food" to Res.drawable.home_cat_food,
    "cat_markets" to Res.drawable.home_cat_markets,
    "cat_automotive" to Res.drawable.home_cat_automotive,
    "cat_beauty" to Res.drawable.home_cat_beauty,
    "cat_fashion" to Res.drawable.home_cat_fashion,
    "cat_electronics" to Res.drawable.home_cat_electronics,
    "cat_services" to Res.drawable.home_cat_services,
    "cat_entertainment" to Res.drawable.home_cat_entertainment,
)

internal fun homeImage(key: String): DrawableResource = homeImages[key] ?: demoPhoto(key) ?: Res.drawable.home_cat_food

internal val previewFeed = HomeFeed(
    member = MemberSummary("Bandy", "BK", LocalizedText("پریمیەم", "Premium", "بريميوم"), LocalizedText("تشرینی ٢", "Nov", "نوفمبر"), 48_500, 12, 5),
    city = LocalizedText("سلێمانی", "Sulaymaniyah", "السليمانية"),
    unreadNotifications = 3,
    categories = listOf(
        HomeCategory("food", LocalizedText("خواردن و کافێ", "Food & Cafes", "مطاعم ومقاهٍ"), "cat_food", 50),
        HomeCategory("markets", LocalizedText("مارکێت و بازاڕ", "Markets & Groceries", "أسواق وبقالة"), "cat_markets"),
        HomeCategory("automotive", LocalizedText("ئۆتۆمبێل", "Automotive", "سيارات"), "cat_automotive"),
        HomeCategory("beauty", LocalizedText("جوانی و تەندروستی", "Health & Beauty", "صحة وجمال"), "cat_beauty"),
        HomeCategory("fashion", LocalizedText("جلوبەرگ", "Fashion", "أزياء"), "cat_fashion"),
        HomeCategory("electronics", LocalizedText("ئەلیکترۆنیات", "Electronics", "إلكترونيات"), "cat_electronics"),
        HomeCategory("services", LocalizedText("خزمەتگوزاری", "Services", "خدمات"), "cat_services"),
        HomeCategory("entertainment", LocalizedText("کات بەسەربردن", "Entertainment", "ترفيه"), "cat_entertainment"),
    ),
    featuredOffer = FeaturedOffer(
        "offer",
        LocalizedText("%٣٠ داشکاندن لە هەموو پلێتی گریل", "30% off every grill platter", "خصم 30% على كل أطباق المشاوي"),
        LocalizedText("ئازمەڕ گریل", "Azmar Grill", "أزمر غريل"),
        LocalizedText("تەنها بۆ ئەندامان ئەم هەفتەیە", "Members only this week", "للأعضاء فقط هذا الأسبوع"),
        30,
        "photo_azmar",
        sponsored = true,
        storeId = "azmar",
    ),
    topDeals = listOf(
        TopDeal("azmar", LocalizedText("ئازمەڕ گریل", "Azmar Grill", "أزمر غريل"), LocalizedText("گریل", "Grill", "مشاوي"), 4.8, 30, "photo_azmar"),
        TopDeal("lavender", LocalizedText("لاڤێندەر بیوتی", "Lavender Beauty", "لافندر بيوتي"), LocalizedText("ساڵۆن", "Salon", "صالون"), 4.9, 25, "photo_lavender"),
        TopDeal("goizha", LocalizedText("گۆیژە کار کەیر", "Goizha Car Care", "كويژة كار كير"), LocalizedText("شوشتنی ئۆتۆمبێل", "Car wash", "غسيل سيارات"), 4.6, 20, "photo_goizha", LocalizedText("ڕاپەڕین", "Raparin", "رابرين")),
    ),
    nearby = listOf(
        NearbyPlace("naz", LocalizedText("ناز کافێ", "Naz Café", "ناز كافيه"), LocalizedText("کافێ", "Café", "مقهى"), 0.6, 10, "photo_naz"),
        NearbyPlace("shirin", LocalizedText("شیرینی شیرین", "Shirin Sweets", "حلويات شيرين"), LocalizedText("شیرینی و نانەواخانە", "Sweets & bakery", "حلويات ومخبز"), 0.9, 20, "photo_shirin"),
        NearbyPlace("bazaar", LocalizedText("بازاڕی فرێش", "Bazaar Fresh Market", "بازار فريش ماركت"), LocalizedText("مارکێت", "Market", "سوق"), 1.1, 15, "photo_bazaar"),
        NearbyPlace("shifa", LocalizedText("دەرمانخانەی شیفا", "Shifa Pharmacy", "صيدلية شفاء"), LocalizedText("دەرمانخانە", "Pharmacy", "صيدلية"), 0.4, 10, "photo_shifa"),
    ),
)
