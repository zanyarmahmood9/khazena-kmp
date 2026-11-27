package com.khazena.khazena.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.ButtonVariant
import com.khazena.khazena.core.designsystem.component.IconButtonStyle
import com.khazena.khazena.core.designsystem.component.KhazenaBottomSheet
import com.khazena.khazena.core.designsystem.component.KhazenaButton
import com.khazena.khazena.core.designsystem.component.KhazenaIconButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaLogo
import khazena.core.presentation.generated.resources.close
import khazena.shared.generated.resources.Res
import khazena.shared.generated.resources.account_body
import khazena.shared.generated.resources.account_kicker
import khazena.shared.generated.resources.account_terms
import khazena.shared.generated.resources.account_title
import khazena.shared.generated.resources.sign_in_apple
import khazena.shared.generated.resources.sign_in_google
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import khazena.core.presentation.generated.resources.Res as CoreRes

@Composable
internal fun AccountRequiredSheet(onDismiss: () -> Unit, onSignIn: () -> Unit) {
    KhazenaBottomSheet(onDismiss = onDismiss) {
        AccountRequiredContent(onDismiss, onSignIn)
    }
}

@Composable
private fun ColumnScope.AccountRequiredContent(onDismiss: () -> Unit, onSignIn: () -> Unit) {
    val colors = KhazenaTheme.colors
    Box(Modifier.fillMaxWidth().height(140.dp).background(colors.blockViolet, KhazenaRadius.block)) {
        Image(painterResource(KhazenaLogo.MarkOnViolet), null, Modifier.size(72.dp).align(Alignment.Center))
        KhazenaIconButton(
            KhazenaIcons.X,
            stringResource(CoreRes.string.close),
            onDismiss,
            Modifier.align(Alignment.TopEnd).padding(10.dp),
            style = IconButtonStyle.White,
        )
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(stringResource(Res.string.account_kicker).uppercase(), style = KhazenaTheme.typography.monoKicker, color = colors.textSecondary)
        Text(
            stringResource(Res.string.account_title),
            style = KhazenaTheme.typography.headingH2,
            color = colors.textPrimary,
            modifier = Modifier.semantics { heading() },
        )
        Text(stringResource(Res.string.account_body), style = KhazenaTheme.typography.bodyM, color = colors.textSecondary)
    }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        KhazenaButton(stringResource(Res.string.sign_in_apple), onSignIn, Modifier.fillMaxWidth(), variant = ButtonVariant.Dark)
        KhazenaButton(stringResource(Res.string.sign_in_google), onSignIn, Modifier.fillMaxWidth(), variant = ButtonVariant.Secondary)
    }
    Text(
        stringResource(Res.string.account_terms),
        style = KhazenaTheme.typography.bodyS,
        color = colors.textSecondary,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Preview(widthDp = 390)
@Composable
private fun AccountRequiredPreview() = KhazenaTheme {
    Column(
        Modifier.background(KhazenaTheme.colors.surfaceBackground).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) { AccountRequiredContent({}, {}) }
}
