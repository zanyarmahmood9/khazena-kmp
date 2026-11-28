package com.khazena.khazena.core.presentation.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LocalLifecycleOwner

private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
private const val PREFS = "khazena_permissions"

private const val ASKED_KEY = "location_asked"

@Composable
actual fun rememberLocationPermission(): LocationPermission {
    val context = LocalContext.current.applicationContext
    val activity = LocalActivity.current
    val status = remember { mutableStateOf(context.locationStatus()) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val pending = remember { PendingPermissionResult() }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        val granted = result.values.any { it }
        status.value = if (granted) LocationPermissionStatus.Granted else LocationPermissionStatus.Denied
        pending.complete(granted, lifecycle)
    }

    LifecycleResumeEffect(Unit) {
        status.value = context.locationStatus()
        pending.deliver()
        onPauseOrDispose {}
    }
    return remember(launcher, activity) {
        object : LocationPermission {
            override val status get() = status.value

            override fun request(onResult: (granted: Boolean) -> Unit) {
                if (status.value != LocationPermissionStatus.NotDetermined) {
                    onResult(status.value == LocationPermissionStatus.Granted)
                    return
                }
                context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit { putBoolean(ASKED_KEY, true) }
                pending.await(onResult)
                launcher.launch(PERMISSIONS)
            }

            override fun enable() {
                when {
                    status.value == LocationPermissionStatus.NotDetermined -> request {}
                    status.value == LocationPermissionStatus.Granted -> Unit
                    // Android lets the app ask again after a single "Don't allow"; after that only Settings can change it.
                    activity != null && PERMISSIONS.any { activity.shouldShowRequestPermissionRationale(it) } -> launcher.launch(PERMISSIONS)
                    else -> context.startActivity(
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", context.packageName, null))
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                    )
                }
            }
        }
    }
}

private fun Context.locationStatus(): LocationPermissionStatus = when {
    PERMISSIONS.any { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED } -> LocationPermissionStatus.Granted
    getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(ASKED_KEY, false) -> LocationPermissionStatus.Denied
    else -> LocationPermissionStatus.NotDetermined
}