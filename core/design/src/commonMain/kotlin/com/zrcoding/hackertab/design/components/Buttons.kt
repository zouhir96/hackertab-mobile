@file:Suppress("unused")

package com.zrcoding.hackertab.design.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.zrcoding.hackertab.design.components.buttons.ButtonSize
import com.zrcoding.hackertab.design.components.buttons.PrimaryButton as PrimaryButtonV4

@Deprecated(
    message = "Wave 2 migration: import PrimaryButton from " +
        "com.zrcoding.hackertab.design.components.buttons.PrimaryButton " +
        "to access size and isLoading.",
    replaceWith = ReplaceWith(
        expression = "PrimaryButton(text = text, onClick = onClick, modifier = modifier, " +
            "enabled = enabled, leadingIcon = leadingIcon, trailingIcon = trailingIcon)",
        imports = ["com.zrcoding.hackertab.design.components.buttons.PrimaryButton"],
    ),
    level = DeprecationLevel.WARNING,
)
@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit,
) {
    PrimaryButtonV4(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        size = ButtonSize.Medium,
    )
}
