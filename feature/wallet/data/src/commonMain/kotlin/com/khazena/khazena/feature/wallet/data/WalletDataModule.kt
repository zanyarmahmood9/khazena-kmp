package com.khazena.khazena.feature.wallet.data

import com.khazena.khazena.feature.wallet.domain.WalletRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val walletDataModule = module {
    singleOf(::FakeWalletRepository) bind WalletRepository::class
}
