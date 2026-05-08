package com.zrcoding.hackertab.home.presentation.cards.freecodecamp

import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.cards.ArticleCard
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * FreeCodeCamp feed card. No source-specific meta beyond what
 * `ArticleCard`/`SourceTag` already render (source name + time-ago + tags).
 */
@Composable
fun FreeCodeCampItem(
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
private fun FreeCodeCampItemPreview() {
    HackertabTheme {
        FreeCodeCampItem(
            article = Article(
                id = "fcc_1",
                title = "Learn TypeScript by building 5 small projects",
                url = "https://freecodecamp.org/news/example",
                publishedAt = LocalDateTime(2025, 5, 8, 8, 0, 0),
                tags = listOf("typescript", "javascript"),
                commentsCount = 0,
                reactions = 0,
                canonicalUrl = null,
                imageUrl = null,
                source = Source.FREE_CODE_CAMP,
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
