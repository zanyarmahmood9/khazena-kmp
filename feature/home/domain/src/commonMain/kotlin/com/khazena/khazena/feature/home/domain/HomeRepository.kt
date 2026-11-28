package com.khazena.khazena.feature.home.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result

interface HomeRepository {
    suspend fun getHomeFeed(): Result<HomeFeed, DataError>
    suspend fun getCategories(): Result<CategoryDirectory, DataError>
    suspend fun getCategory(id: String): Result<CategoryDetail, DataError>
}
