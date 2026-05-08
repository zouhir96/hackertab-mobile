package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Hackertab v4 chip wrappers. These thin wrappers around the M3 chip family
 * apply the design-system color scheme (see `Library.jsx` slug 03.03) and
 * keep the default 48dp tap target — we explicitly do **not** disable
 * `LocalMinimumInteractiveComponentSize`.
 *
 * Selected colors are the Hackertab brand pair: `primary` container with
 * `onPrimary` label. Idle uses `surfaceVariant` / `onSurface`.
 */

/**
 * Filter chip — toggleable selection (e.g. "Today" filter). Wraps M3
 * [FilterChip].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HackertabFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text = label, style = MaterialTheme.typography.labelMedium) },
        modifier = modifier.semantics { role = Role.Switch },
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = MaterialTheme.shapes.small,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurface,
            iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = null,
    )
}

/**
 * Input chip — represents a discrete entered value (e.g. a tag in a search
 * field). Optional `onRemove` shows a trailing X. Wraps M3 [InputChip].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HackertabInputChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    onRemove: (() -> Unit)? = null,
    enabled: Boolean = true,
) {
    InputChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text = label, style = MaterialTheme.typography.labelMedium) },
        modifier = modifier,
        enabled = enabled,
        trailingIcon = onRemove?.let {
            {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    modifier = Modifier
                        .size(14.dp)
                        .semantics { role = Role.Button },
                )
            }
        },
        shape = MaterialTheme.shapes.small,
        colors = InputChipDefaults.inputChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurface,
            trailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = null,
    )
}

/**
 * Suggestion chip — non-toggleable, dismissable hint (e.g. "Add a topic").
 * Wraps M3 [SuggestionChip].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HackertabSuggestionChip(
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
) {
    SuggestionChip(
        onClick = onClick,
        label = { Text(text = label, style = MaterialTheme.typography.labelMedium) },
        modifier = modifier,
        enabled = enabled,
        icon = leadingIcon,
        shape = MaterialTheme.shapes.small,
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurface,
            iconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        border = null,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ChipsPreview() {
    HackertabTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            HackertabFilterChip(selected = true, onClick = {}, label = "Today")
            HackertabFilterChip(selected = false, onClick = {}, label = "Week")
            HackertabInputChip(selected = false, onClick = {}, label = "Kotlin", onRemove = {})
            HackertabSuggestionChip(
                onClick = {},
                label = "Add topic",
                leadingIcon = { Icon(Icons.Default.Add, contentDescription = null) },
            )
        }
    }
}
