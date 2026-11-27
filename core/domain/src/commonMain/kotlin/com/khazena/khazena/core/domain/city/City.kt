package com.khazena.khazena.core.domain.city

import com.khazena.khazena.core.domain.LocalizedText

/** A city members can pick (`public.cities`). Shared by onboarding, home, search, profile and the business store details. */
data class City(
    val id: String,
    val name: LocalizedText,
    val slug: String,
)
