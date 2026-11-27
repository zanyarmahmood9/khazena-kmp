package com.khazena.khazena.feature.onboarding.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.KhazenaLogo
import com.khazena.khazena.core.designsystem.component.KhazenaWordmark
import khazena.feature.onboarding.presentation.generated.resources.Res
import khazena.feature.onboarding.presentation.generated.resources.skip
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal const val ONBOARDING_STEPS = 3

@Composable
internal fun OnboardingTopBar(step: Int, onSkip: (() -> Unit)?, skipLabel: StringResource = Res.string.skip) {
    val colors = KhazenaTheme.colors
    Box(Modifier.fillMaxWidth().height(56.dp).padding(horizontal = KhazenaSpacing.screenMargin)) {
        Row(
            Modifier.align(Alignment.CenterStart).clearAndSetSemantics {},
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            repeat(ONBOARDING_STEPS) { index ->
                val width by animateDpAsState(if (index == step) 28.dp else 8.dp)
                Box(
                    Modifier.size(width, 8.dp).background(
                        if (index == step) colors.textPrimary else colors.borderStrong,
                        KhazenaRadius.pill,
                    ),
                )
            }
        }
        Row(
            Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painterResource(KhazenaLogo.MarkOnPaper), null, Modifier.width(16.dp).height(18.dp))
            KhazenaWordmark(KhazenaTheme.typography.wordmarkS, colors.textPrimary)
        }
        if (onSkip != null) {
            Text(
                stringResource(skipLabel),
                style = KhazenaTheme.typography.labelMSemiBold.copy(textDecoration = TextDecoration.Underline),
                color = colors.textPrimary,
                modifier = Modifier.align(Alignment.CenterEnd).clickable(role = Role.Button, onClick = onSkip).padding(8.dp),
            )
        }
    }
}
