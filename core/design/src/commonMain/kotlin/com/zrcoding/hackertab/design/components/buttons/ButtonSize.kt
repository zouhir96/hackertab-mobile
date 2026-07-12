package com.zrcoding.hackertab.design.components.buttons

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val iconSize: Dp,
) {
    Small(height = 32.dp, horizontalPadding = 14.dp, iconSize = 16.dp),
    Medium(height = 42.dp, horizontalPadding = 18.dp, iconSize = 18.dp),
    Large(height = 54.dp, horizontalPadding = 22.dp, iconSize = 20.dp);

    @Composable
    fun textStyle(): TextStyle = when (this) {
        Small -> MaterialTheme.typography.labelMedium
        Medium -> MaterialTheme.typography.labelLarge
        Large -> MaterialTheme.typography.titleMedium
    }
}
