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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Hackertab v4 primary call-to-action button. Mirrors `Buttons.Primary` in
 * `design/project/components/Library.jsx` (slug 03.04).
 *
 * Anatomy:
 *  - Filled background using `colorScheme.primary` / `onPrimary`.
 *  - Shape is [MaterialTheme.shapes.medium] (10dp) for [ButtonSize.Small] and
 *    [ButtonSize.Medium]; [ButtonSize.Large] uses fully-rounded `CircleShape`.
 *  - Disabled state: 40% container alpha + 60% content alpha.
 *  - Pressed state: 0.97 scale via the shared spring (see [pressScale]).
 *  - Loading state: replaces label/icons with a 16dp spinner; clicks suppressed.
 *
 *  A11y:
 *  - Inherits `Role.Button` from [Button]; we re-assert it for the pressScale
 *    semantics wrapper so `enabled = false` still announces correctly.
 *  - Leading/trailing icons are decorative (`contentDescription = null`).
 */
@Composable
fun PrimaryButton(
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
    val colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
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
                contentColor = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview_Default() {
    HackertabTheme {
        PrimaryButton(text = "Continue", onClick = {})
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview_Small() {
    HackertabTheme {
        PrimaryButton(text = "Save", onClick = {}, size = ButtonSize.Small)
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview_Large() {
    HackertabTheme {
        PrimaryButton(text = "Get started", onClick = {}, size = ButtonSize.Large)
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview_Disabled() {
    HackertabTheme {
        PrimaryButton(text = "Continue", onClick = {}, enabled = false)
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview_Loading() {
    HackertabTheme {
        PrimaryButton(text = "Continue", onClick = {}, isLoading = true)
    }
}
