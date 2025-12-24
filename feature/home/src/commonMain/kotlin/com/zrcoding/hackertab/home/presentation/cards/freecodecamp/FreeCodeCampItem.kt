package com.zrcoding.hackertab.home.presentation.cards.freecodecamp

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FreeCodeCampItem(
    article: Article,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    SourceItemTemplate(
        title = article.title.trim(),
        description = null,
        primaryInfoSection = {
            TextWithStartIcon(
                icon = Res.drawable.ic_time_24,
                text = article.publishedAt.timeAgo()
            )
        },
        tags = article.tags,
        isBookmarked = article.bookmarked,
        onBookmarkClick = onBookmarkClick,
        onShareClick = onShareClick,
        onClick = onClick
    )
}

@OptIn(ExperimentalTime::class)
@Preview()
@Composable
private fun FreeCodeCampItemPreview() {
    HackertabTheme {
        FreeCodeCampItem(
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
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {}
        )
    }
}