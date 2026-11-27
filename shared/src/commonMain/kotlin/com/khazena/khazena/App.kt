package com.khazena.khazena

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaComponentGallery

@Composable
@Preview
fun App() {
    KhazenaTheme {
        KhazenaComponentGallery()
    }
}