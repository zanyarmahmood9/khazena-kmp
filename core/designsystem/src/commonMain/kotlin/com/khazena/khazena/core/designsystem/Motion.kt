package com.khazena.khazena.core.designsystem

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

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

// iOS navigation push: the new screen slides in from the trailing edge while the one below
// drifts 30% the other way; back reverses it. Direction follows the layout (mirrors in RTL).
private val PushEasing = CubicBezierEasing(0.2f, 0.9f, 0.3f, 1f)
private const val PUSH_DURATION = 420
private const val PUSH_PARALLAX = 0.3f

fun <S> AnimatedContentTransitionScope<S>.pushEnter(): EnterTransition =
    slideIntoContainer(SlideDirection.Start, tween(PUSH_DURATION, easing = PushEasing))

fun <S> AnimatedContentTransitionScope<S>.pushExit(): ExitTransition =
    slideOutOfContainer(SlideDirection.Start, tween(PUSH_DURATION, easing = PushEasing)) { (it * PUSH_PARALLAX).toInt() }

fun <S> AnimatedContentTransitionScope<S>.pushPopEnter(): EnterTransition =
    slideIntoContainer(SlideDirection.End, tween(PUSH_DURATION, easing = PushEasing)) { (it * PUSH_PARALLAX).toInt() }

fun <S> AnimatedContentTransitionScope<S>.pushPopExit(): ExitTransition =
    slideOutOfContainer(SlideDirection.End, tween(PUSH_DURATION, easing = PushEasing))

// Skeleton → content (and any loading-state swap): a soft cross-fade in place, from the same top edge, with no
// slide or scale; rows that use revealOnAppear add their own gentle rise. The container takes the new height at
// once (no resize wobble). Skeletons match their content's geometry, so nothing shifts as it fades.
private val RevealEasing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)

fun <S> AnimatedContentTransitionScope<S>.softReveal(): ContentTransform =
    // A true cross-fade: same duration, same curve, no delay, so the two opacities always add up to one. Never a
    // blank frame (the white hero showing empty), and the overlap is short enough not to read as ghosting.
    (fadeIn(tween(KhazenaMotion.MEDIUM, easing = RevealEasing)) togetherWith fadeOut(tween(KhazenaMotion.MEDIUM, easing = RevealEasing)))
        .using(SizeTransform(clip = false) { _, _ -> snap() })

// Content that follows a row of chips or segments: moving to a later one brings the new content in from the
// trailing side, an earlier one from the leading side (mirrored in RTL); the same position just cross-fades.
fun <S> AnimatedContentTransitionScope<S>.slideByIndex(from: Int, to: Int, rtl: Boolean): ContentTransform {
    if (from == to) return fadeIn(tween(KhazenaMotion.MEDIUM)) togetherWith fadeOut(tween(KhazenaMotion.SHORT))
    val fromEnd = (to > from) != rtl
    val slide = tween<IntOffset>(KhazenaMotion.LONG, easing = KhazenaMotion.EmphasizedDecelerate)
    return (slideInHorizontally(slide) { if (fromEnd) it / 3 else -it / 3 } + fadeIn(tween(KhazenaMotion.MEDIUM))) togetherWith
        (slideOutHorizontally(slide) { if (fromEnd) -it / 3 else it / 3 } + fadeOut(tween(KhazenaMotion.SHORT))) using
        SizeTransform(clip = false)
}
