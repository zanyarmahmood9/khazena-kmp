package com.khazena.khazena.core.presentation.permission

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import kotlin.test.Test
import kotlin.test.assertEquals

class PendingPermissionResultTest {
    private val owner = object : LifecycleOwner {
        val registry = LifecycleRegistry.createUnsafe(this)
        override val lifecycle: Lifecycle get() = registry
    }

    @Test
    fun answerArrivingBeforeResumeIsHeldUntilResumedAndDeliveredOnce() {
        val pending = PendingPermissionResult()
        val received = mutableListOf<Boolean>()
        owner.registry.currentState = Lifecycle.State.RESUMED
        pending.await { received += it }

        owner.registry.currentState = Lifecycle.State.STARTED
        pending.complete(false, owner.lifecycle)
        assertEquals(emptyList(), received)

        owner.registry.currentState = Lifecycle.State.RESUMED
        pending.deliver()
        pending.deliver()
        assertEquals(listOf(false), received)
    }

    @Test
    fun answerArrivingWhileResumedIsDeliveredImmediately() {
        val pending = PendingPermissionResult()
        val received = mutableListOf<Boolean>()
        owner.registry.currentState = Lifecycle.State.RESUMED
        pending.await { received += it }
        pending.complete(true, owner.lifecycle)
        assertEquals(listOf(true), received)
    }
}
