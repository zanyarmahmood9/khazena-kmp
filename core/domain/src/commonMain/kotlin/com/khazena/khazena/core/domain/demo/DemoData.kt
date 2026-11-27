package com.khazena.khazena.core.domain.demo

import kotlinx.coroutines.flow.StateFlow

enum class DemoDataMode { Normal, Empty, Error }

interface DemoDataSettings {
    val mode: StateFlow<DemoDataMode>
    fun setMode(mode: DemoDataMode)
}
