package com.khazena.khazena.feature.home.data

import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.feature.home.domain.CategoryDetail
import com.khazena.khazena.feature.home.domain.CategoryPlace
import com.khazena.khazena.feature.home.domain.Subcategory

private val Salim = LocalizedText("شەقامی سالم", "Salim St", "شارع سالم")
private val Sarchinar = LocalizedText("سەرچنار", "Sarchinar", "سرچنار")
private val Mawlawi = LocalizedText("شەقامی مەولەوی", "Mawlawi St", "شارع مولوي")
private val Raparin = LocalizedText("ڕاپەڕین", "Raparin", "رابرين")
private val Bakhtiari = LocalizedText("بەختیاری", "Bakhtiari", "بختياري")

private fun detail(
    id: String,
    name: LocalizedText,
    description: LocalizedText,
    placeCount: Int,
    topDiscount: Int,
    openNow: Int,
    subcategories: List<Subcategory> = emptyList(),
    places: List<CategoryPlace> = emptyList(),
) = CategoryDetail(id, name, description, "cat_$id", placeCount, topDiscount, openNow, subcategories, places)

internal val DemoCategoryDetails: Map<String, CategoryDetail> = listOf(
    detail(
        "food",
        LocalizedText("خواردن و کافێ", "Food & Cafes", "مطاعم ومقاهٍ"),
        LocalizedText(
            "چێشتخانە، کافێ، شیرینی و زیاتر بە نرخی ئەندامان.",
            "Restaurants, cafés, sweets and more at member prices.",
            "مطاعم ومقاهٍ وحلويات والمزيد بأسعار الأعضاء.",
        ),
        placeCount = 128,
        topDiscount = 50,
        openNow = 86,
        subcategories = listOf(
            Subcategory("restaurants", LocalizedText("چێشتخانە", "Restaurants", "مطاعم"), 52),
            Subcategory("cafes", LocalizedText("کافێ", "Cafés", "مقاهٍ"), 31),
            Subcategory("sweets", LocalizedText("شیرینی", "Sweets", "حلويات"), 18),
            Subcategory("fast_food", LocalizedText("خواردنی خێرا", "Fast food", "وجبات سريعة"), 15),
            Subcategory("bakeries", LocalizedText("نانەواخانە", "Bakeries", "مخابز"), 12),
        ),
        places = listOf(
            CategoryPlace("azmar", LocalizedText("ئازمەڕ گریل", "Azmar Grill", "أزمر غريل"), LocalizedText("گریل", "Grill", "مشاوي"), Salim, 1.4, 4.8, 126, 30, "photo_azmar", "restaurants"),
            CategoryPlace("shirin", LocalizedText("شیرینی شیرین", "Shirin Sweets", "حلويات شيرين"), LocalizedText("شیرینی و نانەواخانە", "Sweets & bakery", "حلويات ومخبز"), Sarchinar, 0.9, 4.7, 203, 20, "photo_shirin", "sweets"),
            CategoryPlace("kebab_hawler", LocalizedText("کەباب هەولێر", "Kebab Hawler", "كباب هولير"), LocalizedText("چێشتخانە", "Restaurant", "مطعم"), Mawlawi, 1.2, 4.6, 98, 15, "photo_kebab", "restaurants"),
            CategoryPlace("naz", LocalizedText("ناز کافێ", "Naz Café", "ناز كافيه"), LocalizedText("کافێ", "Café", "مقهى"), Salim, 0.6, 4.5, 61, 10, "photo_naz", "cafes"),
        ),
    ),
    detail(
        "markets",
        LocalizedText("مارکێت و بازاڕ", "Markets & Groceries", "أسواق وبقالة"),
        LocalizedText("مارکێت، سەوزە و میوە و پێداویستی ڕۆژانە.", "Supermarkets, fresh produce and daily essentials.", "أسواق وخضار وفواكه ومستلزمات يومية."),
        placeCount = 64,
        topDiscount = 25,
        openNow = 47,
        places = listOf(
            CategoryPlace("bazaar", LocalizedText("بازاڕی فرێش", "Bazaar Fresh Market", "بازار فريش ماركت"), LocalizedText("مارکێت", "Market", "سوق"), Bakhtiari, 1.1, 4.4, 77, 15, "photo_bazaar", "markets"),
        ),
    ),
    detail(
        "automotive",
        LocalizedText("ئۆتۆمبێل", "Automotive", "سيارات"),
        LocalizedText("شوشتن، چاککردنەوە و پارچەی ئۆتۆمبێل.", "Car washes, repairs and spare parts.", "غسيل وتصليح وقطع غيار السيارات."),
        placeCount = 41,
        topDiscount = 30,
        openNow = 22,
        places = listOf(
            CategoryPlace("goizha", LocalizedText("گۆیژە کار کەیر", "Goizha Car Care", "كويژة كار كير"), LocalizedText("شوشتنی ئۆتۆمبێل", "Car wash", "غسيل سيارات"), Raparin, 2.3, 4.6, 54, 20, "photo_goizha", "car_wash"),
        ),
    ),
    detail(
        "beauty",
        LocalizedText("جوانی و تەندروستی", "Health & Beauty", "صحة وجمال"),
        LocalizedText("ساڵۆن، دەرمانخانە و چاودێری خۆت.", "Salons, pharmacies and self-care.", "صالونات وصيدليات وعناية شخصية."),
        placeCount = 87,
        topDiscount = 40,
        openNow = 58,
        places = listOf(
            CategoryPlace("lavender", LocalizedText("لاڤێندەر بیوتی", "Lavender Beauty", "لافندر بيوتي"), LocalizedText("ساڵۆن", "Salon", "صالون"), Salim, 1.8, 4.9, 142, 25, "photo_lavender", "salons"),
            CategoryPlace("shifa", LocalizedText("دەرمانخانەی شیفا", "Shifa Pharmacy", "صيدلية شفاء"), LocalizedText("دەرمانخانە", "Pharmacy", "صيدلية"), Sarchinar, 0.4, 4.7, 88, 10, "photo_shifa", "pharmacies"),
        ),
    ),
    detail(
        "fashion",
        LocalizedText("جلوبەرگ", "Fashion", "أزياء"),
        LocalizedText("جل، پێڵاو و ئێکسسوار بۆ هەموو وەرزێک.", "Clothes, shoes and accessories for every season.", "ملابس وأحذية وإكسسوارات لكل موسم."),
        placeCount = 52,
        topDiscount = 35,
        openNow = 31,
    ),
    detail(
        "electronics",
        LocalizedText("ئەلیکترۆنیات", "Electronics", "إلكترونيات"),
        LocalizedText("مۆبایل، کۆمپیوتەر و ئامێری ماڵەوە.", "Phones, computers and home appliances.", "هواتف وحواسيب وأجهزة منزلية."),
        placeCount = 29,
        topDiscount = 20,
        openNow = 18,
    ),
    detail(
        "services",
        LocalizedText("خزمەتگوزاری", "Services", "خدمات"),
        LocalizedText("پاککردنەوە، چاککردنەوە و خزمەتی ماڵەوە.", "Cleaning, repairs and home services.", "تنظيف وتصليح وخدمات منزلية."),
        placeCount = 46,
        topDiscount = 30,
        openNow = 27,
    ),
    detail(
        "entertainment",
        LocalizedText("کات بەسەربردن", "Entertainment", "ترفيه"),
        LocalizedText("سینەما، یاری و چالاکی بۆ هەموو خێزان.", "Cinemas, games and activities for the whole family.", "سينما وألعاب وأنشطة لكل العائلة."),
        placeCount = 23,
        topDiscount = 45,
        openNow = 12,
    ),
).associateBy { it.id }
