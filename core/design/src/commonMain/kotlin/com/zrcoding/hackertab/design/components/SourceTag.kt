package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.ThemeMode
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
        Text(
            text = timeAgo,
            style = codeSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        FreshPill()
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
