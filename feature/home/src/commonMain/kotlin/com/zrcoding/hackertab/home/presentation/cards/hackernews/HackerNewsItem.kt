package com.zrcoding.hackertab.home.presentation.cards.hackernews

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_ellipse
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.resources.score
import com.zrcoding.hackertab.design.theme.Flamingo
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HackerNewsItem(
    article: Article,
    isBookmarked: Boolean,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    SourceItemTemplate(
        title = article.title,
        primaryInfoSection = {
            TextWithStartIcon(
                text = stringResource(Res.string.score, article.reactions),
                textColor = Flamingo,
                icon = Res.drawable.ic_ellipse,
                tint = Flamingo
            )
            TextWithStartIcon(
                icon = Res.drawable.ic_time_24,
                text = article.publishedAt.timeAgo()
            )
            TextWithStartIcon(
                text = stringResource(Res.string.comments, article.commentsCount),
                icon = Res.drawable.ic_comment
            )
        },
        isBookmarked = isBookmarked,
        onBookmarkClick = onBookmarkClick,
        onShareClick = onShareClick,
        onClick = onClick
    )
}

@OptIn(ExperimentalTime::class)
@Preview()
@Composable
fun HackerNewsItemPreview() {
    HackertabTheme {
        HackerNewsItem(
            article = Article(
                id = "similique",
                title = "React is the best web framework ever React is the best web framework ever",
                url = "https://www.google.com/#q=propriae",
                publishedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                tags = listOf(),
                commentsCount = 0,
                reactions = 0,
                canonicalUrl = null,
                imageUrl = null,
                source = null
            ),
            isBookmarked = false,
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {}
        )
    }
}