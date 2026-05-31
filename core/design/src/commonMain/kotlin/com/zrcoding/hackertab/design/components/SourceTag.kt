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
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Compact source-attribution row used in the header of every feed card.
 *
 * Renders: [source's brand block + label] · [time-ago] [optional NEW pill].
 *
 * Issue 6 (critique): when [isFresh] is true, a high-contrast "NEW" pill is
 * appended after the time-ago. This is the *primary* fresh indicator on the
 * card; CardShell renders a 3dp brand bar as a *secondary* signal.
 */
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
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // 18dp brand-colored block with the source glyph
        Source.IconBlock(source = source, size = 18.dp)

        Text(
            text = source.label,
            style = codeSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.W600,
        )

        // Separator dot
        Box(
            modifier = Modifier
                .size(3.dp)
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
            .height(16.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 6.dp),
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

/**
 * Brand-colored block holding a source's glyph. Reusable from both the source
 * rail (22dp) and source tag (18dp).
 */
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
                modifier = Modifier.size(size - 6.dp),
            )
        } else Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(size - 6.dp),
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
