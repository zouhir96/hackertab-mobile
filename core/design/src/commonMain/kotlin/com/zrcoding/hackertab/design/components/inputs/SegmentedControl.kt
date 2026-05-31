package com.zrcoding.hackertab.design.components.inputs

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabMotion
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

data class SegmentOption(val id: String, val label: String)

@Composable
fun SegmentedControl(
    options: ImmutableList<SegmentOption>,
    selectedId: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = MaterialTheme.dimension.space4),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space6),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        options.forEach { option ->
            SegmentPill(
                option = option,
                selected = option.id == selectedId,
                onClick = { onSelect(option.id) },
            )
        }
    }
}

@Composable
private fun SegmentPill(
    option: SegmentOption,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val animation = tween<androidx.compose.ui.graphics.Color>(
        durationMillis = HackertabMotion.fast,
        easing = HackertabMotion.standardEasing,
    )
    val containerColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.onBackground
        } else {
            androidx.compose.ui.graphics.Color.Transparent
        },
        animationSpec = animation,
        label = "segment.container",
    )
    val labelColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.background
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = animation,
        label = "segment.label",
    )
    val borderStroke = if (selected) {
        null
    } else {
        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    }
    val isSelected = selected

    Box(
        modifier = Modifier
            .height(MaterialTheme.dimension.space32)
            .clip(CircleShape)
            .background(containerColor)
            .let { base -> if (borderStroke != null) base.border(borderStroke, CircleShape) else base }
            .clickable(role = Role.Tab, onClick = onClick)
            .semantics {
                role = Role.Tab
                this.selected = isSelected
            }
            .padding(horizontal = MaterialTheme.dimension.space12),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = option.label,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.W500),
            color = labelColor,
        )
    }
}

@Preview
@Composable
private fun SegmentedControlPreview() {
    HackertabTheme {
        SegmentedControl(
            options = persistentListOf(
                SegmentOption("today", "Today"),
                SegmentOption("week", "This week"),
                SegmentOption("month", "This month"),
            ),
            selectedId = "week",
            onSelect = {},
        )
    }
}
