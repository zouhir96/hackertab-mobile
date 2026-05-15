package com.zrcoding.hackertab.home.presentation.cards.medium

import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.cards.ArticleCard
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_claps
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.MetaIconText
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Medium feed card. Meta: claps · comments.
 *
 * Note: package is `mediun` (typo) until Wave 7 renames it to `medium`.
 */
@Composable
fun MediumItem(
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
            MetaIconText(icon = Res.drawable.ic_claps, text = "${article.reactions}")
            MetaIconText(icon = Res.drawable.ic_comment, text = "${article.commentsCount}")
        },
    )
}

@Preview
@Composable
private fun MediumItemPreview() {
    HackertabTheme {
        MediumItem(
            article = Article(
                id = "md_1",
                title = "Five Compose patterns I've stopped using in 2025",
                url = "https://medium.com/example",
                publishedAt = LocalDateTime(2025, 5, 8, 13, 0, 0),
                tags = listOf("android", "compose"),
                commentsCount = 12,
                reactions = 384,
                canonicalUrl = null,
                imageUrl = null,
                source = Source.MEDIUM,
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
