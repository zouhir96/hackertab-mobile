package com.zrcoding.hackertab.home.presentation.cards.lobsters

import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.cards.ArticleCard
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.SourceLobsters
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.MetaDotText
import com.zrcoding.hackertab.home.presentation.cards.MetaIconText
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LobstersItem(
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
            MetaDotText(text = "${article.reactions} pts", color = SourceLobsters)
            MetaIconText(icon = Res.drawable.ic_comment, text = "${article.commentsCount}")
        },
    )
}

@Preview
@Composable
private fun LobstersItemPreview() {
    HackertabTheme {
        LobstersItem(
            article = Article(
                id = "lo_1",
                title = "OpenBSD 7.6 release notes — what's new at the kernel level",
                url = "https://lobste.rs/s/example",
                publishedAt = LocalDateTime(2025, 5, 8, 6, 30, 0),
                tags = listOf("openbsd", "kernel"),
                commentsCount = 19,
                reactions = 124,
                canonicalUrl = null,
                imageUrl = null,
                source = Source.LOBSTERS,
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
