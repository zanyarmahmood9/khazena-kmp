package com.khazena.khazena.feature.store.data

import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.store.domain.AvatarTone
import com.khazena.khazena.feature.store.domain.ContactKind
import com.khazena.khazena.feature.store.domain.MenuItem
import com.khazena.khazena.feature.store.domain.MenuSection
import com.khazena.khazena.feature.store.domain.OpeningHours
import com.khazena.khazena.feature.store.domain.Review
import com.khazena.khazena.feature.store.domain.Store
import com.khazena.khazena.feature.store.domain.StoreContact
import com.khazena.khazena.feature.store.domain.StoreRepository
import com.khazena.khazena.feature.store.domain.Weekday

class FakeStoreRepository(private val demo: DemoDataSettings) : StoreRepository {
    override suspend fun getStore(id: String): Result<Store, DataError> {
        val store = DemoStores[id] ?: return Result.Error(DataError.Network.NOT_FOUND)
        return demo.demoResult(store.copy(menu = emptyList(), menuSections = emptyList(), reviews = emptyList())) { store }
    }
}

private val Sulaymaniyah = LocalizedText("سلێمانی", "Sulaymaniyah", "السليمانية")

private fun dailyHours(opens: String, closes: String, friday: Pair<String, String> = opens to closes) =
    Weekday.entries.map { day -> if (day == Weekday.Friday) OpeningHours(day, friday.first, friday.second) else OpeningHours(day, opens, closes) }

private val EveryVisitRules = listOf(
    LocalizedText("پیشاندانی QRـی زیندوو پێش پارەدان", "Show your live QR before paying", "اعرض رمز QR المباشر قبل الدفع"),
    LocalizedText("لەگەڵ هیچ داشکاندنێکی تر کۆ ناکرێتەوە", "Can’t be combined with other promotions", "لا يُجمع مع عروض أخرى"),
)

