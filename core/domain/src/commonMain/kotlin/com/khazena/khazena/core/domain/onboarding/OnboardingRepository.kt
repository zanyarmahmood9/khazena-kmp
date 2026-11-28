package com.khazena.khazena.core.domain.onboarding

import kotlinx.coroutines.flow.Flow

enum class AppSide { Customer, Business }

data class OnboardingProgress(
    val introDone: Boolean = false,
    val side: AppSide? = null,
)

interface OnboardingRepository {
    val progress: Flow<OnboardingProgress>
    suspend fun completeIntro()

    /** The side the user actually landed on; it also means the intro is done. */
    suspend fun enterSide(side: AppSide)
}
