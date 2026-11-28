package com.khazena.khazena.core.domain.language

import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    val language: Flow<AppLanguage>
    suspend fun setLanguage(language: AppLanguage)
}
