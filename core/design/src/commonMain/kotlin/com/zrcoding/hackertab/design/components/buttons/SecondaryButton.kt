package com.zrcoding.hackertab.design.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    size: ButtonSize = ButtonSize.Medium,
    isLoading: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = if (size == ButtonSize.Large) CircleShape else MaterialTheme.shapes.medium
    val borderColor = MaterialTheme.colorScheme.outline
        .copy(alpha = if (enabled) 1f else 0.4f)
    val contentColor = MaterialTheme.colorScheme.onSurface
        .copy(alpha = if (enabled) 1f else 0.4f)
    val colors = ButtonDefaults.outlinedButtonColors(
        containerColor = Color.Transparent,
        contentColor = contentColor,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = contentColor,
    )

    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .heightIn(min = size.height)
            .pressScale(interactionSource)
            .semantics { role = Role.Button },
        enabled = enabled && !isLoading,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        border = BorderStroke(1.dp, borderColor),
        contentPadding = PaddingValues(horizontal = size.horizontalPadding, vertical = 0.dp),
    ) {
        CompositionLocalProvider(LocalTextStyle provides size.textStyle()) {
            ButtonContentRow(
                text = text,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                iconSize = size.iconSize,
                isLoading = isLoading,
                contentColor = contentColor,
            )
        }
    }
}

@Preview
@Composable
private fun SecondaryButtonPreview_Default() {
    HackertabTheme {
        SecondaryButton(text = "Cancel", onClick = {})
    }
}

@Preview
@Composable
private fun SecondaryButtonPreview_Disabled() {
    HackertabTheme {
        SecondaryButton(text = "Cancel", onClick = {}, enabled = false)
    }
}

@Preview
@Composable
private fun SecondaryButtonPreview_Loading() {
    HackertabTheme {
        SecondaryButton(text = "Cancel", onClick = {}, isLoading = true)
    }
}
