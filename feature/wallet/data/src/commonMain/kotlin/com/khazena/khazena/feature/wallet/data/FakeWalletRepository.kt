package com.khazena.khazena.feature.wallet.data

import com.khazena.khazena.core.data.demo.DemoCatalog
import com.khazena.khazena.core.data.demo.demoResult
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.LocalizedText
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import com.khazena.khazena.feature.wallet.domain.Saving
import com.khazena.khazena.feature.wallet.domain.Wallet
import com.khazena.khazena.feature.wallet.domain.WalletRepository

class FakeWalletRepository(private val demo: DemoDataSettings) : WalletRepository {
    private val plan = LocalizedText(ckb = "ئەندامێتی ساڵانە", en = "1-Year Premium", ar = "بريميوم سنوي")

    override suspend fun getWallet(): Result<Wallet, DataError> = demo.demoResult(
        empty = Wallet(DemoCatalog.members.first(), plan, "12 Oct 2027", 0, 0, emptyList()),
    ) {
        val history = DemoCatalog.merchants.mapIndexed { index, merchant ->
            Saving("s$index", merchant.name, DemoCatalog.ago((index + 1) * 47), (index + 2) * 2_500L)
        }
        Wallet(DemoCatalog.members.first(), plan, "12 Oct 2027", history.sumOf { it.savedIqd } + 160_000, 23, history)
    }
}