private val Azmar = Store(
    id = "azmar",
    name = LocalizedText("ئازمەڕ گریل", "Azmar Grill", "أزمر غريل"),
    categoryLine = LocalizedText("خواردن و کافێ · گریل", "Food & Cafes · Grill", "مطاعم ومقاهٍ · مشاوي"),
    cuisine = LocalizedText("گریلی کوردی و ڕۆژهەڵاتی ناوەڕاست", "Kurdish & Middle Eastern charcoal grill", "مشاوٍ كردية وشرق أوسطية على الفحم"),
    coverKey = "cover_azmar",
    logoKey = "logo_azmar",
    photoCount = 12,
    isOpenNow = true,
    hoursNote = LocalizedText("تا ١١ی شەو", "Until 11 PM", "حتى 11 مساءً"),
    rating = 4.8,
    reviewCount = 126,
    distanceKm = 1.4,
    discountPercent = 30,
    offerRules = listOf(LocalizedText("%٣٠ داشکاندن لە هەموو مینیوەکە، هەموو سەردانێک", "30% off the full menu, every visit", "خصم 30% على كامل القائمة في كل زيارة")) + EveryVisitRules,
    menuSections = listOf(
        MenuSection("grills", LocalizedText("گریل", "Grills", "مشاوي")),
        MenuSection("soups", LocalizedText("شۆربا", "Soups", "شوربات")),
        MenuSection("desserts", LocalizedText("شیرینی", "Desserts", "حلويات")),
        MenuSection("drinks", LocalizedText("خواردنەوە", "Drinks", "مشروبات")),
    ),
    menu = listOf(
        MenuItem(
            "chicken_kebab_platter",
            LocalizedText("پلێتی کەبابی مریشک", "Chicken Kebab Platter", "طبق كباب دجاج"),
            LocalizedText("دوو شیشی مریشک، برنج، سەوزەی برژاو و نانی تازە", "Two chicken skewers, rice, grilled veg and fresh bread", "سيخا دجاج، أرز، خضار مشوية وخبز طازج"),
            12_000, 8_400, "item_chicken_kebab", "grills", popular = true,
        ),
        MenuItem(
            "mixed_grill_for_two",
            LocalizedText("گریلی تێکەڵ بۆ دوو کەس", "Mixed Grill for Two", "مشاوي مشكلة لشخصين"),
            LocalizedText("کەبابی بەرخ، تکەی مریشک و کفتە لەگەڵ زیادە بۆ دوو کەس", "Lamb kebab, chicken tikka and kofta with sides for two", "كباب لحم، تكة دجاج وكفتة مع مقبلات لشخصين"),
            30_000, 21_000, "item_mixed_grill", "grills", popular = true,
        ),
        MenuItem(
            "red_lentil_soup",
            LocalizedText("شۆربای نیسکی سوور", "Red Lentil Soup", "شوربة عدس أحمر"),
            LocalizedText("نیسکی هێواش لێنراو لەگەڵ لیمۆ و نانی برژاو", "Slow-cooked lentils with lemon and toasted bread", "عدس مطبوخ ببطء مع ليمون وخبز محمص"),
            4_000, 2_800, "item_lentil_soup", "soups",
        ),
        MenuItem(
            "kunafa_pistachio",
            LocalizedText("کونافە بە فستق", "Kunafa with Pistachio", "كنافة بالفستق"),
            LocalizedText("کونافەی گەرمی پەنیر، شەربەت و فستقی هاڕاو", "Warm cheese kunafa, syrup and crushed pistachio", "كنافة جبن دافئة مع قطر وفستق مطحون"),
            6_000, 4_200, "item_kunafa", "desserts", popular = true,
        ),
        MenuItem(
            "fresh_ayran",
            LocalizedText("دۆی تازە", "Fresh Ayran", "عيران طازج"),
            LocalizedText("خواردنەوەی ساردی ماست بە پونگ", "Cold yogurt drink with mint", "مشروب لبن بارد بالنعناع"),
            1_500, 1_050, "item_ayran", "drinks",
        ),
    ),
    ratingBreakdown = listOf(98, 19, 5, 3, 1),
    reviews = listOf(
        Review(
            "r1", "Roam A.", "RA", AvatarTone.Violet, LocalizedText("٢ ڕۆژ لەمەوبەر", "2 days ago", "قبل يومين"), 2, 5,
            LocalizedText(
                "باشترین کەبابی مریشک لە سلێمانی. %٣٠ەکە هەر کە کاشێرەکە کۆدەکەمی سکان کرد جێبەجێ بوو — بێ هیچ پرسیارێک.",
                "Best chicken kebab in Slemani. The 30% was applied the second the cashier scanned my code — no questions asked.",
                "أفضل كباب دجاج في السليمانية. طُبّق خصم 30% فور مسح الكاشير لرمزي — دون أي أسئلة.",
            ),
            7_200,
        ),
        Review(
            "r2", "Lana K.", "LK", AvatarTone.Sun, LocalizedText("٥ ڕۆژ لەمەوبەر", "5 days ago", "قبل 5 أيام"), 5, 5,
            LocalizedText(
                "کەشێکی خێزانیی خۆش و گریلە تێکەڵەکە زۆر گەورەیە. کارمەندەکان گەرم و خێرا بوون تەنانەت لە شەوێکی قەرەباڵغیشدا.",
                "Lovely family atmosphere and the mixed grill is huge. Staff were warm and quick even on a busy night.",
                "أجواء عائلية رائعة والمشاوي المشكلة ضخمة. الموظفون ودودون وسريعون حتى في ليلة مزدحمة.",
            ),
            9_000, hasPhotos = true,
        ),
        Review(
            "r3", "Hiwa M.", "HM", AvatarTone.Sun, LocalizedText("١ هەفتە لەمەوبەر", "1 week ago", "قبل أسبوع"), 7, 4,
            LocalizedText(
                "خواردنی نایاب. ئێوارەی هەینی کەمێک چاوەڕوان بووین، بەڵام داشکاندنەکە بە دروستی لە پسوولەکەدا دەرکەوت.",
                "Great food. A bit of a wait on Friday evening, but the discount showed correctly on the bill.",
                "طعام رائع. انتظار قليل مساء الجمعة، لكن الخصم ظهر بشكل صحيح في الفاتورة.",
            ),
            4_800,
        ),
        Review(
            "r4", "Sara D.", "SD", AvatarTone.Green, LocalizedText("٢ هەفتە لەمەوبەر", "2 weeks ago", "قبل أسبوعين"), 14, 5,
            LocalizedText(
                "شوێنێکی زۆر پاک و کونافەکەی پێویستە تاقی بکرێتەوە. هەموو جارێک %٣٠ کەمتر دەدەین، بۆیە بووەتە شوێنی هەفتانەمان.",
                "Spotless place and the kunafa is a must. Paying 30% less every time makes this our weekly spot.",
                "مكان نظيف جداً والكنافة لا بد منها. دفع 30% أقل في كل مرة جعله مكاننا الأسبوعي.",
            ),
            5_400, hasPhotos = true,
        ),
    ),
    about = LocalizedText(
        "گریلی خەڵووزی خێزانی لە ساڵی ٢٠١٤ەوە، خواردنە کلاسیکییە کوردی و ڕۆژهەڵاتی ناوەڕاستییەکان پێشکەش دەکات — کەباب، تکە و نانی تازە لە تەنووری قوڕ.",
        "Family-run charcoal grill since 2014, serving Kurdish and Middle Eastern classics — kebabs, tikka and fresh bread from the clay oven.",
        "مشاوٍ على الفحم يديرها أهلها منذ 2014، تقدم الأطباق الكردية والشرق أوسطية الكلاسيكية — كباب وتكة وخبز طازج من التنور الطيني.",
    ),
    address = LocalizedText("شەقامی سالم، نزیک پارکی ئازادی", "Salim Street, near Azadi Park", "شارع سالم، قرب حديقة آزادي"),
    area = Sulaymaniyah,
    latitude = 35.5613,
    longitude = 45.4374,
    hours = dailyHours("11:00", "23:00", friday = "13:00" to "00:00").map { if (it.day == Weekday.Thursday) it.copy(closes = "00:00") else it },
    contacts = listOf(
        StoreContact(ContactKind.Phone, "+964 770 123 4567", "tel:+9647701234567"),
        StoreContact(ContactKind.Instagram, "@azmargrill", "https://instagram.com/azmargrill"),
        StoreContact(ContactKind.Facebook, "Azmar Grill", "https://facebook.com/azmargrill"),
        StoreContact(ContactKind.TikTok, "@azmargrill", "https://tiktok.com/@azmargrill"),
    ),
)

