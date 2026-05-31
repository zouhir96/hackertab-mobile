package com.zrcoding.hackertab.design.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabMotion
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeMedium
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TopicChipStrip(
    topics: ImmutableList<Topic>,
    activeTopicId: String?,
    onSelect: (Topic) -> Unit,
    modifier: Modifier = Modifier,
    canAddTopic: Boolean = false,
    onAddTopic: () -> Unit = {},
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space6),
        contentPadding = PaddingValues(horizontal = MaterialTheme.dimension.space16, vertical = 0.dp),
    ) {
        items(topics, key = { it.value }) { topic ->
            TopicPill(
                topic = topic,
                selected = topic.value == activeTopicId,
                onClick = { onSelect(topic) },
            )
        }
        if (canAddTopic) {
            item(key = "__add__") {
                AddTopicChip(onClick = onAddTopic)
            }
        }
    }
}

@Composable
private fun TopicPill(topic: Topic, selected: Boolean, onClick: () -> Unit) {
    val containerColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.surfaceVariant
        else Color.Transparent,
        animationSpec = tween(
            durationMillis = HackertabMotion.fast,
            easing = HackertabMotion.standardEasing,
        ),
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onSurface
        else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(
            durationMillis = HackertabMotion.fast,
            easing = HackertabMotion.standardEasing,
        ),
    )

    Row(
        modifier = Modifier
            .height(MaterialTheme.dimension.space32)
            .clip(CircleShape)
            .background(containerColor)
            .let {
                if (selected) it
                else it.border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    CircleShape,
                )
            }
            .clickable(onClick = onClick)
            .padding(horizontal = MaterialTheme.dimension.space12)
            .semantics {
                role = Role.Tab
                this.selected = selected
                contentDescription = "${topic.label}, ${if (selected) "selected" else "tap to filter"}"
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space6),
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.dimension.space6)
                .clip(CircleShape)
                .background(topic.value.getTagColor()),
        )
        Text(
            text = topic.label,
            style = codeMedium,
            color = contentColor,
        )
    }
}

@Composable
private fun AddTopicChip(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(MaterialTheme.dimension.space32)
            .clip(CircleShape)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                CircleShape,
            )
            .clickable(onClick = onClick)
            .semantics {
                role = Role.Button
                contentDescription = "Add topic"
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(MaterialTheme.dimension.space12),
        )
    }
}

@Preview
@Composable
private fun TopicChipStripLight() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        TopicChipStrip(
            topics = persistentListOf(
                Topic(value = "kotlin", label = "Kotlin", category = "mobile"),
                Topic(value = "rust", label = "Rust", category = "backend"),
                Topic(value = "go", label = "Go", category = "backend"),
                Topic(value = "react", label = "React", category = "frontend"),
            ),
            activeTopicId = "kotlin",
            onSelect = {},
            canAddTopic = true,
        )
    }
}

@Preview
@Composable
private fun TopicChipStripDark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        TopicChipStrip(
            topics = persistentListOf(
                Topic(value = "kotlin", label = "Kotlin", category = "mobile"),
                Topic(value = "rust", label = "Rust", category = "backend"),
            ),
            activeTopicId = "rust",
            onSelect = {},
        )
    }
}
