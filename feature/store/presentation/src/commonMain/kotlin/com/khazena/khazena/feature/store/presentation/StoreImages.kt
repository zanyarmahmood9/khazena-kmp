package com.khazena.khazena.feature.store.presentation

import com.khazena.khazena.core.presentation.demoPhoto
import khazena.feature.store.presentation.generated.resources.Res
import khazena.feature.store.presentation.generated.resources.store_cover_azmar
import khazena.feature.store.presentation.generated.resources.store_cover_kebab
import khazena.feature.store.presentation.generated.resources.store_cover_naz
import khazena.feature.store.presentation.generated.resources.store_cover_shirin
import khazena.feature.store.presentation.generated.resources.store_item_ayran
import khazena.feature.store.presentation.generated.resources.store_item_chicken_kebab
import khazena.feature.store.presentation.generated.resources.store_item_kunafa
import khazena.feature.store.presentation.generated.resources.store_item_lentil_soup
import khazena.feature.store.presentation.generated.resources.store_item_mixed_grill
import khazena.feature.store.presentation.generated.resources.store_logo_azmar
import org.jetbrains.compose.resources.DrawableResource

// shortcut: store covers, logos and menu photos are bundled and looked up by key; other keys fall back to the shared
// place photos. Switch to Coil URLs when stores come from the API.
private val storeImages = mapOf(
    "cover_azmar" to Res.drawable.store_cover_azmar,
    "cover_shirin" to Res.drawable.store_cover_shirin,
    "cover_kebab" to Res.drawable.store_cover_kebab,
    "cover_naz" to Res.drawable.store_cover_naz,
    "logo_azmar" to Res.drawable.store_logo_azmar,
    "item_chicken_kebab" to Res.drawable.store_item_chicken_kebab,
    "item_mixed_grill" to Res.drawable.store_item_mixed_grill,
    "item_lentil_soup" to Res.drawable.store_item_lentil_soup,
    "item_kunafa" to Res.drawable.store_item_kunafa,
    "item_ayran" to Res.drawable.store_item_ayran,
)

internal fun storeImage(key: String): DrawableResource = storeImages[key] ?: demoPhoto(key) ?: Res.drawable.store_cover_azmar
