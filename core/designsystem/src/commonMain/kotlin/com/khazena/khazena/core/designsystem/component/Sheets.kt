package com.khazena.khazena.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaSpacing
import com.khazena.khazena.core.designsystem.KhazenaTheme

private val SheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KhazenaBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = KhazenaTheme.colors
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = SheetShape,
        containerColor = colors.surfaceBackground,
        scrimColor = colors.blockInk.copy(alpha = 0.45f),
        dragHandle = { SheetHandle() },
    ) {
        SheetContent(content)
    }
}

@Composable
fun KhazenaSheetTitle(
    title: String,
    closeLabel: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                title,
                style = KhazenaTheme.typography.headingH4,
                color = KhazenaTheme.colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
            if (subtitle != null) Text(subtitle, style = KhazenaTheme.typography.bodyS, color = KhazenaTheme.colors.textSecondary)
        }
        KhazenaIconButton(KhazenaIcons.X, closeLabel, onClose, style = IconButtonStyle.White)
    }
}

@Composable
private fun SheetHandle() = Box(
    Modifier.padding(top = 10.dp, bottom = 6.dp).size(width = 40.dp, height = 5.dp)
        .background(KhazenaTheme.colors.borderDefault, KhazenaRadius.pill),
)

@Composable
private fun SheetContent(content: @Composable ColumnScope.() -> Unit) = Column(
    Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(horizontal = KhazenaSpacing.screenMargin)
        .padding(top = 6.dp, bottom = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    content = content,
)

@Composable
private fun SheetPreviewFrame(content: @Composable ColumnScope.() -> Unit) = Column(
    Modifier.background(KhazenaTheme.colors.surfaceBackground, SheetShape),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    SheetHandle()
    SheetContent(content)
}

@Preview(name = "Sheet", widthDp = 390)
@Composable
private fun SheetPreview() = PreviewSurface {
    SheetPreviewFrame {
        KhazenaSheetTitle("App language", "Close", {}, subtitle = "Choose how Khazena speaks to you")
        KhazenaButton("Apply", {}, Modifier.fillMaxWidth())
    }
}

@Preview(name = "Sheet · RTL", widthDp = 390)
@Composable
private fun SheetRtlPreview() = RtlPreviewSurface {
    SheetPreviewFrame {
        KhazenaSheetTitle("زمانی ئەپ", "داخستن", {}, subtitle = "هەڵبژێرە خەزێنە بە چ زمانێک قسەت لەگەڵ بکات")
        KhazenaButton("جێبەجێکردن", {}, Modifier.fillMaxWidth())
    }
}
