package com.khazena.khazena.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource

internal class ClickThrottle(private val window: Duration, private val timeSource: TimeSource = TimeSource.Monotonic) {
    private var last: TimeMark? = null

    fun tryAcquire(): Boolean {
        if (last?.let { it.elapsedNow() < window } == true) return false
        last = timeSource.markNow()
        return true
    }
}

// Drops repeat taps inside the window so a double tap can't fire an action (or a navigation) twice.
@Composable
fun rememberThrottledClick(onClick: () -> Unit, window: Duration = 500.milliseconds): () -> Unit {
    val latest by rememberUpdatedState(onClick)
    val throttle = remember(window) { ClickThrottle(window) }
    return remember(throttle) { { if (throttle.tryAcquire()) latest() } }
}
