package com.khazena.khazena.core.data.demo

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataMode
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryDemoDataSettings : DemoDataSettings {
    private val _mode = MutableStateFlow(DemoDataMode.Normal)
    override val mode: StateFlow<DemoDataMode> = _mode.asStateFlow()
    override fun setMode(mode: DemoDataMode) {
        _mode.value = mode
    }
}

private const val DEMO_LATENCY_MS = 900L

suspend fun <T> DemoDataSettings.demoResult(empty: T, data: () -> T): Result<T, DataError> {
    delay(DEMO_LATENCY_MS)
    return when (mode.value) {
        DemoDataMode.Normal -> Result.Success(data())
        DemoDataMode.Empty -> Result.Success(empty)
        DemoDataMode.Error -> Result.Error(DataError.Network.NO_INTERNET)
    }
}
