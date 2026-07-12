package com.zrcoding.hackertab.design.components.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

data class ActionRow(
    val id: String,
    val icon: ImageVector,
    val label: String,
    val isDestructive: Boolean = false,
    val onClick: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    actions: ImmutableList<ActionRow>,
    modifier: Modifier = Modifier,
    title: String? = null,
) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimension.space16, vertical = MaterialTheme.dimension.space8),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space2),
        ) {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space12, vertical = MaterialTheme.dimension.space12),
                )
            }
            actions.forEach { action ->
                ActionSheetRow(
                    action = action,
                    onClick = {
                        action.onClick()
                        onDismiss()
                    },
                )
            }
        }
    }
}

@Composable
private fun ActionSheetRow(
    action: ActionRow,
    onClick: () -> Unit,
) {
    val color = if (action.isDestructive) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MaterialTheme.dimension.space8))
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { role = Role.Button }
            .padding(horizontal = MaterialTheme.dimension.space12, vertical = MaterialTheme.dimension.space12),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.dimension.space24)
                .clip(RoundedCornerShape(MaterialTheme.dimension.space8))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(MaterialTheme.dimension.space12),
            )
        }
        Text(
            text = action.label,
            style = MaterialTheme.typography.titleSmall,
            color = color,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ActionSheetPreview() {
    HackertabTheme {
        ActionSheet(
            visible = true,
            onDismiss = {},
            title = "Article actions",
            actions = persistentListOf(
                ActionRow("save", Icons.Default.Bookmark, "Save", onClick = {}),
                ActionRow("share", Icons.Default.Share, "Share", onClick = {}),
                ActionRow(
                    id = "delete",
                    icon = Icons.Default.Delete,
                    label = "Remove bookmark",
                    isDestructive = true,
                    onClick = {},
                ),
            ),
        )
    }
}
