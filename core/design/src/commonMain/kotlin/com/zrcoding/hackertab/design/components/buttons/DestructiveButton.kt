package com.zrcoding.hackertab.design.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
fun DestructiveButton(
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
    val contentColor = Color(0xFFFFFFFF)
    val colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = contentColor,
        disabledContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.4f),
        disabledContentColor = contentColor.copy(alpha = 0.6f),
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .heightIn(min = size.height)
            .pressScale(interactionSource)
            .semantics { role = Role.Button },
        enabled = enabled && !isLoading,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
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
private fun DestructiveButtonPreview_Default() {
    HackertabTheme {
        DestructiveButton(text = "Delete", onClick = {})
    }
}

@Preview
@Composable
private fun DestructiveButtonPreview_Disabled() {
    HackertabTheme {
        DestructiveButton(text = "Delete", onClick = {}, enabled = false)
    }
}

@Preview
@Composable
private fun DestructiveButtonPreview_Loading() {
    HackertabTheme {
        DestructiveButton(text = "Delete", onClick = {}, isLoading = true)
    }
}
