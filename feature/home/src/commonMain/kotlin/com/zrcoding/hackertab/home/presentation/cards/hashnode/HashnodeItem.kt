package com.zrcoding.hackertab.home.presentation.cards.hashnode

import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.cards.ArticleCard
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_like
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.MetaIconText
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HashnodeItem(
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
            MetaIconText(icon = Res.drawable.ic_comment, text = "${article.commentsCount}")
            MetaIconText(icon = Res.drawable.ic_like, text = "${article.reactions}")
        },
    )
}

@Preview
@Composable
private fun HashnodeItemPreview() {
    HackertabTheme {
        HashnodeItem(
            article = Article(
                id = "hn_1",
                title = "Migrating a Kotlin Multiplatform app to Compose 1.9",
                url = "https://example.hashnode.dev/post",
                publishedAt = LocalDateTime(2025, 5, 8, 11, 15, 0),
                tags = listOf("kotlin", "kmp"),
                commentsCount = 7,
                reactions = 64,
                canonicalUrl = null,
                imageUrl = null,
                source = Source.HASH_NODE,
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
