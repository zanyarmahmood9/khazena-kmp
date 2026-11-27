package com.khazena.khazena.feature.wallet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.wallet.domain.WalletRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class WalletViewModel(repository: WalletRepository) : ViewModel() {

    private val eventChannel = Channel<WalletEvent>()
    val events = eventChannel.receiveAsFlow()

    private val loader = ContentLoader(
        scope = viewModelScope,
        isEmpty = { it.history.isEmpty() },
        onRefreshFailed = { eventChannel.send(WalletEvent.ShowMessage(it)) },
        fetch = repository::getWallet,
    )

    val state = loader.state
        .map { WalletState(content = it) }
        .onStart { loader.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), WalletState())

    fun onAction(action: WalletAction) {
        when (action) {
            WalletAction.OnRetry -> loader.load()
            WalletAction.OnRefresh -> loader.refresh()
        }
    }
}
