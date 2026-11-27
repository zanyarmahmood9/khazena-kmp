package com.khazena.khazena

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaComponentGallery

@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
            .build()
    }
    KhazenaTheme {
        KhazenaComponentGallery()
    }
}
