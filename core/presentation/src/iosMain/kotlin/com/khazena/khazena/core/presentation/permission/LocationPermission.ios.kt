package com.khazena.khazena.core.presentation.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.darwin.NSObject

@Composable
actual fun rememberLocationPermission(): LocationPermission {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val permission = remember(lifecycle) { IosLocationPermission(lifecycle) }
    LifecycleResumeEffect(permission) {
        permission.refresh()
        permission.pending.deliver()
        onPauseOrDispose {}
    }
    return permission
}

private class IosLocationPermission(private val lifecycle: Lifecycle) : LocationPermission {
    private val manager = CLLocationManager()

    private val delegate = AuthorizationDelegate(::refresh)
    private val state = mutableStateOf(LocationPermissionStatus.NotDetermined)
    val pending = PendingPermissionResult()

    init {
        manager.delegate = delegate
        state.value = read()
    }

    override val status get() = state.value

    override fun request(onResult: (granted: Boolean) -> Unit) {
        if (state.value != LocationPermissionStatus.NotDetermined) {
            onResult(state.value == LocationPermissionStatus.Granted)
            return
        }
        pending.await(onResult)
        manager.requestWhenInUseAuthorization()
    }

    fun refresh() {
        state.value = read()
        if (state.value == LocationPermissionStatus.NotDetermined) return
        pending.complete(state.value == LocationPermissionStatus.Granted, lifecycle)
    }

    private fun read(): LocationPermissionStatus = when (manager.authorizationStatus) {
        kCLAuthorizationStatusNotDetermined -> LocationPermissionStatus.NotDetermined
        kCLAuthorizationStatusAuthorizedWhenInUse, kCLAuthorizationStatusAuthorizedAlways -> LocationPermissionStatus.Granted
        else -> LocationPermissionStatus.Denied
    }
}

private class AuthorizationDelegate(private val onChange: () -> Unit) : NSObject(), CLLocationManagerDelegateProtocol {
    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) = onChange()
}