// The other places have no service list yet (Figma 02c·B): header, offer, info and a first review only.
private fun basicStore(
    id: String,
    name: LocalizedText,
    categoryLine: LocalizedText,
    cuisine: LocalizedText,
    coverKey: String,
    rating: Double,
    reviewCount: Int,
    distanceKm: Double,
    discountPercent: Int,
    address: LocalizedText,
    hours: List<OpeningHours>,
    closes: LocalizedText,
    instagram: String,
    phone: String,
) = Store(
    id = id,
    name = name,
    categoryLine = categoryLine,
    cuisine = cuisine,
    coverKey = coverKey,
    logoKey = null,
    photoCount = 6,
    isOpenNow = true,
    hoursNote = closes,
    rating = rating,
    reviewCount = reviewCount,
    distanceKm = distanceKm,
    discountPercent = discountPercent,
    offerRules = listOf(
        LocalizedText("%$discountPercent داشکاندن لە هەموو شتێک، هەموو سەردانێک", "$discountPercent% off everything, every visit", "خصم $discountPercent% على كل شيء في كل زيارة"),
    ) + EveryVisitRules,
    menuSections = emptyList(),
    menu = emptyList(),
    ratingBreakdown = listOf((reviewCount * 0.8).toInt(), (reviewCount * 0.15).toInt(), reviewCount - (reviewCount * 0.8).toInt() - (reviewCount * 0.15).toInt(), 0, 0),
    reviews = listOf(
        Review(
            "${id}_r1", "Dara S.", "DS", AvatarTone.Violet, LocalizedText("٣ ڕۆژ لەمەوبەر", "3 days ago", "قبل 3 أيام"), 3, 5,
            LocalizedText("داشکاندنەکە بێ کێشە جێبەجێ بوو. دووبارە دێمەوە.", "The discount went through without a fuss. I’ll be back.", "طُبّق الخصم دون أي مشكلة. سأعود مجدداً."),
            3_000,
        ),
    ),
    about = cuisine,
    address = address,
    area = Sulaymaniyah,
    latitude = 35.5613,
    longitude = 45.4374,
    hours = hours,
    contacts = listOf(
        StoreContact(ContactKind.Phone, phone, "tel:${phone.filter { it.isDigit() || it == '+' }}"),
        StoreContact(ContactKind.Instagram, "@$instagram", "https://instagram.com/$instagram"),
    ),
)

private val Until11 = LocalizedText("تا ١١ی شەو", "Until 11 PM", "حتى 11 مساءً")
private val Until10 = LocalizedText("تا ١٠ی شەو", "Until 10 PM", "حتى 10 مساءً")
private val Until9 = LocalizedText("تا ٩ی شەو", "Until 9 PM", "حتى 9 مساءً")
private val Until12 = LocalizedText("تا ١٢ی شەو", "Until midnight", "حتى منتصف الليل")

