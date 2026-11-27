package com.khazena.khazena.di

import com.khazena.khazena.core.data.di.coreDataModule
import com.khazena.khazena.feature.business.home.data.businessHomeDataModule
import com.khazena.khazena.feature.business.home.presentation.di.businessHomePresentationModule
import com.khazena.khazena.feature.business.profile.data.storeDataModule
import com.khazena.khazena.feature.business.profile.presentation.di.storePresentationModule
import com.khazena.khazena.feature.business.reports.data.reportsDataModule
import com.khazena.khazena.feature.business.reports.presentation.di.reportsPresentationModule
import com.khazena.khazena.feature.business.scan.data.scanMemberDataModule
import com.khazena.khazena.feature.business.scan.presentation.di.scanMemberPresentationModule
import com.khazena.khazena.feature.business.services.data.servicesDataModule
import com.khazena.khazena.feature.business.services.presentation.di.servicesPresentationModule
import com.khazena.khazena.feature.home.data.homeDataModule
import com.khazena.khazena.feature.home.presentation.di.homePresentationModule
import com.khazena.khazena.feature.onboarding.presentation.di.onboardingPresentationModule
import com.khazena.khazena.feature.profile.data.profileDataModule
import com.khazena.khazena.feature.profile.presentation.di.profilePresentationModule
import com.khazena.khazena.feature.scanme.data.scanMeDataModule
import com.khazena.khazena.feature.scanme.presentation.di.scanMePresentationModule
import com.khazena.khazena.feature.search.data.searchDataModule
import com.khazena.khazena.feature.search.presentation.di.searchPresentationModule
import com.khazena.khazena.feature.wallet.data.walletDataModule
import com.khazena.khazena.feature.wallet.presentation.di.walletPresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            appModule,
            onboardingPresentationModule,
            homeDataModule, homePresentationModule,
            searchDataModule, searchPresentationModule,
            walletDataModule, walletPresentationModule,
            profileDataModule, profilePresentationModule,
            scanMeDataModule, scanMePresentationModule,
            businessHomeDataModule, businessHomePresentationModule,
            servicesDataModule, servicesPresentationModule,
            reportsDataModule, reportsPresentationModule,
            storeDataModule, storePresentationModule,
            scanMemberDataModule, scanMemberPresentationModule,
        )
    }
}
