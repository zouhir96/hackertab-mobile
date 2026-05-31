package com.zrcoding.hackertab.design.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.Icon
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import com.zrcoding.hackertab.domain.models.Source
import org.jetbrains.compose.resources.painterResource

@Composable
fun BookmarkCard(
    bookmark: BookmarkedArticle,
    onClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
    isUnread: Boolean = true,
) {
    val source = runCatching { Source.valueOf(bookmark.source) }.getOrNull()
    val sourceLabel = source?.label ?: bookmark.source
    val timeAgo = bookmark.timeAgoLabel()

    Card(
        modifier = modifier
            .padding(horizontal = MaterialTheme.dimension.space12)
            .padding(bottom = MaterialTheme.dimension.space6)
            .fillMaxWidth()
            .alpha(if (isUnread) 1f else 0.6f)
            .semantics(mergeDescendants = true) {
                contentDescription = "${bookmark.title}. $sourceLabel, " +
                    "${if (isUnread) "unread" else "read"}, $timeAgo. Tap to read."
            },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.dimension.space12, horizontal = MaterialTheme.dimension.space16),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
        ) {
            if (source != null) {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.dimension.space32)
                        .clip(MaterialTheme.shapes.small)
                        .background(source.bookmarkBrandColor()),
                    contentAlignment = Alignment.Center,
                ) {
                    val (iconRes, _) = source.Icon()
                    Icon(
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(MaterialTheme.dimension.space16),
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space6)) {
                    if (isUnread) {
                        Box(
                            modifier = Modifier
                                .size(MaterialTheme.dimension.space4)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                        )
                    }
                    Text(
                        text = bookmark.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Text(
                    text = "$sourceLabel · $timeAgo",
                    style = codeSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = MaterialTheme.dimension.space2),
                )
            }
            IconButton(
                onClick = onMoreClick,
                modifier = Modifier.size(MaterialTheme.dimension.space48),
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More actions",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(MaterialTheme.dimension.space16),
                )
            }
        }
    }
}

private fun Source.bookmarkBrandColor(): Color = when (this) {
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

private fun BookmarkedArticle.timeAgoLabel(): String = "saved"
