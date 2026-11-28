package com.khazena.khazena

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.khazena.khazena.core.designsystem.KhazenaColors
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaLaunchMark
import com.khazena.khazena.core.presentation.language.ProvideAppLanguage
import com.khazena.khazena.navigation.AppNavHost
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(isDebug: Boolean = false) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
            .build()
    }
    val viewModel = koinViewModel<AppViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    val language = state.language
    // Both are read before the first screen, so the splash already knows where returning users go.
    if (language == null || state.onboarding == null) {
        Box(Modifier.fillMaxSize().background(KhazenaColors().blockEmber), contentAlignment = Alignment.Center) {
            KhazenaLaunchMark(Modifier.absoluteOffset(x = 6.dp))
        }
        return
    }
    ProvideAppLanguage(language) {
        KhazenaTheme(language.code) {
            // Screens cross-fade, so this shows through mid-transition; without it the platform's white/black window flashes.
            Box(Modifier.fillMaxSize().background(KhazenaTheme.colors.surfaceBackground)) {
                val onboarding by rememberUpdatedState(state.onboarding)
                AppNavHost(
                    navController = navController,
                    isSignedIn = state.isSignedIn,
                    isDebug = isDebug,
                    onboarding = { onboarding },
                    onIntroCompleted = viewModel::onIntroCompleted,
                    onSideEntered = viewModel::onSideEntered,
                )
            }
        }
    }
}
