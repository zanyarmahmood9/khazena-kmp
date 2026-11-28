package com.khazena.khazena

import com.khazena.khazena.core.domain.onboarding.AppSide
import com.khazena.khazena.core.domain.onboarding.OnboardingProgress
import com.khazena.khazena.feature.chooseside.presentation.ChooseSideRoute
import com.khazena.khazena.navigation.BusinessRoute
import com.khazena.khazena.navigation.CustomerRoute
import com.khazena.khazena.navigation.resumeRoute
import kotlin.test.Test
import kotlin.test.assertEquals

class ResumeRouteTest {
    @Test
    fun splashSendsUsersWhereTheyLeftOff() {
        assertEquals(null, OnboardingProgress().resumeRoute())
        assertEquals(ChooseSideRoute, OnboardingProgress(introDone = true).resumeRoute())
        assertEquals(CustomerRoute, OnboardingProgress(introDone = true, side = AppSide.Customer).resumeRoute())
        assertEquals(BusinessRoute, OnboardingProgress(introDone = true, side = AppSide.Business).resumeRoute())
    }
}
