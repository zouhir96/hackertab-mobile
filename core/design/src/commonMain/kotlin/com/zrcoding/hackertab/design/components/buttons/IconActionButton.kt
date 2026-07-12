package com.zrcoding.hackertab.design.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IconActionButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    size: Dp = 42.dp,
    iconSize: Dp = 18.dp,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val resolvedTint = tint.copy(alpha = if (enabled) 1f else 0.4f)
    val resolvedBackground = backgroundColor.copy(alpha = if (enabled) 1f else 0.4f)

    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .pressScale(interactionSource)
            .size(size)
            .clip(CircleShape)
            .background(resolvedBackground)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = false, radius = size / 2),
                enabled = enabled,
                onClick = onClick,
                role = Role.Button,
            )
            .semantics {
                role = Role.Button
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = resolvedTint,
            modifier = Modifier.size(iconSize),
        )
    }
}

@Preview
@Composable
private fun IconActionButtonPreview_Default() {
    HackertabTheme {
        IconActionButton(
            icon = Icons.Default.Bookmark,
            contentDescription = "Bookmark",
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun IconActionButtonPreview_Disabled() {
    HackertabTheme {
        IconActionButton(
            icon = Icons.Default.Bookmark,
            contentDescription = "Bookmark",
            onClick = {},
            enabled = false,
        )
    }
}
