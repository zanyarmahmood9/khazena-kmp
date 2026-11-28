package com.khazena.khazena.core.presentation.permission

import androidx.lifecycle.Lifecycle

internal class PendingPermissionResult {
    private var callback: ((Boolean) -> Unit)? = null
    private var result: Boolean? = null

    fun await(onResult: (Boolean) -> Unit) {
        callback = onResult
        result = null
    }

    fun complete(granted: Boolean, lifecycle: Lifecycle) {
        if (callback == null) return
        result = granted
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) deliver()
    }

    fun deliver() {
        val onResult = callback ?: return
        val granted = result ?: return
        callback = null
        result = null
        onResult(granted)
    }
}