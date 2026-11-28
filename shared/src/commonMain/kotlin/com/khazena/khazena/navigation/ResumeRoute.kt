package com.khazena.khazena.navigation

import com.khazena.khazena.core.domain.onboarding.AppSide
import com.khazena.khazena.core.domain.onboarding.OnboardingProgress
import com.khazena.khazena.feature.chooseside.presentation.ChooseSideRoute

// Where the splash sends a returning user; null means a first launch, which goes through the intro.
internal fun OnboardingProgress?.resumeRoute(): Any? = when {
    this == null -> null
    side == AppSide.Customer -> CustomerRoute
    side == AppSide.Business -> BusinessRoute
    introDone -> ChooseSideRoute
    else -> null
}
