package com.khazena.khazena.core.data.demo

import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.domain.demo.DemoDataMode
import com.khazena.khazena.core.domain.demo.DemoDataSettings
import kotlin.time.Duration.Companion.milliseconds
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

private val DEMO_LATENCY = 900.milliseconds

suspend fun <T> DemoDataSettings.demoResult(empty: T, data: () -> T): Result<T, DataError> {
    delay(DEMO_LATENCY)
    return when (mode.value) {
        DemoDataMode.Normal -> Result.Success(data())
        DemoDataMode.Empty -> Result.Success(empty)
        DemoDataMode.Error -> Result.Error(DataError.Network.NO_INTERNET)
    }
}
