package com.zrcoding.hackertab.design.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.ThemeMode
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SourceRail(
    sources: ImmutableList<Source>,
    activeSourceId: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space6),
        contentPadding = PaddingValues(
            start = MaterialTheme.dimension.space16,
            end = MaterialTheme.dimension.space16,
            top = MaterialTheme.dimension.space6,
            bottom = MaterialTheme.dimension.space12,
        ),
    ) {
        items(sources, key = { it.id }) { source ->
            SourcePill(
                source = source,
                selected = source.id == activeSourceId,
                onClick = { onSelect(source.id) },
            )
        }
    }
}

@Composable
private fun SourcePill(source: Source, selected: Boolean, onClick: () -> Unit) {
    val containerColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary
        else Color.Transparent,
        animationSpec = spring(
            stiffness = 380f,
            dampingRatio = 0.5f,
        ),
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurface,
        animationSpec = spring(
            stiffness = 380f,
            dampingRatio = 0.5f,
        ),
    )
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val (iconRes, iconTint) = source.Icon()

    Row(
        modifier = Modifier
            .height(MaterialTheme.dimension.space40)
            .scale(if (isPressed) 0.97f else 1f)
            .clip(CircleShape)
            .background(containerColor)
            .let { if (selected) it else it.border(pillBorder(), CircleShape) }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(start = MaterialTheme.dimension.space8, end = MaterialTheme.dimension.space12)
            .semantics {
                role = Role.Tab
                this.selected = selected
                contentDescription = "${source.label}, ${if (selected) "selected" else "tap to select"}"
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
    ) {
        if (iconTint != Color.Unspecified) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = if (iconTint == Color.Unspecified) Color.White else iconTint,
                modifier = Modifier.size(MaterialTheme.dimension.space20),
            )
        } else Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(MaterialTheme.dimension.space20),
        )
        Text(
            text = source.label,
            style = MaterialTheme.typography.labelLarge,
            color = contentColor,
            fontWeight = FontWeight.W600,
        )
    }
}

@Composable
private fun pillBorder(): BorderStroke =
    BorderStroke(1.dp, MaterialTheme.colorScheme.outline)

@Preview
@Composable
private fun SourceRailLight() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        SourceRail(
            sources = persistentListOf(
                Source.GITHUB, Source.HACKER_NEWS, Source.DEVTO, Source.REDDIT,
            ),
            activeSourceId = "github",
            onSelect = {},
        )
    }
}

@Preview
@Composable
private fun SourceRailDark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        SourceRail(
            sources = persistentListOf(
                Source.GITHUB, Source.HACKER_NEWS, Source.DEVTO, Source.REDDIT, Source.PRODUCTHUNT,
            ),
            activeSourceId = "github",
            onSelect = {},
        )
    }
}
