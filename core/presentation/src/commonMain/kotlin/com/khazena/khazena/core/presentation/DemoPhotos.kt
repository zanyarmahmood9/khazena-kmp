package com.khazena.khazena.core.presentation

import khazena.core.presentation.generated.resources.Res
import khazena.core.presentation.generated.resources.demo_photo_azmar
import khazena.core.presentation.generated.resources.demo_photo_bazaar
import khazena.core.presentation.generated.resources.demo_photo_goizha
import khazena.core.presentation.generated.resources.demo_photo_kebab
import khazena.core.presentation.generated.resources.demo_photo_lavender
import khazena.core.presentation.generated.resources.demo_photo_naz
import khazena.core.presentation.generated.resources.demo_photo_shifa
import khazena.core.presentation.generated.resources.demo_photo_shirin
import org.jetbrains.compose.resources.DrawableResource

// shortcut: place photos are bundled and looked up by key, shared by every feature that shows places;
// switch to Coil URLs when places come from the API.
private val demoPhotos = mapOf(
    "photo_azmar" to Res.drawable.demo_photo_azmar,
    "photo_lavender" to Res.drawable.demo_photo_lavender,
    "photo_goizha" to Res.drawable.demo_photo_goizha,
    "photo_naz" to Res.drawable.demo_photo_naz,
    "photo_shirin" to Res.drawable.demo_photo_shirin,
    "photo_bazaar" to Res.drawable.demo_photo_bazaar,
    "photo_shifa" to Res.drawable.demo_photo_shifa,
    "photo_kebab" to Res.drawable.demo_photo_kebab,
)

fun demoPhoto(key: String): DrawableResource? = demoPhotos[key]
