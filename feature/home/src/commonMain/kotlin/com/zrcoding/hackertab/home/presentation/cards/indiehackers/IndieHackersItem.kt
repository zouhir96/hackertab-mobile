package com.zrcoding.hackertab.home.presentation.cards.indiehackers

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.zrcoding.hackertab.design.components.cards.ArticleCard
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.MetaDotText
import com.zrcoding.hackertab.home.presentation.cards.MetaIconText
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

private val IndieHackersBlue = Color(0xFF4799EB)

@Composable
fun IndieHackersItem(
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
            MetaDotText(text = "${article.reactions} pts", color = IndieHackersBlue)
            MetaIconText(icon = Res.drawable.ic_comment, text = "${article.commentsCount}")
        },
    )
}

@Preview
@Composable
private fun IndieHackersItemPreview() {
    HackertabTheme {
        IndieHackersItem(
            article = Article(
                id = "ih_1",
                title = "How I bootstrapped a SaaS to $10k MRR in 6 months",
                url = "https://www.indiehackers.com/post/example",
                publishedAt = LocalDateTime(2025, 5, 8, 12, 0, 0),
                tags = listOf("saas", "bootstrap"),
                commentsCount = 31,
                reactions = 92,
                canonicalUrl = null,
                imageUrl = null,
                source = Source.INDIE_HACKERS,
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
