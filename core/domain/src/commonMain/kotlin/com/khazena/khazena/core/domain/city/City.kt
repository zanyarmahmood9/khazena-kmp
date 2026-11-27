package com.khazena.khazena.core.domain.city

import com.khazena.khazena.core.domain.LocalizedText

data class City(
    val id: String,
    val name: LocalizedText,
    val slug: String,
)
