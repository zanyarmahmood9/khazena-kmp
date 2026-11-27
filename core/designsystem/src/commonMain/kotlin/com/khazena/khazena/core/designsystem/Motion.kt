package com.khazena.khazena.core.designsystem

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

object KhazenaMotion {
    val EmphasizedDecelerate = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)
    val EmphasizedAccelerate = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f)
    const val SHORT = 150
    const val MEDIUM = 300
    const val LONG = 450
    const val STAGGER = 45
    const val MAX_STAGGER = 360
}

fun Modifier.revealOnAppear(index: Int = 0): Modifier = composed {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        delay((index * KhazenaMotion.STAGGER).coerceAtMost(KhazenaMotion.MAX_STAGGER).toLong())
        progress.animateTo(1f, tween(KhazenaMotion.LONG, easing = KhazenaMotion.EmphasizedDecelerate))
    }
    graphicsLayer {
        alpha = progress.value
        translationY = (1f - progress.value) * 20.dp.toPx()
    }
}
