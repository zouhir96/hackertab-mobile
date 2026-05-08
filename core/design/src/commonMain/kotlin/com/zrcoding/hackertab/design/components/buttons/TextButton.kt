package com.zrcoding.hackertab.design.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
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

/**
 * Hackertab v4 text button — no chrome, brand-primary label. Used for inline
 * actions ("Skip", "View all"). Mirrors `Library.jsx` slug 03.04.
 *
 * Single size — height/typography align with [ButtonSize.Medium], horizontal
 * padding fixed at 14dp (per the JSX reference).
 */
@Composable
fun HackertabTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    isLoading: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val size = ButtonSize.Medium
    val contentColor = MaterialTheme.colorScheme.primary
        .copy(alpha = if (enabled) 1f else 0.4f)
    val colors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = contentColor,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = contentColor,
    )

    TextButton(
        onClick = onClick,
        modifier = modifier
            .heightIn(min = size.height)
            .pressScale(interactionSource)
            .semantics { role = Role.Button },
        enabled = enabled && !isLoading,
        interactionSource = interactionSource,
        shape = MaterialTheme.shapes.medium,
        colors = colors,
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
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
private fun TextButtonPreview_Default() {
    HackertabTheme {
        HackertabTextButton(text = "Skip", onClick = {})
    }
}

@Preview
@Composable
private fun TextButtonPreview_Disabled() {
    HackertabTheme {
        HackertabTextButton(text = "Skip", onClick = {}, enabled = false)
    }
}
