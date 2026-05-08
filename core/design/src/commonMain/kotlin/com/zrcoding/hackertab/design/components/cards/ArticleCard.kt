package com.zrcoding.hackertab.design.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.SourceTag
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Source
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Generic article card. Used by HN, Reddit, Lobsters, DevTo, Hashnode,
 * FreeCodeCamp, Medium, IndieHackers, HackerNoon — any source whose payload
 * shape matches the [Article] domain model.
 *
 * The [metaContent] slot lets the call site supply source-specific stats
 * (points, comments, reactions, etc.) without duplicating card chrome.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticleCard(
    article: Article,
    timeAgo: String,
    isBookmarked: Boolean,
    isFresh: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onMoreClick: () -> Unit = {},
    metaContent: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val source = requireNotNull(article.source) { "ArticleCard requires Article.source" }
    CardShell(
        isFresh = isFresh,
        isRead = false, // wired by Wave 3 home feed
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = "${article.title}. ${source.label}, $timeAgo. Tap to read."
        },
    ) {
        SourceTag(source = source, timeAgo = timeAgo, isFresh = isFresh)
        Spacer(Modifier.height(8.dp))
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
        )
        if (article.tags.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                article.tags.take(6).forEach { tag ->
                    Text(
                        text = "#$tag",
                        style = codeSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 7.dp, vertical = 2.dp),
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            metaContent()
            Spacer(Modifier.weight(1f))
            CardActions(
                isBookmarked = isBookmarked,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onMoreClick,
            )
        }
    }
}

@Preview
@Composable
private fun ArticleCardPreview(){
    HackertabTheme {
        ArticleCard(
            article = Article(
                id = "populo",
                title = "constituto",
                url = "http://www.bing.com/search?q=voluptaria",
                bookmarked = false,
                publishedAt = LocalDateTime(2020, 12,12, 12,12, 12),
                commentsCount = 1602,
                reactions = 7139,
                tags = listOf(),
                canonicalUrl = "https://duckduckgo.com/?q=definitionem",
                imageUrl = "https://search.yahoo.com/search?p=ridiculus",
                source = Source.DEVTO
            ),
            timeAgo = "nonumes",
            isBookmarked = false,
            isFresh = false,
            onClick = {},
            onLongClick = {},
            onBookmarkClick = {},
            onMoreClick = {},
            metaContent = {},
        )
    }
}
