package com.khazena.khazena

import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.graphics.Color
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlin.time.Duration.Companion.milliseconds

private const val LARGE_SCREEN_MIN_WIDTH_DP = 600
private val SPLASH_FADE = 350.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setOnExitAnimationListener { splash ->
            // The app's first frame draws the same mark in the same place, so a soft fade reads as one continuous screen.
            splash.view.animate()
                .alpha(0f)
                .setDuration(SPLASH_FADE.inWholeMilliseconds)
                .setInterpolator(DecelerateInterpolator())
                .withEndAction(splash::remove)
                .start()
        }
        // The app has a light theme only, so system bar icons stay dark even in system dark mode.
        val lightBars = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        enableEdgeToEdge(statusBarStyle = lightBars, navigationBarStyle = lightBars)
        super.onCreate(savedInstanceState)
        // Portrait on phones only; tablets, unfolded foldables and ChromeOS rotate freely. Re-evaluated when a foldable
        // opens or closes, since that recreates the activity.
        requestedOrientation = if (resources.configuration.smallestScreenWidthDp < LARGE_SCREEN_MIN_WIDTH_DP) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        val isDebug = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

        setContent {
            App(isDebug = isDebug)
        }
    }
}
