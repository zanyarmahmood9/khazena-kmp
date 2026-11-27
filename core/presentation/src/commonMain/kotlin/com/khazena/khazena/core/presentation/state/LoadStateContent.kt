package com.khazena.khazena.core.presentation.state

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.khazena.khazena.core.designsystem.KhazenaMotion
import com.khazena.khazena.core.designsystem.component.ButtonVariant
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaSkeletonList
import com.khazena.khazena.core.designsystem.component.KhazenaStateCard
import com.khazena.khazena.core.designsystem.component.StateTone
import com.khazena.khazena.core.designsystem.revealOnAppear
import khazena.core.presentation.generated.resources.Res
import khazena.core.presentation.generated.resources.loading
import khazena.core.presentation.generated.resources.state_error_badge
import khazena.core.presentation.generated.resources.state_error_title
import khazena.core.presentation.generated.resources.try_again
import org.jetbrains.compose.resources.stringResource

@Composable
fun <T> LoadStateContent(
    state: LoadState<T>,
    onRetry: () -> Unit,
    empty: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    loading: @Composable () -> Unit = { KhazenaSkeletonList(stringResource(Res.string.loading)) },
    content: @Composable (T) -> Unit,
) {
    AnimatedContent(
        targetState = state,
        modifier = modifier,
        contentKey = { it::class },
        contentAlignment = Alignment.TopCenter,
        transitionSpec = {
            val enter = fadeIn(tween(KhazenaMotion.MEDIUM, delayMillis = 60, easing = KhazenaMotion.EmphasizedDecelerate)) +
                slideInVertically(tween(KhazenaMotion.LONG, easing = KhazenaMotion.EmphasizedDecelerate)) { it / 16 } +
                scaleIn(tween(KhazenaMotion.LONG, easing = KhazenaMotion.EmphasizedDecelerate), initialScale = 0.98f)
            (enter togetherWith fadeOut(tween(KhazenaMotion.SHORT, easing = KhazenaMotion.EmphasizedAccelerate)))
                .using(SizeTransform(clip = false))
        },
        label = "LoadState",
    ) { target ->
        Box(Modifier.fillMaxWidth()) {
            when (target) {
                LoadState.Loading -> loading()
                LoadState.Empty -> Box(Modifier.revealOnAppear()) { empty() }
                is LoadState.Error -> ErrorState(target, onRetry)
                is LoadState.Data -> content(target.value)
            }
        }
    }
}

@Composable
private fun ErrorState(state: LoadState.Error, onRetry: () -> Unit) {
    KhazenaStateCard(
        icon = KhazenaIcons.Bell,
        badge = stringResource(Res.string.state_error_badge),
        title = stringResource(Res.string.state_error_title),
        body = state.message.asString(),
        modifier = Modifier.revealOnAppear(),
        tone = StateTone.Ember,
        actionLabel = stringResource(Res.string.try_again),
        actionVariant = ButtonVariant.Dark,
        onAction = onRetry,
    )
}
