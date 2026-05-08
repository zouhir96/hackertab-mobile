package com.zrcoding.hackertab.home.presentation.cards.hackernoon

import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.cards.ArticleCard
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * HackerNoon feed card. Tags-only meta (rendered by `ArticleCard`).
 */
@Composable
fun HackerNoonItem(
    article: Article,
    isRead: Boolean = false,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onLongClick: () -> Unit = {},
) {
    ArticleCard(
        article = article,
        timeAgo = article.publishedAt.timeAgo(),
        isBookmarked = article.bookmarked,
        isFresh = false,
        onClick = onClick,
        onLongClick = onLongClick,
        onBookmarkClick = onBookmarkClick,
        onMoreClick = onLongClick,
        metaContent = {},
    )
}

@Preview
@Composable
private fun HackerNoonItemPreview() {
    HackertabTheme {
        HackerNoonItem(
            article = Article(
                id = "hn_1",
                title = "Why your AI startup needs a moat by next week",
                url = "https://hackernoon.com/example",
                publishedAt = LocalDateTime(2025, 5, 8, 7, 45, 0),
                tags = listOf("ai", "startups"),
                commentsCount = 0,
                reactions = 0,
                canonicalUrl = null,
                imageUrl = null,
                source = Source.HACKER_NOON,
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
