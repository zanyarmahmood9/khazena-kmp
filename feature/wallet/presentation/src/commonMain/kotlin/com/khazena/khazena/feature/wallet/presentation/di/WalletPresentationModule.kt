package com.khazena.khazena.feature.wallet.presentation.di

import com.khazena.khazena.feature.wallet.presentation.WalletViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val walletPresentationModule = module {
    viewModelOf(::WalletViewModel)
}