private val DemoStores: Map<String, Store> = listOf(
    Azmar,
    basicStore(
        "shirin", LocalizedText("شیرینی شیرین", "Shirin Sweets", "حلويات شيرين"),
        LocalizedText("خواردن و کافێ · شیرینی", "Food & Cafes · Sweets", "مطاعم ومقاهٍ · حلويات"),
        LocalizedText("شیرینی و نانەواخانەی خێزانی", "Family sweets & bakery", "حلويات ومخبز عائلي"),
        "cover_shirin", 4.7, 203, 0.9, 20, LocalizedText("سەرچنار، نزیک بازنەی سەرچنار", "Sarchinar, near the roundabout", "سرچنار، قرب الدوار"),
        dailyHours("09:00", "22:00"), Until10, "shirinsweets", "+964 770 222 1100",
    ),
    basicStore(
        "kebab_hawler", LocalizedText("کەباب هەولێر", "Kebab Hawler", "كباب هولير"),
        LocalizedText("خواردن و کافێ · چێشتخانە", "Food & Cafes · Restaurant", "مطاعم ومقاهٍ · مطعم"),
        LocalizedText("کەبابی هەولێری لەسەر خەڵووز", "Erbil-style charcoal kebab", "كباب أربيلي على الفحم"),
        "cover_kebab", 4.6, 98, 1.2, 15, LocalizedText("شەقامی مەولەوی", "Mawlawi Street", "شارع مولوي"),
        dailyHours("12:00", "23:00"), Until11, "kebabhawler", "+964 750 333 4455",
    ),
    basicStore(
        "naz", LocalizedText("ناز کافێ", "Naz Café", "ناز كافيه"),
        LocalizedText("خواردن و کافێ · کافێ", "Food & Cafes · Café", "مطاعم ومقاهٍ · مقهى"),
        LocalizedText("قاوەی تایبەت و شیرینی", "Specialty coffee & pastries", "قهوة مختصة ومعجنات"),
        "cover_naz", 4.5, 61, 0.6, 10, LocalizedText("شەقامی سالم", "Salim Street", "شارع سالم"),
        dailyHours("08:00", "00:00"), Until12, "nazcafe", "+964 770 444 5566",
    ),
    basicStore(
        "lavender", LocalizedText("لاڤێندەر بیوتی", "Lavender Beauty", "لافندر بيوتي"),
        LocalizedText("جوانی و تەندروستی · ساڵۆن", "Health & Beauty · Salon", "صحة وجمال · صالون"),
        LocalizedText("قژ، نینۆک و چاودێری پێست", "Hair, nails and skin care", "شعر وأظافر وعناية بالبشرة"),
        "photo_lavender", 4.9, 142, 1.8, 25, LocalizedText("شەقامی سالم", "Salim Street", "شارع سالم"),
        dailyHours("10:00", "21:00"), Until9, "lavenderbeauty", "+964 770 555 6677",
    ),
    basicStore(
        "goizha", LocalizedText("گۆیژە کار کەیر", "Goizha Car Care", "كويژة كار كير"),
        LocalizedText("ئۆتۆمبێل · شوشتن", "Automotive · Car wash", "سيارات · غسيل"),
        LocalizedText("شوشتن و پاککردنەوەی ناوەوە", "Wash, wax and interior cleaning", "غسيل وتلميع وتنظيف داخلي"),
        "photo_goizha", 4.6, 54, 2.3, 20, LocalizedText("ڕاپەڕین", "Raparin", "رابرين"),
        dailyHours("08:00", "20:00"), LocalizedText("تا ٨ی ئێوارە", "Until 8 PM", "حتى 8 مساءً"), "goizhacarcare", "+964 750 666 7788",
    ),
    basicStore(
        "bazaar", LocalizedText("بازاڕی فرێش", "Bazaar Fresh Market", "بازار فريش ماركت"),
        LocalizedText("مارکێت و بازاڕ · مارکێت", "Markets & Groceries · Market", "أسواق وبقالة · سوق"),
        LocalizedText("سەوزە، میوە و پێداویستی ڕۆژانە", "Fresh produce and daily essentials", "خضار وفواكه ومستلزمات يومية"),
        "photo_bazaar", 4.4, 77, 1.1, 15, LocalizedText("بەختیاری", "Bakhtiari", "بختياري"),
        dailyHours("07:00", "23:00"), Until11, "bazaarfresh", "+964 770 777 8899",
    ),
    basicStore(
        "shifa", LocalizedText("دەرمانخانەی شیفا", "Shifa Pharmacy", "صيدلية شفاء"),
        LocalizedText("جوانی و تەندروستی · دەرمانخانە", "Health & Beauty · Pharmacy", "صحة وجمال · صيدلية"),
        LocalizedText("دەرمان، ڤیتامین و چاودێری", "Medicine, vitamins and care", "أدوية وفيتامينات وعناية"),
        "photo_shifa", 4.7, 88, 0.4, 10, LocalizedText("سەرچنار", "Sarchinar", "سرچنار"),
        dailyHours("08:00", "00:00"), Until12, "shifapharmacy", "+964 750 888 9900",
    ),
).associateBy { it.id }
