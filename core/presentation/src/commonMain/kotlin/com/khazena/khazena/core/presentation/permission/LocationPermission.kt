package com.khazena.khazena.core.presentation.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable

enum class LocationPermissionStatus { NotDetermined, Granted, Denied }

@Stable
interface LocationPermission {
    val status: LocationPermissionStatus

    fun request(onResult: (granted: Boolean) -> Unit = {})
}

@Composable
expect fun rememberLocationPermission(): LocationPermission

@Composable
fun AskLocationOnce() {
    val location = rememberLocationPermission()
    LaunchedEffect(location) {
        if (location.status == LocationPermissionStatus.NotDetermined) location.request()
    }
}
