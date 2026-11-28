package com.khazena.khazena.core.presentation.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable

enum class LocationPermissionStatus { NotDetermined, Granted, Denied }

@Stable
interface LocationPermission {
    val status: LocationPermissionStatus

    fun request(onResult: (granted: Boolean) -> Unit = {})

    /** For a "Turn on location" button: asks while the system still allows it, otherwise opens the app's settings. */
    fun enable()
}

@Composable
expect fun rememberLocationPermission(): LocationPermission

@Composable
fun AskLocationOnce(): LocationPermission {
    val location = rememberLocationPermission()
    LaunchedEffect(location) {
        if (location.status == LocationPermissionStatus.NotDetermined) location.request()
    }
    return location
}
