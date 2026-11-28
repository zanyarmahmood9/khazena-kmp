package com.khazena.khazena.core.presentation.state

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaMotion
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.component.KhazenaToast
import com.khazena.khazena.core.presentation.UiText
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

private val MESSAGE_DURATION = 3.seconds

class MessageToastState {
    var message by mutableStateOf<UiText?>(null)
        private set
    var visible by mutableStateOf(false)
        private set

    fun show(message: UiText) {
        this.message = message
        visible = true
    }

    fun hide() {
        visible = false
    }
}

@Composable
fun rememberMessageToastState() = remember { MessageToastState() }

@Composable
fun MessageToastHost(state: MessageToastState, modifier: Modifier = Modifier) {
    LaunchedEffect(state.message, state.visible) {
        if (state.visible) {
            delay(MESSAGE_DURATION)
            state.hide()
        }
    }
    AnimatedVisibility(
        visible = state.visible,
        modifier = modifier,
        enter = fadeIn(tween(KhazenaMotion.MEDIUM)) +
            slideInVertically(tween(KhazenaMotion.LONG, easing = KhazenaMotion.EmphasizedDecelerate)) { it },
        exit = fadeOut(tween(KhazenaMotion.SHORT)) +
            slideOutVertically(tween(KhazenaMotion.MEDIUM, easing = KhazenaMotion.EmphasizedAccelerate)) { it },
    ) {
        state.message?.let {
            KhazenaToast(
                title = it.asString(),
                modifier = Modifier
                    .padding(horizontal = KhazenaSpacing.screenMargin, vertical = 8.dp)
                    .semantics { liveRegion = LiveRegionMode.Polite },
            )
        }
    }
}
