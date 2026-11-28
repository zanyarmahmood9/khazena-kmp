package com.khazena.khazena.feature.profile.domain

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result

data class UserProfile(
    val name: String,
    val phone: String,
    val city: LocalizedText,
    val planName: LocalizedText?,
)

interface ProfileRepository {
    suspend fun getProfile(): Result<UserProfile?, DataError>
}
