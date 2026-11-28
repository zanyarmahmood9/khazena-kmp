package com.khazena.khazena

import androidx.compose.ui.window.ComposeUIViewController
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
fun MainViewController() = ComposeUIViewController { App(isDebug = kotlin.native.Platform.isDebugBinary) }
