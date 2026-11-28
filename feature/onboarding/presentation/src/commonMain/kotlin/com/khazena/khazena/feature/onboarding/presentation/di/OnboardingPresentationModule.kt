package com.khazena.khazena.feature.onboarding.presentation.di

import com.khazena.khazena.feature.onboarding.presentation.intro.IntroViewModel
import com.khazena.khazena.feature.onboarding.presentation.splash.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingPresentationModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::IntroViewModel)
}
