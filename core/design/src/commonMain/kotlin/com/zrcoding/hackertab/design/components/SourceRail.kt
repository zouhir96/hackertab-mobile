package com.zrcoding.hackertab.design.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.ThemeMode
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/** Pseudo-source id for the "All" rail item that aggregates every enabled source. */
const val ALL_SOURCES_ID: String = "all"

// TODO Wave 7+: CMP has no cross-platform reduce-motion flag; degrade per-platform via expect/actual.
private const val IS_REDUCED_MOTION = false

/**
 * Persistent horizontal source switcher. Replaces the v3 dropdown title.
 * Always visible above the feed; tapping a pill changes the active source
 * with a spring-eased color crossfade.
 *
 * Issue 11 (critique): no `★` prefix on the "All" pill — just the bold label.
 */
@Composable
fun SourceRail(
    sources: ImmutableList<Source>,
    activeSourceId: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    showAllPseudoSource: Boolean = true,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 12.dp),
    ) {
        if (showAllPseudoSource) {
            item(key = ALL_SOURCES_ID) {
                AllPill(
                    selected = activeSourceId == ALL_SOURCES_ID,
                    onClick = { onSelect(ALL_SOURCES_ID) },
                )
            }
        }
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
private fun AllPill(selected: Boolean, onClick: () -> Unit) {
    val containerColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary
        else Color.Transparent,
        animationSpec = if (IS_REDUCED_MOTION) snap() else spring(
            stiffness = 380f,
            dampingRatio = 0.5f,
        ),
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurface,
        animationSpec = if (IS_REDUCED_MOTION) snap() else spring(
            stiffness = 380f,
            dampingRatio = 0.5f,
        ),
    )
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .height(38.dp)
            .scale(if (isPressed) 0.97f else 1f)
            .clip(CircleShape)
            .background(containerColor)
            .let {
                if (selected) it
                else it.border(pillBorder(), CircleShape)
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 14.dp)
            .semantics {
                role = Role.Tab
                this.selected = selected
                contentDescription = if (selected) "All sources, selected" else "All sources, tap to select"
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "All",
            style = MaterialTheme.typography.labelLarge,
            color = contentColor,
            fontWeight = if (selected) FontWeight.W600 else FontWeight.W700,
        )
    }
}

@Composable
private fun SourcePill(source: Source, selected: Boolean, onClick: () -> Unit) {
    val containerColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary
        else Color.Transparent,
        animationSpec = if (IS_REDUCED_MOTION) snap() else spring(
            stiffness = 380f,
            dampingRatio = 0.5f,
        ),
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurface,
        animationSpec = if (IS_REDUCED_MOTION) snap() else spring(
            stiffness = 380f,
            dampingRatio = 0.5f,
        ),
    )
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val (iconRes, iconTint) = source.Icon()

    Row(
        modifier = Modifier
            .height(38.dp)
            .scale(if (isPressed) 0.97f else 1f)
            .clip(CircleShape)
            .background(containerColor)
            .let { if (selected) it else it.border(pillBorder(), CircleShape) }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(start = 8.dp, end = 12.dp)
            .semantics {
                role = Role.Tab
                this.selected = selected
                contentDescription = "${source.label}, ${if (selected) "selected" else "tap to select"}"
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Brand-colored 22dp block with the source's white glyph
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(source.brandColor()),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = if (iconTint == Color.Unspecified) Color.White else iconTint,
                modifier = Modifier.size(14.dp),
            )
        }
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

/**
 * Returns the source's brand color used as the rail-pill icon-block background.
 * Mirrors the values in `design/project/components/data.js`.
 */
private fun Source.brandColor(): Color = when (this) {
    Source.GITHUB -> Color(0xFF181717)
    Source.HACKER_NEWS -> Color(0xFFFF6600)
    Source.REDDIT -> Color(0xFFFF4500)
    Source.PRODUCTHUNT -> Color(0xFFDA552F)
    Source.DEVTO -> Color(0xFF0A0A0A)
    Source.LOBSTERS -> Color(0xFFAC130D)
    Source.HASH_NODE -> Color(0xFF2962FF)
    Source.FREE_CODE_CAMP -> Color(0xFF0A0A23)
    Source.INDIE_HACKERS -> Color(0xFF0E2439)
    Source.MEDIUM -> Color(0xFF00AB6C)
    Source.HACKER_NOON -> Color(0xFF00B14F)
    Source.CONFERENCES -> Color(0xFF6E56CF)
}

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
            activeSourceId = ALL_SOURCES_ID,
            onSelect = {},
        )
    }
}
