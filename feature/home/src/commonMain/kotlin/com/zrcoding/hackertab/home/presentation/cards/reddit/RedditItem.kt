package com.zrcoding.hackertab.home.presentation.cards.reddit

import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.cards.ArticleCard
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.SourceReddit
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.MetaDotText
import com.zrcoding.hackertab.home.presentation.cards.MetaIconText
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Reddit feed card. Meta: score (brand-orange dot) · comments.
 */
@Composable
fun RedditItem(
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
        metaContent = {
            MetaDotText(text = "${article.reactions} pts", color = SourceReddit)
            MetaIconText(icon = Res.drawable.ic_comment, text = "${article.commentsCount}")
        },
    )
}

@Preview
@Composable
private fun RedditItemPreview() {
    HackertabTheme {
        RedditItem(
            article = Article(
                id = "rd_1",
                title = "Compose Multiplatform 1.9 — anyone else seeing iOS scrolling jank?",
                url = "https://reddit.com/r/Kotlin/example",
                publishedAt = LocalDateTime(2025, 5, 8, 14, 0, 0),
                tags = listOf("kotlin", "compose"),
                commentsCount = 47,
                reactions = 256,
                canonicalUrl = null,
                imageUrl = null,
                source = Source.REDDIT,
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
