package com.khazena.khazena.core.designsystem

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TestTimeSource

class ClickThrottleTest {
    @Test
    fun dropsRepeatTapsInsideTheWindowAndAllowsAfterIt() {
        val time = TestTimeSource()
        val throttle = ClickThrottle(500.milliseconds, time)
        val accepted = buildList {
            add(throttle.tryAcquire())
            time += 100.milliseconds
            add(throttle.tryAcquire())
            time += 399.milliseconds
            add(throttle.tryAcquire())
            time += 1.milliseconds
            add(throttle.tryAcquire())
        }
        assertEquals(listOf(true, false, false, true), accepted)
    }
}
