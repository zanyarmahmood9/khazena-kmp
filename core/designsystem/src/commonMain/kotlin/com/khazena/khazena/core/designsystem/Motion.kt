package com.khazena.khazena.core.designsystem

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlin.time.Duration.Companion.milliseconds
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
        delay((index * KhazenaMotion.STAGGER).coerceAtMost(KhazenaMotion.MAX_STAGGER).milliseconds)
        progress.animateTo(1f, tween(KhazenaMotion.LONG, easing = KhazenaMotion.EmphasizedDecelerate))
    }
    graphicsLayer {
        alpha = progress.value
        translationY = (1f - progress.value) * 20.dp.toPx()
    }
}

// iOS-style press: eases down quickly while held, springs back softly on release.
fun Modifier.pressScale(interactionSource: InteractionSource, pressedScale: Float = 0.96f): Modifier = composed {
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) pressedScale else 1f,
        animationSpec = if (pressed) {
            tween(durationMillis = 120, easing = FastOutSlowInEasing)
        } else {
            spring(dampingRatio = 0.55f, stiffness = Spring.StiffnessMediumLow)
        },
        label = "press-scale",
    )
    graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}
