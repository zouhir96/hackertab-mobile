package com.zrcoding.hackertab.home.presentation.cards.hackernews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.cards.ArticleCard
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.SourceHackerNews
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Hacker News feed card.
 * Meta: score (brand orange) · time ago · comment count.
 */
@Composable
fun HackerNewsItem(
    article: Article,
    isRead: Boolean = false,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onLongClick: () -> Unit = {},
) {
    val timeAgo = article.publishedAt.timeAgo()
    ArticleCard(
        article = article,
        timeAgo = timeAgo,
        isBookmarked = article.bookmarked,
        isFresh = false,
        onClick = onClick,
        onLongClick = onLongClick,
        onBookmarkClick = onBookmarkClick,
        onMoreClick = onLongClick,
        metaContent = {
            // Score dot (brand orange)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(SourceHackerNews),
                )
                Text(
                    text = "${article.reactions} pts",
                    style = codeSmall,
                    color = SourceHackerNews,
                )
            }
            // Comment count
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_comment),
                    contentDescription = "comments",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(12.dp),
                )
                Text(
                    text = "${article.commentsCount}",
                    style = codeSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
    )
}

@Preview
@Composable
private fun HackerNewsItemLightPreview() {
    HackertabTheme {
        HackerNewsItem(
            article = Article(
                id = "hn_1",
                title = "React is the best web framework ever — a deeply considered take",
                url = "https://news.ycombinator.com/item?id=1",
                publishedAt = LocalDateTime(2025, 5, 8, 10, 0, 0),
                tags = listOf(),
                commentsCount = 142,
                reactions = 347,
                canonicalUrl = null,
                imageUrl = null,
                source = Source.HACKER_NEWS,
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
