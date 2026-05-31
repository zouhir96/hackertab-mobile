package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SourceTag(
    source: Source,
    timeAgo: String,
    isFresh: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = "${source.label}, $timeAgo${if (isFresh) ", new" else ""}"
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
    ) {
        Source.IconBlock(source = source, size = MaterialTheme.dimension.space16)

        Text(
            text = source.label,
            style = codeSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.W600,
        )

        Box(
            modifier = Modifier
                .size(MaterialTheme.dimension.space2)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)),
        )

        Text(
            text = timeAgo,
            style = codeSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        if (isFresh) {
            FreshPill()
        }
    }
}

@Composable
private fun FreshPill() {
    Box(
        modifier = Modifier
            .height(MaterialTheme.dimension.space16)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = MaterialTheme.dimension.space6),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "NEW",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.W700,
        )
    }
}

@Composable
internal fun Source.Companion.IconBlock(
    source: Source,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    val (iconRes, iconTint) = source.Icon()
    Box(
        modifier = modifier
            .size(size)
            .clip(MaterialTheme.shapes.small)
            .background(source.tagBrandColor()),
        contentAlignment = Alignment.Center,
    ) {
        if (iconTint != Color.Unspecified) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(size - MaterialTheme.dimension.space6),
            )
        } else Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(size - MaterialTheme.dimension.space6),
        )
    }
}

private fun Source.tagBrandColor(): Color = when (this) {
    Source.GITHUB -> Color(0xFF181717)
    Source.HACKER_NEWS -> Color(0xFFFF6600)
    Source.REDDIT -> Color(0xFFFF4500)
    Source.PRODUCTHUNT -> Color(0xFFDA552F)
    Source.DEVTO -> Color(0x1A0A0A0A)
    Source.LOBSTERS -> Color(0xFFAC130D)
    Source.HASH_NODE -> Color(0xFF2962FF)
    Source.FREE_CODE_CAMP -> Color(0x1A0A0A23)
    Source.INDIE_HACKERS -> Color(0xFF0E2439)
    Source.MEDIUM -> Color(0xFF00AB6C)
    Source.HACKER_NOON -> Color(0xFF00B14F)
    Source.CONFERENCES -> Color(0xFF6E56CF)
}

@Preview
@Composable
private fun SourceTagFreshLight() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        SourceTag(
            source = Source.HACKER_NEWS,
            timeAgo = "1h ago",
            isFresh = true,
        )
    }
}

@Preview
@Composable
private fun SourceTagDark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        SourceTag(
            source = Source.GITHUB,
            timeAgo = "3d ago",
            isFresh = false,
        )
    }
}
