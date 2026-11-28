package com.khazena.khazena.feature.home.presentation

import kotlin.test.Test
import kotlin.test.assertEquals

class DaypartTest {
    @Test
    fun greetingFollowsTheLocalHour() {
        assertEquals(Daypart.Evening, daypartAt(4))
        assertEquals(Daypart.Morning, daypartAt(5))
        assertEquals(Daypart.Morning, daypartAt(11))
        assertEquals(Daypart.Afternoon, daypartAt(12))
        assertEquals(Daypart.Afternoon, daypartAt(16))
        assertEquals(Daypart.Evening, daypartAt(17))
        assertEquals(Daypart.Evening, daypartAt(23))
    }
}
